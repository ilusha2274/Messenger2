package controller;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import repository.User;
import repository.ChatRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class NewMessageController {

    private final ChatRepository chatRepository;

    public NewMessageController(ChatRepository chatRepository) {
        this.chatRepository = chatRepository;
    }

    @GetMapping("/newmessage")
    public String printHewMessage(@AuthenticationPrincipal User user, Model model) {

        model.addAttribute("activePage", "NEWMESSAGES");
        model.addAttribute("title", user.getName());

        return "newmessage";
    }

    @PostMapping("/newmessage")
    public String newMessage(String nameChat, @AuthenticationPrincipal User user, Model model) {

        chatRepository.addGroupChat(nameChat, "group", user);
        model.addAttribute("activePage", "CHAT");
        model.addAttribute("title", user.getName());
        return "redirect:chat";
    }

}
