package controller;

import helper.PrintFriend;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.RequestParam;
import repository.Chat;
import repository.User;
import repository.ChatRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import repository.UserRepository;

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

        List<PrintFriend> printFriends = userRepository.findListFriendsByUser(user);

        model.addAttribute("printFriends", printFriends);
        model.addAttribute("activePage", "NEWMESSAGES");
        model.addAttribute("title", user.getName());

        return "newmessage";
    }

    @PostMapping("/newmessage")
    public String newMessage(@RequestParam(value = "idChecked", required = false) List<String> nameFriend, String nameChat, @AuthenticationPrincipal User user, Model model) {

        Chat newChat = chatRepository.addGroupChat(nameChat, "group", user);

        for (String s : nameFriend) {
            User newUser = new User(Integer.parseInt(s));
            chatRepository.addUserToGroupChat(newUser, newChat);
        }

        model.addAttribute("activePage", "CHAT");
        model.addAttribute("title", user.getName());
        return "redirect:chat";
    }

}
