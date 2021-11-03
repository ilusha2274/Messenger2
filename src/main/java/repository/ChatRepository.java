package repository;

import java.util.List;

public interface ChatRepository {
    List<Chat> findListChatByUser(User user);

    Chat getByNumberChat(int i);

    Chat addChat(List<User> users);

    void addMessageToChat(String text, User user, Chat chat);
}
