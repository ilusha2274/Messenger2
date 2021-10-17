package controller;

import PrintClass.PrintPost;
import Repository.Chat;
import Repository.User;
import CollectionRepository.ChatRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.SessionAttribute;

import javax.servlet.http.HttpServletRequest;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

@Controller
public class BlockMenuController {

    private final ChatRepository chatRepository;

    public BlockMenuController(ChatRepository chatRepository) {
        this.chatRepository = chatRepository;
    }


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

    @GetMapping("/posts")
    public String printPosts(Model model, @SessionAttribute User user){

        ArrayList<PrintPost> printPosts = printChats(user);

        model.addAttribute("printPosts",printPosts);
        model.addAttribute("title",user.getName());
        model.addAttribute("activePage", "POSTS");

        return"posts";
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

    private ArrayList<PrintPost> printChats (User user){

        ArrayList<PrintPost> printPosts = new ArrayList<>();
        List<Chat> chats = chatRepository.findListChatByUser(user);
        DateTimeFormatter dateTimeFormatterDate = DateTimeFormatter.ofPattern("dd MMM", Locale.ENGLISH);

        for(int i =0;i<chats.size();i++){

            PrintPost printPost;
            if (user == chats.get(i).getUser1()){
                printPost = new PrintPost(chats.get(i).getUser2().getName(), chats.get(i).getChatId(), "", "");
            }else {
                printPost = new PrintPost(chats.get(i).getUser1().getName(), chats.get(i).getChatId(), "", "");
            }
            if (chats.get(i).getLastMessage() != null){
                printPost.setDate(chats.get(i).getLastMessage().getLocalDate().format(dateTimeFormatterDate));
                printPost.setLastMessage(chats.get(i).getLastMessage().getText());
            }
            printPosts.add(printPost);
        }
        return printPosts;
    }
}
