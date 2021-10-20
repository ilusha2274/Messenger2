package controller;

import repository.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.SessionAttribute;

import javax.servlet.http.HttpSession;

@Controller
public class BlockMenuController {

    @GetMapping("/home")
    public String printHome(@SessionAttribute User user, Model model) {

        model.addAttribute("title", user.getName());

        return "home";
    }

    @GetMapping("/profile")
    public String printProfile(@SessionAttribute User user, Model model) {

        model.addAttribute("activePage", "PROFILE");
        model.addAttribute("title", user.getName());

        return "profile";
    }

    @GetMapping("/settings")
    public String printSettings(@SessionAttribute User user, Model model) {

        model.addAttribute("activePage", "SETTINGS");
        model.addAttribute("title", user.getName());

        return "settings";
    }

    @GetMapping("/exit")
    public String printExit(HttpSession session) {

        session.invalidate();

        return "redirect:login";
    }
}
