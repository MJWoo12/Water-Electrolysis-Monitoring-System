package com.h2net.h2mornitoringweb.login;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/login")
public class LoginController {
    @Autowired
    private LoginService loginService;
    @PostMapping
    public ResponseEntity<Map<String, Object>> login(@RequestBody Map<String, Object> requestBody) throws Exception{
            String userId = (String) requestBody.get("userId");
            String userPw = (String) requestBody.get("userPw");

            Map<String, Object> loginCheck = loginService.loginCheck(userId, userPw);

        try {
            if(loginCheck.get("login").equals(true)){
               return ResponseEntity.status(HttpStatus.OK).body(loginCheck);
            }else{
               return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(loginCheck);
            }
        }catch (Exception e){
          return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(loginCheck);
        }
    }

}
