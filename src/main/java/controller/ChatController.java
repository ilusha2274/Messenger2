package controller;

import helper.PrintMessage;
import helper.PrintChat;
import repository.Chat;
import repository.Message;
import repository.User;
import repository.ChatRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

@Controller
public class ChatController {

    private final ChatRepository chatRepository;
    private final DateTimeFormatter dateTimeFormatterTime = DateTimeFormatter.ofPattern("HH:mm");
    private final DateTimeFormatter dateTimeFormatterDate = DateTimeFormatter.ofPattern("dd MMM", Locale.ENGLISH);

    public ChatController(ChatRepository chatRepository) {
        this.chatRepository = chatRepository;
    }


    @GetMapping("/chat")
    public String printChatList(Model model, @SessionAttribute User user) {

        ArrayList<PrintChat> printChats = printChats(user);

        model.addAttribute("printChats", printChats);
        model.addAttribute("title", user.getName());
        model.addAttribute("activePage", "CHAT");
        model.addAttribute("active", false);

        return "chat";
    }

    @GetMapping("/chat/{id}")
    public String printChat(Model model, @SessionAttribute User user, @PathVariable Integer id) {

        ArrayList<PrintChat> printChats = printChats(user);
        ArrayList<PrintMessage> printMessages = printMessages(chatRepository.getListMessageByNumberChat(id), user);

        model.addAttribute("activePage", "CHAT");
        model.addAttribute("title", user.getName());
        model.addAttribute("printMessages", printMessages);
        model.addAttribute("printChats", printChats);
        model.addAttribute("active", true);

        return "chat";
    }

    @PostMapping("/chat/{id}")
    public String chat(String message, @SessionAttribute User user, @PathVariable Integer id) throws IOException {

        chatRepository.addMessageToChat(message, user, id);

        return "redirect:" + id;
    }

    private ArrayList<PrintChat> printChats(User user) {

        ArrayList<PrintChat> printChats = new ArrayList<>();
        List<Chat> chats = chatRepository.findListChatByUser(user);

        for (Chat chat : chats) {
            printChats.add(fillingChat(user, chat));
        }

        return printChats;
    }

    private ArrayList<PrintMessage> printMessages(List<Message> messages, User user) {

        ArrayList<PrintMessage> printMessages = new ArrayList<>();

        for (int i = 0; i < messages.size(); i++) {
            printMessages.add(fillingMessage(messages.get(i), user));
        }

        return printMessages;
    }

    private PrintChat fillingChat(User user, Chat chat) {

        PrintChat printChat;
        User user1 = chat.getUser1();
        User user2 = chat.getUser2();

        if (user1.getId() == user2.getId()) {
            printChat = new PrintChat(user.getName(), chat.getChatId(), "", "");
        } else {
            if (user.getId() == user1.getId()) {
                printChat = new PrintChat(user2.getName(), chat.getChatId(), "", "");
            } else {
                printChat = new PrintChat(user1.getName(), chat.getChatId(), "", "");
            }
        }

        if (chat.getLastMessage() != null) {
            printChat.setDate(chat.getLastMessage().getLocalDateTime().format(dateTimeFormatterDate));
            printChat.setLastMessage(chat.getLastMessage().getText());
        }

        return printChat;
    }

    private PrintMessage fillingMessage(Message message, User user) {

        PrintMessage printMessage;

        String date = message.getLocalDateTime().format(dateTimeFormatterTime) + " | " +
                message.getLocalDateTime().format(dateTimeFormatterDate);

        if (message.getAuthor().getId() == user.getId()) {
            printMessage = new PrintMessage(true, message.getText(), date);
        } else {
            printMessage = new PrintMessage(false, message.getText(), date);
        }

        return printMessage;
    }

}
