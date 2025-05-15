package com.h2net.h2mornitoringweb.join;

import com.h2net.h2mornitoringweb.mapper.JoinMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class JoinService {
    @Autowired
    private JoinMapper joinMapper;

    public boolean checkUsedId (String userId){
        return joinMapper.selectUserId(userId);
    }

    public boolean insertUser(JoinVo joinVo){
        Map<String, Object> userInfo = new HashMap<>();

        userInfo.put("userId", joinVo.getUserId());
        userInfo.put("userPw", joinVo.getUserPw());
        userInfo.put("userNm", joinVo.getUserNm());
        userInfo.put("email", joinVo.getEmail());
        userInfo.put("organization", joinVo.getOrganization());
        userInfo.put("phone", joinVo.getPhone());

        int insertUser = joinMapper.insertUser(userInfo);
        return insertUser > 0;
    }
}
