package repository;

import java.util.ArrayList;
import java.util.List;

public class Chat {
    private int chatId;
    private List<Message> messages = new ArrayList<>();
    private User user1;
    private User user2;
    private Message lastMessage;

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

    public User getUser1() {
        return user1;
    }

    public void setUser1(User user1) {
        this.user1 = user1;
    }

    public User getUser2() {
        return user2;
    }

    public void setUser2(User user2) {
        this.user2 = user2;
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
}
