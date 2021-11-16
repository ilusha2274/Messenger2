package helper;

import org.springframework.jdbc.core.RowMapper;
import repository.Chat;

import java.sql.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

public class ChatMapper implements RowMapper<Chat> {

    private final DateTimeFormatter dateTimeFormatterDate = DateTimeFormatter.ofPattern("dd MMM", Locale.ENGLISH);

    @Override
    public Chat mapRow(ResultSet resultSet, int i) throws SQLException {
        Chat chat = new Chat(resultSet.getString("username"),
                resultSet.getInt("chat_id"),"","");

        if (resultSet.getString("text_message") != null){
            LocalDateTime localDateTime = resultSet.getTimestamp("date_message").toLocalDateTime();
            chat.setTextLastMessage(resultSet.getString("text_message"));
            chat.setDateLastMessage(localDateTime.format(dateTimeFormatterDate));
        }

        return chat;
    }
}
