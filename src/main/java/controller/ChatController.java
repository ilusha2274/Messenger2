package controller;

import PrintClass.PrintMessage;
import PrintClass.PrintPost;
import Repository.Chat;
import Repository.User;
import interfaceRepository.ChatRepository;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.WebContext;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

@Controller
public class ChatController {

    private final TemplateEngine templateEngine;
    private final ChatRepository chatRepository;
    private final ServletContext servletContext;


    public ChatController(TemplateEngine templateEngine, ChatRepository chatRepository, ServletContext servletContext) {
        this.templateEngine = templateEngine;
        this.chatRepository = chatRepository;
        this.servletContext = servletContext;
    }

    @GetMapping("/chat/{id}")
    public void printHewMessage(HttpServletRequest req, @SessionAttribute User user,
                                HttpServletResponse resp, @PathVariable Integer id) throws IOException {

        WebContext webContext = new WebContext(req,resp,servletContext);

        ArrayList<PrintPost> printPosts = printChats(user);
        ArrayList<PrintMessage> printMessages = printMessages(chatRepository.getByNumberChat(id),user);

        webContext.setVariable("profile",false);
        webContext.setVariable("posts",true);
        webContext.setVariable("settings",false);
        webContext.setVariable("newmessage",false);
        webContext.setVariable("exit",false);
        webContext.setVariable("title",user.getName());
        webContext.setVariable("printMessages",printMessages);
        webContext.setVariable("printPosts",printPosts);

        templateEngine.process("chat",webContext, resp.getWriter());
    }

    @PostMapping("/chat/{id}")
    public void chat(String message,@SessionAttribute User user,
                     HttpServletRequest req, HttpServletResponse resp,@PathVariable Integer id) throws IOException {

        chatRepository.getByNumberChat(id).addMessage(user,message);
        resp.sendRedirect("/chat/" + id);
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
