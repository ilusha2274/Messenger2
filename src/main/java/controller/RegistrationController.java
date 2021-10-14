package controller;

import Repository.User;
import exception.PasswordMismatchException;
import exception.WrongEmailException;
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
public class RegistrationController {

    private final UserRepository userRepository;
    private final TemplateEngine templateEngine;

    public RegistrationController(UserRepository userRepository, TemplateEngine templateEngine) {
        this.userRepository = userRepository;
        this.templateEngine = templateEngine;
    }

    @GetMapping("/registration")
    public String registration(){
        return "registration";
    }

    @PostMapping("/registration")
    public void registration (String email, String name, String password, String twoPassword,
                              HttpServletRequest req, HttpServletResponse resp,
                              String exception) throws IOException {
        User newUser = new User(name,email,password);

        try {
            userRepository.addUser(newUser,twoPassword);
            req.getSession().setAttribute("user", newUser);
            resp.sendRedirect("/home");

        }catch (WrongEmailException | PasswordMismatchException | IOException e){
            Context context = new Context();
            context.setVariable("exception",e.getMessage());
            templateEngine.process("registration",context, resp.getWriter());
        }
    }
}
