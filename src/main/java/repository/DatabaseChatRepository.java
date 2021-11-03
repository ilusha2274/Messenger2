package repository;

import helper.ChatMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DatabaseChatRepository implements ChatRepository{

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

    @Autowired
    public DatabaseChatRepository(JdbcTemplate jdbcTemplate, UserRepository userRepository) {
        this.jdbcTemplate = jdbcTemplate;
        this.userRepository = userRepository;
    }

    @Override
    public List<Chat> findListChatByUser(User user) {
        List<Chat> chats = new ArrayList<>();
        try {
            PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM users_chats WHERE user_id=?");
            preparedStatement.setInt(1,user.getId());

            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()){
                chats.add(getByNumberChat(resultSet.getInt("chat_id")));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return chats;
    }

    @Override
    public Chat getByNumberChat(int i) {
        return jdbcTemplate.query("SELECT * FROM chats WHERE chat_id=?",
                new Object[]{i},
                new ChatMapper(jdbcTemplate,userRepository)).stream().findAny().orElse(null);
    }

    @Override
    public Chat addChat(List<User> users) {

//        try {
//            PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO chats");
//            preparedStatement.executeUpdate();
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }

        //int id = jdbcTemplate.update("INSERT INTO chats DEFAULT VALUES RETURNING chat_id");
        int id = jdbcTemplate.queryForObject("INSERT INTO chats DEFAULT VALUES RETURNING chat_id",Integer.class);
        for (int i =0;i<users.size();i++) {

//            try {
//                PreparedStatement preparedStatement =
//                        connection.prepareStatement("INSERT INTO users_chats VALUES(?,?)");
//
//                preparedStatement.setInt(1,users.get(i).getId());
//                preparedStatement.setInt(1,0);
//
//                preparedStatement.executeUpdate();
//            } catch (SQLException e) {
//                e.printStackTrace();
//            }

            jdbcTemplate.update("INSERT INTO users_chats (user_id,chat_id) VALUES(?,?)", users.get(i).getId(), id);
        }
        return null;
    }

    @Override
    public void addMessageToChat(String text, User user, Chat chat) {

        int id = jdbcTemplate.queryForObject("INSERT INTO messages (text_message,chat_id,user_id) VALUES(?,?,?) RETURNING message_id",
                Integer.class,
                text, chat.getChatId(),user.getId());

        jdbcTemplate.update("UPDATE chats SET chat_last_message=? WHERE chat_id=?",id,chat.getChatId());

    }
}
