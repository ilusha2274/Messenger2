package CollectionRepository;

import Repository.Chat;
import Repository.User;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CollectionChatRepository implements ChatRepository {

    private List<Chat> chats = new ArrayList<>();
    private Map<User, List<Chat>> usersByChat = new HashMap<>();
    private int chatId =0;

    @Override
    public List<Chat> findListChatByUser(User user) {
        if (usersByChat.get(user) == null){
            List<Chat> chats = new ArrayList<>();
            return chats;
        }
        return usersByChat.get(user);
    }

    @Override
    public Chat getByNumberChat(int i) {
        return chats.get(i);
    }

    @Override
    public Chat addChat(User user1, User user2) {
        Chat chat = new Chat(user1, user2,chatId);
        chats.add(chat);
        if (user1 == user2){
            addChatForOne(user1,chat);
        }else {
            addChatForTwo(user1,user2,chat);
        }
        chatId++;
        return chat;
    }

    private void addChatForTwo (User user1, User user2, Chat chat){
        if (usersByChat.get(user1) == null){
            List<Chat> chats1 = new ArrayList<>();
            chats1.add(chat);
            usersByChat.put(user1,chats1);
        }else {
            List<Chat> chats1 = usersByChat.get(user1);
            chats1.add(chat);
            usersByChat.put(user1,chats1);
        }

        if (usersByChat.get(user2) == null){
            List<Chat> chats2 = new ArrayList<>();
            chats2.add(chat);
            usersByChat.put(user2,chats2);
        }else {
            List<Chat> chats2 = usersByChat.get(user2);
            chats2.add(chat);
            usersByChat.put(user2,chats2);
        }
    }

    private void addChatForOne (User user1, Chat chat){
        if (usersByChat.get(user1) == null){
            List<Chat> chats1 = new ArrayList<>();
            chats1.add(chat);
            usersByChat.put(user1,chats1);
        }else {
            List<Chat> chats1 = usersByChat.get(user1);
            chats1.add(chat);
            usersByChat.put(user1,chats1);
        }
    }
}