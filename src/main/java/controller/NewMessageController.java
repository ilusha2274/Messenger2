package controller;

import repository.User;
import repository.ChatRepository;
import repository.UserRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.SessionAttribute;

import java.util.ArrayList;
import java.util.List;

@Controller
public class NewMessageController {

    private final ChatRepository chatRepository;
    private final UserRepository userRepository;

    public NewMessageController(ChatRepository chatRepository, UserRepository userRepository) {
        this.chatRepository = chatRepository;
        this.userRepository = userRepository;
    }

    @GetMapping("/newmessage")
    public String printHewMessage(@SessionAttribute User user, Model model) {

        model.addAttribute("activePage", "NEWMESSAGES");
        model.addAttribute("title", user.getName());

        return "newmessage";
    }

    @PostMapping("/newmessage")
    public String newMessage(String email, @SessionAttribute User user, Model model) {

        if (userRepository.findUserByEmail(email) != null) {
            List<User> users = new ArrayList<>();
            users.add(user);
            users.add(userRepository.findUserByEmail(email));
            model.addAttribute("activePage", "CHAT");
            chatRepository.addChat(users);
            return "redirect:chat";
        } else {
            model.addAttribute("activePage", "NEWMESSAGES");
            model.addAttribute("exception", "Пользователь не найден");
            return "newmessage";
        }
    }

}
