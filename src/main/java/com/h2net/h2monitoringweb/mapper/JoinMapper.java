package com.h2net.h2monitoringweb.mapper;

import com.h2net.h2monitoringweb.join.JoinVo;
import org.apache.ibatis.annotations.Mapper;

import java.util.Map;

@Mapper
public interface JoinMapper {
    int selectUserId(String userId);

    int insertUser(JoinVo userInfo);
}
