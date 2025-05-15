package com.h2net.h2mornitoringweb.login;

import com.h2net.h2mornitoringweb.mapper.LoginMapper;
import lombok.RequiredArgsConstructor;
import org.apache.ibatis.session.SqlSessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class LoginService {
    @Autowired
    private LoginMapper loginMapper;

    public Map<String, Object> loginCheck (String userId, String userPw){
        try {
            Map<String, Object> response = new HashMap<>();
            Map<String, Object> userInfo = loginMapper.selectUserInfo(userId);

            if(userInfo == null){
                response.put("login", false);
                response.put("message", "사용자를 찾을 수 없음");
                return response;
            }

            if(!userPw.equals(userInfo.get("password"))){
                response.put("login", false);
                response.put("message", "아이디 또는 비밀번호가 잘못되었습니다.");
                return response;
            }

            response.put("login", true);
            response.put("user", userInfo);
            response.put("message", "로그인 성공");
            return response;

        }catch (Exception e){
            Map<String, Object> response = new HashMap<>();
            response.put("message", "로그인 중 오류가 발생했습니다.");
            return response;
        }
    }
}
