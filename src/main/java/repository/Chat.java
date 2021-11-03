package repository;

import java.util.ArrayList;
import java.util.List;

public class Chat {
    private int chatId;
    private List<Message> messages = new ArrayList<>();
    private List<User> users = new ArrayList<>();
    private Message lastMessage;

    public List<Message> getMessages() {
        return messages;
    }

    public void setMessage(Message message) {
        messages.add(message);
    }

    public List<User> getUsers() {
        return users;
    }

    public void setUsers(List<User> users) {
        this.users = users;
    }

    public Message getLastMessage() {
        return lastMessage;
    }

    public void setLastMessage(Message lastMessage) {
        this.lastMessage = lastMessage;
    }

    public int getChatId() {
        return chatId;
    }

    public void setChatId(int chatId) {
        this.chatId = chatId;
    }

    public void addUserToChat(User user) {
        users.add(user);
    }

    public Message getMessageByNumber(int i) {
        return messages.get(i);
    }
}
