package com.h2net.h2monitoringweb.login;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.io.IOException;

@Controller
@RequestMapping("/")
public class LoginViewController {
    @GetMapping("/")
    public String loginPage(){
        return "/login/login";
    }

    @GetMapping("/logout")
    public String logoutPage(HttpServletResponse response) throws IOException {
        Cookie cookie = new Cookie("loginToken", null);
        cookie.setPath("/");
        cookie.setMaxAge(0); // 즉시 만료
        response.addCookie(cookie);
        response.sendRedirect("/");
        return "/login/login";
    }
}
