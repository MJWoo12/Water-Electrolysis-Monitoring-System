package com.h2net.h2mornitoringweb.login;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/")
public class LoginViewController {
    @GetMapping
    public String loginPage(){
        return "/login/login";
    }
}
