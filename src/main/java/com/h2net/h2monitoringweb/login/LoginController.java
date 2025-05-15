package com.h2net.h2monitoringweb.login;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.http.Cookie;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/api")
public class LoginController {
    @Autowired
    private LoginService loginService;
    @PostMapping("/login")
    public ResponseEntity<Map<String, Object>> login(@RequestBody LoginVO vo, HttpSession session, HttpServletResponse response) throws Exception{
            Map<String, Object> loginCheck = loginService.loginCheck(vo);
        try {
            if(loginCheck.get("login").equals(true)){
               session.setAttribute("auth", vo.getAuth());
               if(vo.getAuth().equals("01")){
                    int newLogin = loginService.selectNoAuthUser();
                    if(newLogin > 0){
                        session.setAttribute("newLogin", true);
                    }else {
                        session.setAttribute("newLogin", false);
                    }
               }
                String token = UUID.randomUUID().toString();
                loginService.storeToken(token, vo.getId());

                Cookie cookie = new Cookie("loginToken", token);
                cookie.setPath("/");
                cookie.setHttpOnly(true);
                cookie.setMaxAge(60 * 60);
                response.addCookie(cookie);

               return ResponseEntity.status(HttpStatus.OK).body(loginCheck);
            }else{
               return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(loginCheck);
            }
        }catch (Exception e){
          return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(loginCheck);
        }
    }

    @GetMapping("/userInfo")
    public ResponseEntity<Map<String, Object>> getUserInfo(HttpSession session) {
        Map<String, Object> result = new HashMap<>();
        result.put("userAuth", session.getAttribute("auth"));       // 로그인 시 세팅한 값
        result.put("newLogin", session.getAttribute("newLogin"));   // 로그인 시 세팅한 값
        return ResponseEntity.ok(result);
    }
}
