package helper;

import org.springframework.jdbc.core.RowMapper;
import repository.Chat;
import repository.Message;

import java.sql.*;
import java.time.LocalDateTime;

public class ChatMapper implements RowMapper<Chat> {

    @Override
    public Chat mapRow(ResultSet resultSet, int i) throws SQLException {
        Chat chat = new Chat();
        chat.setChatId(resultSet.getInt("chat_id"));
        chat.setNameChat(resultSet.getString(4));

        if (resultSet.getString("text_message") != null){
                LocalDateTime localDateTime = resultSet.getTimestamp("date_message").toLocalDateTime();
            Message message = new Message(resultSet.getString("text_message"),localDateTime);
            chat.setLastMessage(message);
        }

        return chat;
    }
}
