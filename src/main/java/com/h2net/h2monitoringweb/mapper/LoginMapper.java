package com.h2net.h2monitoringweb.mapper;

import com.h2net.h2monitoringweb.login.LoginVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.Map;

@Mapper
public interface LoginMapper {
    LoginVO selectUserInfo(LoginVO vo);

    int selectNoAuthUser();
}
