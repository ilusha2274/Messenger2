package controller;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import repository.User;
import repository.ChatRepository;
import repository.UserRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

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
    public String printHewMessage(@AuthenticationPrincipal User user, Model model) {

        model.addAttribute("activePage", "NEWMESSAGES");
        model.addAttribute("title", user.getName());

        return "newmessage";
    }

    @PostMapping("/newmessage")
    public String newMessage(String email, String nameChat, @AuthenticationPrincipal User user, Model model) {

        String result = "redirect:chat";
        if (nameChat != null) {
            addGroupChat(nameChat, user, model);
        }

        if (email != null) {
            result = addPrivateChat(email, user, model);
        }
        return result;
    }

    private String addPrivateChat(String email, User user, Model model) {
        User user2 = userRepository.findUserByEmail(email);
        List<User> users = new ArrayList<>();
        users.add(user);
        users.add(user2);

        if (user2 != null) {
            model.addAttribute("activePage", "CHAT");
            model.addAttribute("title", user.getName());
            chatRepository.addChat(users, "private");
            return "redirect:chat";
        } else {
            model.addAttribute("activePage", "NEWMESSAGES");
            model.addAttribute("exception", "Пользователь не найден");
            model.addAttribute("title", user.getName());
            return "newmessage";
        }
    }

    private String addGroupChat(String nameChat, User user, Model model) {
        chatRepository.addGroupChat(nameChat, "group", user);
        model.addAttribute("activePage", "CHAT");
        model.addAttribute("title", user.getName());
        return "redirect:chat";
    }
}
