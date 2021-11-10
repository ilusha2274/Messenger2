package repository;

import java.util.List;

public interface ChatRepository {
    List<Chat> findListChatByUser(User user);

    Chat getByNumberChat(int i);

    Chat addChat(List<User> users,String chatType);

    void addMessageToChat(String text, User user, int chatId);

    List<Message> getListMessageByNumberChat (int i);
}
