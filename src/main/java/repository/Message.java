package repository;

import java.time.LocalDateTime;

public class Message {
    private String text;
    private LocalDateTime localDateTime;
    private User author;

    public Message(User user, String text, LocalDateTime localDateTime) {
        this.text = text;
        author = user;
        this.localDateTime = localDateTime;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public LocalDateTime getLocalDateTime() {
        return localDateTime;
    }

    public void setLocalDateTime(LocalDateTime localDateTime) {
        this.localDateTime = localDateTime;
    }

    public User getAuthor() {
        return author;
    }

    public void setAuthor(User author) {
        this.author = author;
    }
}
