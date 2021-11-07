package repository;

import helper.ChatMapper;
import helper.MessageMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class DatabaseChatRepository implements ChatRepository {

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public DatabaseChatRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public List<Chat> findListChatByUser(User user) {
        return jdbcTemplate.query("SELECT chats.*, u1.*, u2.*, messages.* " +
                        " FROM chats " +
                        " JOIN users u1 " +
                        " ON chats.user1_id = u1.user_id " +
                        " JOIN users u2 " +
                        " ON chats.user2_id = u2.user_id " +
                        " LEFT JOIN messages " +
                        " ON chats.chat_last_message = messages.message_id " +
                        " WHERE chats.user1_id=? OR chats.user2_id=? ",
                new ChatMapper(),user.getId(),user.getId());
    }

    @Override
    public Chat getByNumberChat(int i) {
        return jdbcTemplate.query("SELECT * FROM chats WHERE chat_id=?",
                new Object[]{i},
                new ChatMapper()).stream().findAny().orElse(null);
    }

    @Override
    public Chat addChat(User user1, User user2) {

        int id = jdbcTemplate.queryForObject("INSERT INTO chats (user1_id,user2_id) VALUES (?,?) RETURNING chat_id",
                Integer.class,
                user1.getId(),user2.getId());
        return null;
    }

    @Override
    public void addMessageToChat(String text, User user, int chatId) {

        LocalDateTime localDateTime = LocalDateTime.now();

        int id = jdbcTemplate.queryForObject("INSERT INTO messages (text_message,chat_id,user_id,date_message) VALUES(?,?,?,?) RETURNING message_id",
                Integer.class,
                text, chatId, user.getId(), java.sql.Timestamp.valueOf(localDateTime));

        jdbcTemplate.update("UPDATE chats SET chat_last_message=? WHERE chat_id=?", id, chatId);

    }

    @Override
    public List<Message> getListMessageByNumberChat(int i) {
        return jdbcTemplate.query(" SELECT users.*, messages.* " +
                " FROM messages JOIN users " +
                " ON messages.user_id = users.user_id " +
                " WHERE messages.chat_id =? ",
                new MessageMapper(),i);
    }
}
