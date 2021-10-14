package Repository;

import java.time.LocalDate;
import java.time.LocalTime;

public class Message {
    private String text;
    private LocalTime localTime;
    private LocalDate localDate;
    private User author;

    public Message (User user,String text,LocalDate localDate,LocalTime localTime){
        this.text = text;
        author = user;
        this.localDate = localDate;
        this.localTime = localTime;
    }

    public LocalDate getLocalDate() {
        return localDate;
    }
    public void setLocalDate(LocalDate localDate) {
        this.localDate = localDate;
    }

    public LocalTime getLocalTime() {
        return localTime;
    }
    public void setLocalTime(LocalTime localTime) {
        this.localTime = localTime;
    }

    public User getAuthor() {
        return author;
    }
    public void setAuthor(User author) {
        this.author = author;
    }

    public String getText() {
        return text;
    }
    public void setText(String text) {
        this.text = text;
    }
}
