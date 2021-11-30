package helper;

import org.springframework.jdbc.core.RowMapper;
import repository.Message;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;

public class MessageMapper implements RowMapper<Message> {
    @Override
    public Message mapRow(ResultSet resultSet, int i) throws SQLException {

        LocalDateTime localDateTime = resultSet.getTimestamp("date_message").toLocalDateTime();

        return new Message(resultSet.getInt("user_id"), resultSet.getString("text_message"), localDateTime);

    }
}
