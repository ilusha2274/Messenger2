package repository;

import java.util.ArrayList;
import java.util.List;

public class Chat {
    private int chatId;
    private List<Message> messages = new ArrayList<>();
    private List<User> users = new ArrayList<>();
    private Message lastMessage;
    private String nameChat;

    public List<Message> getMessages() {
        return messages;
    }

    public void setMessage(Message message) {
        messages.add(message);
    }

    public Message getLastMessage() {
        return lastMessage;
    }

    public void setLastMessage(Message lastMessage) {
        this.lastMessage = lastMessage;
    }

    public List<User> getUsers() {
        return users;
    }

    public void setUsers(List<User> users) {
        this.users = users;
    }

    public int getChatId() {
        return chatId;
    }

    public void setChatId(int chatId) {
        this.chatId = chatId;
    }

    public Message getMessageByNumber(int i) {
        return messages.get(i);
    }

    public String getNameChat() {
        return nameChat;
    }

    public void setNameChat(String nameChat) {
        this.nameChat = nameChat;
    }
}
