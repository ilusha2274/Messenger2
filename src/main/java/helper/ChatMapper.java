package helper;

import org.springframework.jdbc.core.RowMapper;
import repository.Chat;

import java.sql.ResultSet;
import java.sql.SQLException;

public class ChatMapper implements RowMapper<Chat> {

    @Override
    public Chat mapRow(ResultSet resultSet, int i) throws SQLException {
        Chat chat = new Chat(resultSet.getString("chatname"),
                resultSet.getInt("chat_id"), "", "");

        return chat;
    }
}
