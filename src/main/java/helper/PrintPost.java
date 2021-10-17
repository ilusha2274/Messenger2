package helper;

public class PrintPost {
    public String nameChat;
    public int idChat;
    String date;
    String lastMessage;

    public PrintPost(String nameChat, int idChat, String date, String lastMessage) {
        this.nameChat = nameChat;
        this.idChat = idChat;
        this.date = date;
        this.lastMessage = lastMessage;
    }

    public String getDate() {
        return date;
    }
    public void setDate(String date) {
        this.date = date;
    }

    public String getLastMessage() {
        return lastMessage;
    }
    public void setLastMessage(String Message) {
        if (Message.length() > 25){
            Message = Message.substring(0,25);
            Message += "...";
        }
        lastMessage = Message;
    }

    public String getNameChat() {
        return nameChat;
    }
    public void setNameChat(String nameChat) {
        this.nameChat = nameChat;
    }

    public int getIdChat() {
        return idChat;
    }
    public void setIdChat(int idChat) {
        this.idChat = idChat;
    }

}
