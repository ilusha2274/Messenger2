package controller;

import repository.User;
import exception.WrongLoginPasswordException;
import repository.UserRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import javax.servlet.http.HttpSession;

@Controller
public class LogInController {

    private final UserRepository userRepository;

    public LogInController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @GetMapping("/login")
    public String printLogin() {
        return "login";
    }

    @PostMapping("/login")
    public String login(HttpSession session, Model model, String email, String password) {

        try {
            User userLogIn = userRepository.logInUser(email, password);
            session.setAttribute("user", userLogIn);
            return "redirect:home";
        } catch (WrongLoginPasswordException e) {
            model.addAttribute("exception", e.getMessage());
            return "login";
        }
    }

}
