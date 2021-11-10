package repository;

import java.time.LocalDateTime;

public class Message {
    private String text;
    private LocalDateTime localDateTime;
    private User author;
    private Integer idAuthor;

    public Message(User user, String text, LocalDateTime localDateTime) {
        this.text = text;
        author = user;
        this.localDateTime = localDateTime;
    }

    public Message(String text, LocalDateTime localDateTime) {
        this.text = text;
        this.localDateTime = localDateTime;
    }

    public Message(Integer idAuthor,String text,LocalDateTime localDateTime){
        this.idAuthor = idAuthor;
        this.text = text;
        this.localDateTime = localDateTime;
    }

    public Integer getIdAuthor() {
        return idAuthor;
    }

    public void setIdAuthor(Integer idAuthor) {
        this.idAuthor = idAuthor;
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
