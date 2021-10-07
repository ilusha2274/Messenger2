package controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Controller
public class LogInController {

    @GetMapping("/login")
    public String printLogin(){
        return "login";
    }

    @PostMapping("/login")
    public void login(String email, String password){

        System.out.println(email);
        System.out.println(password);
    }

}
