package helper;

import org.springframework.jdbc.core.RowMapper;
import repository.User;

import java.sql.ResultSet;
import java.sql.SQLException;

public class UserMapper implements RowMapper<User> {
    @Override
    public User mapRow(ResultSet resultSet, int i) throws SQLException {
        User user = new User(resultSet.getInt("user_id"), resultSet.getString("user_name"),
                resultSet.getString("user_email"),
                resultSet.getString("user_password"), resultSet.getBoolean("enabled"));
        user.setRole("USER");

        return user;
    }
}
