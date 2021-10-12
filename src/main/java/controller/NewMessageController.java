package controller;

import Repository.User;
import interfaceRepository.ChatRepository;
import interfaceRepository.UserRepository;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.SessionAttribute;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Controller
public class NewMessageController {

    private final TemplateEngine templateEngine;
    private final ChatRepository chatRepository;
    private final UserRepository userRepository;

    public NewMessageController(TemplateEngine templateEngine, ChatRepository chatRepository, UserRepository userRepository) {
        this.templateEngine = templateEngine;
        this.chatRepository = chatRepository;
        this.userRepository = userRepository;
    }

    Context context = new Context();

    @GetMapping("/newmessage")
    public void printHewMessage(@SessionAttribute User user, HttpServletResponse resp) throws IOException {
        context.setVariable("profile",false);
        context.setVariable("posts",false);
        context.setVariable("settings",false);
        context.setVariable("newmessage",true);
        context.setVariable("exit",false);
        context.setVariable("title",user.getName());
        templateEngine.process("newmessage",context, resp.getWriter());
    }

    @PostMapping("/newmessage")
    public void newMessage(String email, @SessionAttribute User user,HttpServletResponse resp) throws IOException {
        if (userRepository.findUserByEmail(email)!= null){
            context.setVariable("profile",false);
            context.setVariable("posts",true);
            context.setVariable("settings",false);
            context.setVariable("newmessage",false);
            context.setVariable("exit",false);
            chatRepository.addChat(user,userRepository.findUserByEmail(email));
            resp.sendRedirect("/posts");
        }else {
            context.setVariable("profile",false);
            context.setVariable("posts",false);
            context.setVariable("settings",false);
            context.setVariable("newmessage",true);
            context.setVariable("exit",false);
            context.setVariable("exception","Пользователь не найден");
            templateEngine.process("newmessage",context, resp.getWriter());
        }
    }

}
