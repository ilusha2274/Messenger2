package websocket;

public class ChatMessage {
    private String content;
    private String time;
    private String nameAuthor;

    public ChatMessage() {
    }

    public ChatMessage(String content, String time,String nameAuthor) {
        this.content = content;
        this.time = time;
        this.nameAuthor = nameAuthor;
    }

    public ChatMessage(String content) {
        this.content = content;
    }

    public String getNameAuthor() {
        return nameAuthor;
    }

    public void setNameAuthor(String nameAuthor) {
        this.nameAuthor = nameAuthor;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }
}