package com.h2net.h2monitoringweb.login;

import com.h2net.h2monitoringweb.mapper.LoginMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class LoginService {
    @Autowired
    private LoginMapper loginMapper;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    private final Map<String, String> tokenStore = new ConcurrentHashMap<>();

    public void storeToken(String token, String userId) {
        tokenStore.put(token, userId);
    }

    public String getUserIdByToken(String token) {
        return tokenStore.get(token);
    }

    public Map<String, Object> loginCheck (LoginVO vo){
        try {
            Map<String, Object> response = new HashMap<>();
            LoginVO userInfo = loginMapper.selectUserInfo(vo);

            if(userInfo == null){
                response.put("login", false);
                response.put("message", "아이디 또는 비밀번호가 다릅니다."); //사용자를 찾을 수 없음
                return response;
            }

            if(!passwordEncoder.matches(vo.getPassword(), userInfo.getPassword())){
                response.put("login", false);
                response.put("message", "아이디 또는 비밀번호가 다릅니다.");
                return response;
            }

            response.put("login", true);
            vo.setAuth(userInfo.getAuth());
            response.put("user", userInfo);
            response.put("message", "로그인 성공");
            return response;

        }catch (Exception e){
            Map<String, Object> response = new HashMap<>();
            response.put("message", "로그인 중 오류가 발생했습니다.");
            return response;
        }
    }
    public int selectNoAuthUser(){
        return loginMapper.selectNoAuthUser();
    }

    public boolean isValidCookieAuth(String token) {
        if (token == null || token.trim().isEmpty()) {
            return false;
        }
        String userId = tokenStore.get(token);
        if (userId == null || userId.trim().isEmpty()) {
            return false;
        }

        LoginVO vo = new LoginVO();
        vo.setId(userId);

        try {
            LoginVO userInfo = loginMapper.selectUserInfo(vo);

            return userInfo != null;
        } catch (Exception e) {
            e.printStackTrace(); // 예외 로그 출력
            return false; // 예외 발생 시 인증되지 않음
        }
    }
}
