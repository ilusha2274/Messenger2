package websocket;

public class ChatMessage {
    private String content;
    private String time;

    public ChatMessage(){
    }

    public ChatMessage(String content, String time) {
        this.content = content;
        this.time = time;
    }

    public ChatMessage (String content){
        this.content = content;
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