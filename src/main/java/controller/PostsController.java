package controller;

import helper.PrintMessage;
import helper.PrintPost;
import repository.Chat;
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
public class PostsController {

    private final ChatRepository chatRepository;
    private final DateTimeFormatter dateTimeFormatterTime = DateTimeFormatter.ofPattern("HH:mm");
    private final DateTimeFormatter dateTimeFormatterDate = DateTimeFormatter.ofPattern("dd MMM", Locale.ENGLISH);

    public PostsController(ChatRepository chatRepository) {
        this.chatRepository = chatRepository;
    }

    @GetMapping("/posts")
    public String printPosts(Model model, @SessionAttribute User user){

        ArrayList<PrintPost> printPosts = printChats(user);

        model.addAttribute("printPosts",printPosts);
        model.addAttribute("title",user.getName());
        model.addAttribute("activePage", "POSTS");
        model.addAttribute("active", false);

        return"posts";
    }

    @GetMapping("/posts/{id}")
    public String printHewMessage(Model model, @SessionAttribute User user, @PathVariable Integer id){

        ArrayList<PrintPost> printPosts = printChats(user);
        ArrayList<PrintMessage> printMessages = printMessages(chatRepository.getByNumberChat(id),user);

        model.addAttribute("activePage", "POSTS");
        model.addAttribute("title",user.getName());
        model.addAttribute("printMessages",printMessages);
        model.addAttribute("printPosts",printPosts);
        model.addAttribute("active", true);

        return "posts";
    }

    @PostMapping("/posts/{id}")
    public String chat(String message,@SessionAttribute User user, @PathVariable Integer id) throws IOException {

        chatRepository.getByNumberChat(id).addMessage(user,message);

        return "redirect:" + id;
    }

    private ArrayList<PrintPost> printChats (User user){

        ArrayList<PrintPost> printPosts = new ArrayList<>();
        List<Chat> chats = chatRepository.findListChatByUser(user);

        for (Chat chat : chats){
            printPosts.add(fillingChat(user,chat));
        }

        return printPosts;
    }

    private ArrayList<PrintMessage> printMessages (Chat chat, User user){

        ArrayList<PrintMessage> printMessages = new ArrayList<>();

        for (int i =0;i<chat.getMessages().size();i++){
            printMessages.add(fillingMessage(i,chat,user));
        }

        return printMessages;
    }

    private PrintPost fillingChat (User user,Chat chat){

            PrintPost printPost;

            if (user == chat.getUser1()) {
                printPost = new PrintPost(chat.getUser2().getName(), chat.getChatId(), "", "");
            } else {
                printPost = new PrintPost(chat.getUser1().getName(), chat.getChatId(), "", "");
            }
            if (chat.getLastMessage() != null) {
                printPost.setDate(chat.getLastMessage().getLocalDate().format(dateTimeFormatterDate));
                printPost.setLastMessage(chat.getLastMessage().getText());
            }

        return printPost;
    }

    private PrintMessage fillingMessage (int i,Chat chat, User user){

        PrintMessage printMessage;

        String date = chat.getMessageByNumber(i).getLocalTime().format(dateTimeFormatterTime) + " | " +
                chat.getMessageByNumber(i).getLocalDate().format(dateTimeFormatterDate);

        if (chat.getMessageByNumber(i).getAuthor() == user){
            printMessage = new PrintMessage(true,chat.getMessageByNumber(i).getText(),date);
        }else {
            printMessage = new PrintMessage(false,chat.getMessageByNumber(i).getText(),date);
        }

        return printMessage;
    }

}
