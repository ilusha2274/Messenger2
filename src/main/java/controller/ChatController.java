package controller;

import helper.PrintMessage;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.util.HtmlUtils;
import repository.Chat;
import repository.Message;
import repository.User;
import repository.ChatRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import websocket.ChatMessage;

import java.io.IOException;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

@Controller
public class ChatController {

    private final ChatRepository chatRepository;
    private final SimpMessagingTemplate simpMessagingTemplate;
    private final DateTimeFormatter dateTimeFormatterTime = DateTimeFormatter.ofPattern("HH:mm");
    private final DateTimeFormatter dateTimeFormatterDate = DateTimeFormatter.ofPattern("dd MMM", Locale.ENGLISH);

    public ChatController(ChatRepository chatRepository, SimpMessagingTemplate simpMessagingTemplate) {
        this.chatRepository = chatRepository;
        this.simpMessagingTemplate = simpMessagingTemplate;
    }


    @GetMapping("/chat")
    public String printChatList(Model model, @AuthenticationPrincipal User user) {

        ArrayList<Chat> chats = (ArrayList<Chat>) chatRepository.findListChatByUser(user);

        model.addAttribute("printChats", chats);
        model.addAttribute("title", user.getName());
        model.addAttribute("activePage", "CHAT");
        model.addAttribute("active", false);

        return "chat";
    }

    @GetMapping("/chat/{id}")
    public String printChat(Model model, @AuthenticationPrincipal User user, @PathVariable Integer id) {

        model.addAttribute("title", user.getName());

        ArrayList<Chat> chats = (ArrayList<Chat>) chatRepository.findListChatByUser(user);

        if (chatRepository.findUserInChat(id, user)) {

            ArrayList<PrintMessage> printMessages = printMessages(chatRepository.getListMessageByNumberChat(id), user);

            model.addAttribute("activePage", "CHAT");
            model.addAttribute("printMessages", printMessages);
            model.addAttribute("printChats", chats);
            model.addAttribute("active", true);
            model.addAttribute("chatID", id);


        } else {
            model.addAttribute("printChats", chats);
            model.addAttribute("activePage", "CHAT");
            model.addAttribute("active", false);

        }
        return "chat";
    }

    @PostMapping("/chat/{id}")
    public String chat(String message, @AuthenticationPrincipal User user, @PathVariable Integer id) throws IOException {

        Message newMessage = chatRepository.addMessageToChat(message, user, id);
        String date = newMessage.getLocalDateTime().format(dateTimeFormatterTime) + " | " +
                newMessage.getLocalDateTime().format(dateTimeFormatterDate);

        ChatMessage chatMessage = new ChatMessage();
        chatMessage.setTime(HtmlUtils.htmlEscape(date));
        chatMessage.setNameAuthor(HtmlUtils.htmlEscape(user.getName()));
        chatMessage.setContent(HtmlUtils.htmlEscape(message));
        List<User> users = chatRepository.findListUserInChat(id);
        for (User value : users) {
            simpMessagingTemplate.convertAndSendToUser(value.getEmail(), "/queue/messages/" + id, chatMessage);
        }
        //simpMessagingTemplate.convertAndSend("/topic/messages/" + id, chatMessage);

        return "redirect:" + id;
    }

//    @MessageMapping("/chat/{id}")
//    @SendTo("/topic/messages")
//    public ChatMessage sendMessage(String message,@AuthenticationPrincipal User user,@PathVariable Integer id) {
//        chatRepository.addMessageToChat(message, user, id);
//        return new ChatMessage(HtmlUtils.htmlEscape(message));
//    }

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
            printMessage = new PrintMessage(true, message.getText(), date, message.getNameAuthor());
        } else {
            printMessage = new PrintMessage(false, message.getText(), date, message.getNameAuthor());
        }

        return printMessage;
    }

}
