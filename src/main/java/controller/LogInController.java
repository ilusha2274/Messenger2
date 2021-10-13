package controller;

import Repository.User;
import exception.WrongLoginPasswordException;
import interfaceRepository.UserRepository;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Controller
public class LogInController {

    private final UserRepository userRepository;
    private final TemplateEngine templateEngine;

    public LogInController(UserRepository userRepository, TemplateEngine templateEngine) {
        this.userRepository = userRepository;
        this.templateEngine = templateEngine;
    }

    @GetMapping("/login")
    public String printLogin(){
        return "login";
    }

    @PostMapping("/login")
    public void login(HttpServletRequest req, String email, String password, String exception,
                      HttpServletResponse resp) throws IOException {

        try {
            User userLogIn = userRepository.logInUser(email,password);
            req.getSession().setAttribute("user", userLogIn);
            resp.sendRedirect("/home");
        } catch (WrongLoginPasswordException e) {
            Context context = new Context();
            context.setVariable("exception",e.getMessage());
            templateEngine.process("login",context, resp.getWriter());
        }
    }

}
