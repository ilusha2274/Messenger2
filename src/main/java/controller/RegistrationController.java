package controller;

import repository.User;
import exception.PasswordMismatchException;
import exception.WrongEmailException;
import repository.UserRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import javax.servlet.http.HttpSession;
import java.sql.SQLException;

@Controller
public class RegistrationController {

    private final UserRepository userRepository;

    public RegistrationController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @GetMapping("/registration")
    public String registration() {
        return "registration";
    }

    // Не добавляет в сессию а перекидывает налогин.
    @PostMapping("/registration")
    public String registration(HttpSession session, String email, String name, String password,
                               String twoPassword, Model model) {

        User newUser = new User(name, email, password);

        try {
            User user = userRepository.addUser(newUser, twoPassword);
            session.setAttribute("user", user);
            return "redirect:home";

        } catch (WrongEmailException | PasswordMismatchException | SQLException e) {
            model.addAttribute("exception", e.getMessage());
            return "registration";
        }
    }
}
