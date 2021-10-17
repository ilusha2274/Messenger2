package repository;

import java.util.List;

public interface ChatRepository {
    List<Chat> findListChatByUser (User user);
    Chat getByNumberChat (int i);
    Chat addChat (User user1, User user2);
}
