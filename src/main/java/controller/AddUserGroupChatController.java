package controller;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import repository.Chat;
import repository.ChatRepository;
import repository.User;
import repository.UserRepository;

@Controller
public class AddUserGroupChatController {

    private final ChatRepository chatRepository;
    private final UserRepository userRepository;

    public AddUserGroupChatController(ChatRepository chatRepository, UserRepository userRepository) {
        this.chatRepository = chatRepository;
        this.userRepository = userRepository;
    }

    @GetMapping("/addUserGroupChat")
    public String printHewMessage(@AuthenticationPrincipal User user, Model model) {

        model.addAttribute("activePage", "ADDUSERGROUPCHAT");
        model.addAttribute("title", user.getName());

        return "addUserGroupChat";
    }

    @PostMapping("/addUserGroupChat")
    public String newMessage(String nameChat, String emailUser, @AuthenticationPrincipal User user, Model model) {

        User newUser = userRepository.findUserByEmail(emailUser);
        Chat chat = chatRepository.findChatByName(nameChat, user);

        if (chat != null && newUser != null) {
            chatRepository.addUserToGroupChat(newUser, chat);
            model.addAttribute("activePage", "CHAT");
            model.addAttribute("title", user.getName());
            return "redirect:chat";

        } else {
            model.addAttribute("activePage", "ADDUSERGROUPCHAT");
            model.addAttribute("exception", "чат или пользователь не найден");
            model.addAttribute("title", user.getName());
            return "addUserGroupChat";
        }
    }

}
