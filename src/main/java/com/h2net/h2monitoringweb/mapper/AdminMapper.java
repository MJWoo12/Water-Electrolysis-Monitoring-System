package com.h2net.h2monitoringweb.mapper;

import com.h2net.h2monitoringweb.admin.AdminVO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

@Mapper
public interface AdminMapper {
    List<Map<String, Object>> selectNoAuthUser();

    int updateUserAuth(AdminVO vo);
}
