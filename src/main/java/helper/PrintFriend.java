package helper;

public class PrintFriend {

    private Integer chatId;
    private String nameFriend;

    public PrintFriend(Integer chatId, String nameFriend) {
        this.chatId = chatId;
        this.nameFriend = nameFriend;
    }

    public Integer getChatId() {
        return chatId;
    }

    public void setChatId(Integer chatId) {
        this.chatId = chatId;
    }

    public String getNameFriend() {
        return nameFriend;
    }

    public void setNameFriend(String nameFriend) {
        this.nameFriend = nameFriend;
    }
}
