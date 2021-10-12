package PrintClass;

public class PrintMessage {
    boolean author;
    String message;

    public PrintMessage(boolean author, String message) {
        this.author = author;
        this.message = message;
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
