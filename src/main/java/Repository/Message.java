package Repository;

import java.util.Date;

public class Message {
    private String text;
    //private Date date;
    private User author;

    public Message (User user,String text){
        this.text = text;
        author = user;
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
