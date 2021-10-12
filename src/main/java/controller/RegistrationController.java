package controller;

import Repository.User;
import exception.PasswordMismatchException;
import exception.WrongEmailException;
import interfaceRepository.UserRepository;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.SessionAttribute;
import org.thymeleaf.context.Context;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Controller
public class RegistrationController {

    private final UserRepository userRepository;

    public RegistrationController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @GetMapping("/registration")
    public String registration(){
        return "registration";
    }

    @PostMapping("/registration")
    public void registration (String email, String name, String password, String twoPassword,
                              HttpServletRequest req, HttpServletResponse resp,
                              String exception){
        User newUser = new User(name,email,password);

        try {
            userRepository.addUser(newUser,twoPassword);
            req.getSession().setAttribute("user", newUser);
            resp.sendRedirect("/home");

        }catch (WrongEmailException | PasswordMismatchException | IOException e){
            exception = e.getMessage();
        }
    }
}
