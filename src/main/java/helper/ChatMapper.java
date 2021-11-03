package helper;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import repository.Chat;
import repository.Message;
import repository.User;
import repository.UserRepository;

import java.sql.*;

public class ChatMapper implements RowMapper<Chat> {

    private final JdbcTemplate jdbcTemplate;
    private final UserRepository userRepository;
    private static final String URL = "jdbc:postgresql://localhost:5432/first_bd";
    private static final String USERNAME = "postgres";
    private static final String PASSWORD = "14789";
    private static Connection connection;

    static {
        try {
            Class.forName("org.postgresql.Driver");
        }catch (ClassNotFoundException e){
            e.printStackTrace();
        }

        try {
            connection = DriverManager.getConnection(URL,USERNAME,PASSWORD);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public ChatMapper(JdbcTemplate jdbcTemplate, UserRepository userRepository) {
        this.jdbcTemplate = jdbcTemplate;
        this.userRepository = userRepository;
    }

    @Override
    public Chat mapRow(ResultSet resultSet, int i) throws SQLException {
        Chat chat = new Chat();
        chat.setChatId(resultSet.getInt("chat_id"));
        chatUser(chat);
        chatMessage(chat);

        return chat;
    }

    private void chatUser (Chat chat){
        try {
            PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM users_chats WHERE chat_id=?");
            preparedStatement.setInt(1,chat.getChatId());

            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()){
                chat.addUserToChat(userRepository.findUserById(resultSet.getInt("user_id")));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void chatMessage (Chat chat){
        try {
            PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM messages WHERE chat_id=?");
            preparedStatement.setInt(1,chat.getChatId());

            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()){
                Message message = new Message(userRepository.findUserById(resultSet.getInt("user_id")),
                        resultSet.getString("text_message"));
                chat.setMessage(message);
                chat.setLastMessage(message);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
