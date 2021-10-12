package controller;

import PrintClass.PrintMessage;
import PrintClass.PrintPost;
import Repository.Chat;
import Repository.User;
import interfaceRepository.ChatRepository;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.SessionAttribute;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.WebContext;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

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

    @GetMapping("/chat/*")
    public void printHewMessage(HttpServletRequest req, @SessionAttribute User user,
                                HttpServletResponse resp) throws IOException {
        String contextPath = req.getRequestURI();
        int id = findIdChat(contextPath,resp);
        WebContext webContext = new WebContext(req,resp,servletContext);

        ArrayList<PrintPost> printPosts = printChats(user);
        ArrayList<PrintMessage> printMessages = printMessages(chatRepository.getByNumberChat(id),req);

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

    @PostMapping("/chat/*")
    public void chat(String message,@SessionAttribute User user,
                     HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String contextPath = req.getRequestURI();
        int id = findIdChat(contextPath,resp);
        chatRepository.getByNumberChat(id).addMessage(user,message);
        resp.sendRedirect("/chat/" + id);
    }

    private int findIdChat (String contextPath, HttpServletResponse resp) throws IOException {
        char[] contextPathChar = contextPath.toCharArray();
        int check = 0;
        StringBuilder chatId = new StringBuilder();
        for (char c : contextPathChar) {
            if (check < 2) {
                if (c == '/') {
                    check++;
                }
            } else {
                chatId.append(c);
            }
        }
        try {
            check = Integer.parseInt(chatId.toString());
        }catch (NumberFormatException e){
            resp.sendRedirect("/posts");
        }
        return check;
    }

    private ArrayList<PrintPost> printChats (User user){
        ArrayList<PrintPost> printPosts = new ArrayList<>();
        List<Chat> chat = chatRepository.findListChatByUser(user);
        for(int i =0;i<chat.size();i++){
            PrintPost printPost = new PrintPost();
            if (user == chat.get(i).getUser1()){
                printPost.setNameChat(chat.get(i).getUser2().getName());
                printPost.setIdChat(chat.get(i).getChatId());
            }else {
                printPost.setNameChat(chat.get(i).getUser1().getName());
                printPost.setIdChat(chat.get(i).getChatId());
            }
            printPosts.add(printPost);
        }
        return printPosts;
    }

    private ArrayList<PrintMessage> printMessages (Chat chat,HttpServletRequest req){
        ArrayList<PrintMessage> printMessages = new ArrayList<>();
        User user = (User) req.getSession().getAttribute("user");
        for (int i =0;i<chat.getMessages().size();i++){
            if (chat.getMessageByNumber(i).getAuthor() == user){
                PrintMessage printMessage = new PrintMessage(true,chat.getMessageByNumber(i).getText());
                printMessages.add(printMessage);
            }else {
                PrintMessage printMessage = new PrintMessage(false,chat.getMessageByNumber(i).getText());
                printMessages.add(printMessage);
            }
        }
        return printMessages;
    }

}
