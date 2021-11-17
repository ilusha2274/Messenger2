package controller;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
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
    private final AuthenticationManager authenticationManager;

    public LogInController(UserRepository userRepository, AuthenticationManager authenticationManager) {
        this.userRepository = userRepository;
        this.authenticationManager = authenticationManager;
    }

    @GetMapping("/login")
    public String printLogin() {
        return "login";
    }

    @GetMapping("loginerror")
    public String printLoginError(Model model){
        model.addAttribute("exception", "Неверное имя пользователя или пароль");
        return "login";
    }

    @PostMapping("/loginerror")
    public String login(HttpSession session, Model model, String email, String password) {

        try {
            User userLogIn = userRepository.logInUser(email, password);
            Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(email,password));
            SecurityContextHolder.getContext().setAuthentication(authentication);
            return "redirect:home";
        } catch (WrongLoginPasswordException e) {
            model.addAttribute("exception", e.getMessage());
            return "login";
        }
    }

}
