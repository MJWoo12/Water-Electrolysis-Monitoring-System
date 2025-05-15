package com.h2net.h2monitoringweb.admin;

import com.h2net.h2monitoringweb.mapper.AdminMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class AdminService {
    @Autowired
    private AdminMapper adminMapper;

    public List<Map<String, Object>> noAuthUserList(){
        return adminMapper.selectNoAuthUser();
    }

    public int updateUserAuth(AdminVO vo){
        return adminMapper.updateUserAuth(vo);
    }
}
