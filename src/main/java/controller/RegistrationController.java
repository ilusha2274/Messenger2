package controller;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
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
    private final AuthenticationManager authenticationManager;

    public RegistrationController(UserRepository userRepository, AuthenticationManager authenticationManager) {
        this.userRepository = userRepository;
        this.authenticationManager = authenticationManager;
    }

    @GetMapping("/registration")
    public String registration() {
        return "registration";
    }

    @PostMapping("/registration")
    public String registration(HttpSession session, String email, String name, String password,
                               String twoPassword, Model model) {

        User newUser = new User(name, email, password);

        try {
            User user = userRepository.addUser(newUser, twoPassword);
            Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(email, password));
            SecurityContextHolder.getContext().setAuthentication(authentication);
            return "redirect:home";

        } catch (WrongEmailException | PasswordMismatchException | SQLException e) {
            model.addAttribute("exception", e.getMessage());
            return "registration";
        }
    }
}
