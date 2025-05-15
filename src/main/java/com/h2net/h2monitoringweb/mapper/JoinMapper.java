package com.h2net.h2mornitoringweb.mapper;

import org.apache.ibatis.annotations.Mapper;

import java.util.Map;

@Mapper
public interface JoinMapper {
    boolean selectUserId(String userId);

    int insertUser(Map<String, Object> userInfo);
}
