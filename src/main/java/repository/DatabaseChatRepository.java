package repository;

import helper.ChatMapper;
import helper.MessageMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.TransactionCallbackWithoutResult;
import org.springframework.transaction.support.TransactionTemplate;

import java.time.LocalDateTime;
import java.util.List;

public class DatabaseChatRepository implements ChatRepository {

    private final JdbcTemplate jdbcTemplate;
    private final TransactionTemplate transactionTemplate;

    @Autowired
    public DatabaseChatRepository(JdbcTemplate jdbcTemplate,TransactionTemplate transactionTemplate) {
        this.jdbcTemplate = jdbcTemplate;
        this.transactionTemplate = transactionTemplate;
    }

    @Override
    public List<Chat> findListChatByUser(User user) {
        return jdbcTemplate.query("SELECT chats.chat_id, messages.text_message, messages.date_message, users.user_name AS username " +
                        " FROM chats " +
                        " LEFT JOIN messages " +
                        " ON chats.chat_last_message = messages.message_id " +
                        " JOIN users_chats uc1 " +
                        " ON chats.chat_id = uc1.chat_id " +
                        " JOIN users_chats uc2 " +
                        " ON chats.chat_id = uc2.chat_id " +
                        " AND uc2.user_id != ? " +
                        " JOIN users " +
                        " ON uc2.user_id = users.user_id " +
                        " WHERE chats.chat_type = 'private' " +
                        " AND uc1.user_id = ? " +
                        " UNION " +
                        " SELECT chats.chat_id, messages.text_message, messages.date_message, chats.name_chat AS username " +
                        " FROM chats " +
                        " LEFT JOIN messages " +
                        " ON chats.chat_last_message = messages.message_id " +
                        " JOIN users_chats " +
                        " ON chats.chat_id = users_chats.chat_id " +
                        " WHERE users_chats.user_id = ?" +
                        " AND chats.chat_type = 'group' " +
                        " UNION " +
                        " SELECT chats.chat_id, messages.text_message, messages.date_message, chats.chat_type AS username " +
                        " FROM chats " +
                        " LEFT JOIN messages " +
                        " ON chats.chat_last_message = messages.message_id " +
                        " JOIN users_chats " +
                        " ON chats.chat_id = users_chats.chat_id " +
                        " WHERE users_chats.user_id = ?" +
                        " AND chats.chat_type = 'saved' ",
                new ChatMapper(),user.getId(),user.getId(),user.getId(),user.getId());
    }

    // Не используется. Надо поменять
    @Override
    public Chat getByNumberChat(int i) {
        return jdbcTemplate.query("SELECT * FROM chats WHERE chat_id=?",
                new Object[]{i},
                new ChatMapper()).stream().findAny().orElse(null);
    }

    @Override
    public Chat addChat(List<User> users,String chatType) {

        transactionTemplate.execute(status ->{
            int id = jdbcTemplate.queryForObject("INSERT INTO chats (chat_type) VALUES(?) RETURNING chat_id",
                    Integer.class,chatType);
            for (User user : users) {
                jdbcTemplate.update("INSERT INTO users_chats (user_id,chat_id) VALUES(?,?)", user.getId(), id);
            }
            return null;
        });
        return null;
    }

    @Override
    public void addMessageToChat(String text, User user, int chatId) {

        transactionTemplate.execute(new TransactionCallbackWithoutResult() {
            @Override
            protected void doInTransactionWithoutResult(TransactionStatus status) {
                LocalDateTime localDateTime = LocalDateTime.now();

                int id = jdbcTemplate.queryForObject("INSERT INTO messages (text_message,chat_id,user_id,date_message) VALUES(?,?,?,?) RETURNING message_id",
                        Integer.class,
                        text, chatId, user.getId(), java.sql.Timestamp.valueOf(localDateTime));

                jdbcTemplate.update("UPDATE chats SET chat_last_message=? WHERE chat_id=?", id, chatId);
            }
        });
    }

    @Override
    public List<Message> getListMessageByNumberChat(int i) {
        return jdbcTemplate.query(" SELECT * FROM messages WHERE chat_id=? ",
                new MessageMapper(),i);
    }
}
