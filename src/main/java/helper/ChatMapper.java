package helper;

import org.springframework.jdbc.core.RowMapper;
import repository.Chat;
import repository.Message;
import repository.User;

import java.sql.*;
import java.time.LocalDateTime;

public class ChatMapper implements RowMapper<Chat> {

    @Override
    public Chat mapRow(ResultSet resultSet, int i) throws SQLException {
        Chat chat = new Chat();
        chat.setChatId(resultSet.getInt("chat_id"));

        User user1 = new User(resultSet.getInt(5),resultSet.getString(6),
                resultSet.getString(7),
                resultSet.getString(8));

        User user2 = new User(resultSet.getInt(9),resultSet.getString(10),
                resultSet.getString(11),
                resultSet.getString(12));

        Message message = null;
        if (resultSet.getString("text_message") != null){
            if (user1.getId() == resultSet.getInt(15)){
                LocalDateTime localDateTime = resultSet.getTimestamp("date_message").toLocalDateTime();
                message = new Message(user1,resultSet.getString("text_message"),localDateTime);
            }else {
                LocalDateTime localDateTime = resultSet.getTimestamp("date_message").toLocalDateTime();
                message = new Message(user2,resultSet.getString("text_message"),localDateTime);
            }
        }else {
        }

        chat.setUser1(user1);
        chat.setUser2(user2);
        chat.setLastMessage(message);

        return chat;
    }
}
