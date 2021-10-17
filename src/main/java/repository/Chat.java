package repository;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

public class Chat {
    private List<Message> messages = new ArrayList<>();
    private User user1;
    private User user2;
    private int chatId = 0;
    private Message lastMessage;
    //private String name;

    public Chat(User user1, User user2,int chatId) {
        this.user1 = user1;
        this.user2 = user2;
        this.chatId = chatId;
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

    public List<Message> getMessages() {
        return messages;
    }

    //public String getName() {
    //    return name;
    //}
    //public void setName(String name) {
    //    this.name = name;
    //}

    public void addMessage (User user, String message){
        LocalTime localTime = LocalTime.now();
        LocalDate localDate = LocalDate.now();
        Message message1 = new Message(user,message,localDate,localTime);
        lastMessage = message1;
        messages.add(message1);
    }

    public Message getMessageByNumber (int i){
        return messages.get(i);
    }
}
