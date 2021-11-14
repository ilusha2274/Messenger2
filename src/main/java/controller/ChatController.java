package controller;

import helper.PrintMessage;
import helper.PrintChat;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
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
    public String printChatList(Model model, @AuthenticationPrincipal User user) {

        ArrayList<PrintChat> printChats = (ArrayList<PrintChat>) chatRepository.getPrintChats(user);

        model.addAttribute("printChats", printChats);
        model.addAttribute("title", user.getName());
        model.addAttribute("activePage", "CHAT");
        model.addAttribute("active", false);

        return "chat";
    }

    @GetMapping("/chat/{id}")
    public String printChat(Model model, @AuthenticationPrincipal User user, @PathVariable Integer id) {

        ArrayList<PrintChat> printChats = (ArrayList<PrintChat>) chatRepository.getPrintChats(user);
        ArrayList<PrintMessage> printMessages = printMessages(chatRepository.getListMessageByNumberChat(id), user);

        model.addAttribute("activePage", "CHAT");
        model.addAttribute("title", user.getName());
        model.addAttribute("printMessages", printMessages);
        model.addAttribute("printChats", printChats);
        model.addAttribute("active", true);

        return "chat";
    }

    @PostMapping("/chat/{id}")
    public String chat(String message, @AuthenticationPrincipal User user, @PathVariable Integer id) throws IOException {

        chatRepository.addMessageToChat(message, user, id);

        return "redirect:" + id;
    }

    private ArrayList<PrintMessage> printMessages(List<Message> messages, User user) {

        ArrayList<PrintMessage> printMessages = new ArrayList<>();

        for (int i = 0; i < messages.size(); i++) {
            printMessages.add(fillingMessage(messages.get(i), user));
        }

        return printMessages;
    }

    private PrintMessage fillingMessage(Message message, User user) {

        PrintMessage printMessage;

        String date = message.getLocalDateTime().format(dateTimeFormatterTime) + " | " +
                message.getLocalDateTime().format(dateTimeFormatterDate);

        if (message.getIdAuthor().equals(user.getId())) {
            printMessage = new PrintMessage(true, message.getText(), date);
        } else {
            printMessage = new PrintMessage(false, message.getText(), date);
        }

        return printMessage;
    }

}
