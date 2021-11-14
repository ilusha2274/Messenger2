package helper;

import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

public class PrintChatMapper implements RowMapper<PrintChat> {

    private final DateTimeFormatter dateTimeFormatterDate = DateTimeFormatter.ofPattern("dd MMM", Locale.ENGLISH);

    @Override
    public PrintChat mapRow(ResultSet resultSet, int i) throws SQLException {
        PrintChat printChat = new PrintChat(resultSet.getString(4),
                resultSet.getInt("chat_id"),"","");

        if (resultSet.getString("text_message") != null){
            LocalDateTime localDateTime = resultSet.getTimestamp("date_message").toLocalDateTime();
            printChat.setLastMessage(resultSet.getString("text_message"));
            printChat.setDate(localDateTime.format(dateTimeFormatterDate));
        }

        return printChat;
    }
}
