package controller;

import Repository.User;
import exception.PasswordMismatchException;
import exception.WrongEmailException;
import CollectionRepository.UserRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import javax.servlet.http.HttpServletRequest;

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
    public String registration (String email, String name, String password, String twoPassword,
                                HttpServletRequest req, Model model){

        User newUser = new User(name,email,password);

        try {
            userRepository.addUser(newUser,twoPassword);
            req.getSession().setAttribute("user", newUser);
            return "redirect:home";

        }catch (WrongEmailException | PasswordMismatchException e){
            model.addAttribute("exception",e.getMessage());
            return "registration";
        }
    }
}
