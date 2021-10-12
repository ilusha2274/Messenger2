package controller;

import PrintClass.PrintPost;
import Repository.Chat;
import Repository.User;
import interfaceRepository.ChatRepository;
import interfaceRepository.UserRepository;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.SessionAttribute;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;
import org.thymeleaf.context.WebContext;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Controller
public class BlockMenuController {

    private final TemplateEngine templateEngine;
    private final ServletContext servletContext;
    private final ChatRepository chatRepository;

    public BlockMenuController(TemplateEngine templateEngine, ServletContext servletContext, ChatRepository chatRepository) {
        this.templateEngine = templateEngine;
        this.servletContext = servletContext;
        this.chatRepository = chatRepository;
    }

    Context context = new Context();

    @GetMapping("/home")
    public void printHome(@SessionAttribute User user, HttpServletResponse resp) throws IOException {
        context.setVariable("profile",false);
        context.setVariable("posts",false);
        context.setVariable("settings",false);
        context.setVariable("newmessage",false);
        context.setVariable("exit",false);
        context.setVariable("title",user.getName());
        templateEngine.process("home",context, resp.getWriter());
    }

    @GetMapping("/profile")
    public void printProfile(@SessionAttribute User user, HttpServletResponse resp) throws IOException {
        context.setVariable("profile",true);
        context.setVariable("posts",false);
        context.setVariable("settings",false);
        context.setVariable("newmessage",false);
        context.setVariable("exit",false);
        context.setVariable("title",user.getName());
        templateEngine.process("profile",context, resp.getWriter());
    }

    @GetMapping("/posts")
    public void printPosts(HttpServletRequest req, @SessionAttribute User user, HttpServletResponse resp) throws IOException{
        WebContext webContext = new WebContext(req,resp,servletContext);
        webContext.setVariable("profile",false);
        webContext.setVariable("posts",true);
        webContext.setVariable("settings",false);
        webContext.setVariable("newmessage",false);
        webContext.setVariable("exit",false);
        ArrayList<PrintPost> printPosts = printChats(user);

        webContext.setVariable("printPosts",printPosts);
        webContext.setVariable("title",user.getName());

        templateEngine.process("posts",webContext, resp.getWriter());
    }

    @GetMapping("/settings")
    public void printSettings(@SessionAttribute User user, HttpServletResponse resp) throws IOException{
        context.setVariable("profile",false);
        context.setVariable("posts",false);
        context.setVariable("settings",true);
        context.setVariable("newmessage",false);
        context.setVariable("exit",false);
        context.setVariable("title",user.getName());
        templateEngine.process("settings",context, resp.getWriter());
    }

    @GetMapping("/exit")
    public void printExit(HttpServletRequest req, HttpServletResponse resp) throws IOException{
        req.getSession().invalidate();
        resp.sendRedirect("/login");
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
}
