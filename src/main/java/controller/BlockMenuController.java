package controller;

import repository.User;
import repository.ChatRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.SessionAttribute;

import javax.servlet.http.HttpServletRequest;

@Controller
public class BlockMenuController {

    @GetMapping("/home")
    public String printHome(@SessionAttribute User user, Model model){

        model.addAttribute("title",user.getName());

        return"home";
    }

    @GetMapping("/profile")
    public String printProfile(@SessionAttribute User user, Model model){

        model.addAttribute("activePage", "PROFILE");
        model.addAttribute("title",user.getName());

        return"profile";
    }

    @GetMapping("/settings")
    public String printSettings(@SessionAttribute User user, Model model){

        model.addAttribute("activePage", "SETTINGS");
        model.addAttribute("title",user.getName());

        return"settings";
    }

    @GetMapping("/exit")
    public String printExit(HttpServletRequest req){

        req.getSession().invalidate();

        return "redirect:login";
    }
}
