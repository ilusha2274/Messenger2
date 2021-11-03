package repository;

import java.sql.Timestamp;

public class Message {
    private String text;
    private Timestamp timestamp;
    private User author;

    public Message(User user, String text) {
        this.text = text;
        author = user;
        //this.timestamp = timestamp;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public Timestamp getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Timestamp timestamp) {
        this.timestamp = timestamp;
    }

    public User getAuthor() {
        return author;
    }

    public void setAuthor(User author) {
        this.author = author;
    }
}
