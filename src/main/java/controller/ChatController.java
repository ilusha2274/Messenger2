package controller;

import PrintClass.PrintMessage;
import PrintClass.PrintPost;
import Repository.Chat;
import Repository.User;
import CollectionRepository.ChatRepository;
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


    public ChatController(ChatRepository chatRepository) {
        this.chatRepository = chatRepository;
    }

    @GetMapping("/chat/{id}")
    public String printHewMessage(Model model, @SessionAttribute User user, @PathVariable Integer id){

        ArrayList<PrintPost> printPosts = printChats(user);
        ArrayList<PrintMessage> printMessages = printMessages(chatRepository.getByNumberChat(id),user);

        model.addAttribute("activePage", "POSTS");
        model.addAttribute("title",user.getName());
        model.addAttribute("printMessages",printMessages);
        model.addAttribute("printPosts",printPosts);

        return "chat";
    }

    @PostMapping("/chat/{id}")
    public String chat(String message,@SessionAttribute User user, @PathVariable Integer id) throws IOException {

        chatRepository.getByNumberChat(id).addMessage(user,message);

        return "redirect:" + id;
    }

    private ArrayList<PrintPost> printChats (User user){

        ArrayList<PrintPost> printPosts = new ArrayList<>();
        List<Chat> chats = chatRepository.findListChatByUser(user);
        DateTimeFormatter dateTimeFormatterDate = DateTimeFormatter.ofPattern("dd MMM", Locale.ENGLISH);

        for(int i =0;i<chats.size();i++){

            PrintPost printPost;
            if (user == chats.get(i).getUser1()){
                printPost = new PrintPost(chats.get(i).getUser2().getName(), chats.get(i).getChatId(), "", "");
            }else {
                printPost = new PrintPost(chats.get(i).getUser1().getName(), chats.get(i).getChatId(), "", "");
            }
            if (chats.get(i).getLastMessage() != null){
                printPost.setDate(chats.get(i).getLastMessage().getLocalDate().format(dateTimeFormatterDate));
                printPost.setLastMessage(chats.get(i).getLastMessage().getText());
            }
            printPosts.add(printPost);
        }
        return printPosts;
    }

    private ArrayList<PrintMessage> printMessages (Chat chat, User user){
        ArrayList<PrintMessage> printMessages = new ArrayList<>();

        DateTimeFormatter dateTimeFormatterTime = DateTimeFormatter.ofPattern("HH:mm");
        DateTimeFormatter dateTimeFormatterDate = DateTimeFormatter.ofPattern("dd MMM", Locale.ENGLISH);

        for (int i =0;i<chat.getMessages().size();i++){

            String date = chat.getMessageByNumber(i).getLocalTime().format(dateTimeFormatterTime) + " | " +
                    chat.getMessageByNumber(i).getLocalDate().format(dateTimeFormatterDate);

            if (chat.getMessageByNumber(i).getAuthor() == user){
                PrintMessage printMessage = new PrintMessage(true,chat.getMessageByNumber(i).getText(),date);
                printMessages.add(printMessage);
            }else {
                PrintMessage printMessage = new PrintMessage(false,chat.getMessageByNumber(i).getText(),date);
                printMessages.add(printMessage);
            }
        }
        return printMessages;
    }

}
