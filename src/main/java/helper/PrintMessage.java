package helper;

public class PrintMessage {
    boolean author;
    String message;
    String date;
    String nameAuthor;

    public PrintMessage(boolean author, String message, String date,String nameAuthor) {
        this.author = author;
        this.message = message;
        this.date = date;
        this.nameAuthor = nameAuthor;
    }

    public String getNameAuthor() {
        return nameAuthor;
    }

    public void setNameAuthor(String nameAuthor) {
        this.nameAuthor = nameAuthor;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public boolean isAuthor() {
        return author;
    }

    public void setAuthor(boolean author) {
        this.author = author;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
