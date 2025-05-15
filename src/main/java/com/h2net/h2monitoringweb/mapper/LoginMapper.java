package com.h2net.h2mornitoringweb.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.Map;

@Mapper
public interface LoginMapper {
    Map<String,Object> selectUserInfo(@Param("userId")String userId);
}
