package com.h2net.h2monitoringweb.join;

import com.h2net.h2monitoringweb.mapper.JoinMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
public class JoinService {
    @Autowired
    private JoinMapper joinMapper;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    public boolean checkUsedId (String userId){
        int selectUser = joinMapper.selectUserId(userId);
        return selectUser == 0;
    }

    @Transactional
    public boolean insertUser(JoinVo vo){
        int selectUser = joinMapper.selectUserId(vo.getUserId());
        if(selectUser == 0){
            return false;
        }
        try{
            String encPw = passwordEncoder.encode(vo.getUserPw());
            vo.setUserPw(encPw);
            int insertUser = joinMapper.insertUser(vo);
            return insertUser > 0;
        }catch (Exception e){
            return false;
        }
    }
}
