package helper;

import org.springframework.jdbc.core.RowMapper;
import repository.Message;
import repository.User;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;

public class MessageMapper implements RowMapper<Message> {
    @Override
    public Message mapRow(ResultSet resultSet, int i) throws SQLException {
        User user = new User(resultSet.getInt("user_id"),resultSet.getString("user_name"),
                resultSet.getString("user_email"),
                resultSet.getString("user_password"));

        LocalDateTime localDateTime = resultSet.getTimestamp("date_message").toLocalDateTime();

        return new Message(user,resultSet.getString("text_message"),localDateTime);

    }
}
