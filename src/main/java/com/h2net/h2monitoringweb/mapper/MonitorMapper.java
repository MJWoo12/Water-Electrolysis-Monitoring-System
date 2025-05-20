package com.h2net.h2monitoringweb.mapper;

import com.h2net.h2monitoringweb.login.LoginVO;
import com.h2net.h2monitoringweb.monitor.MonitorVO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

@Mapper
public interface MonitorMapper {
    Map<Float, Object> getWaterPlcData();

    Map<Float, Object> getWetPlcData();

    Map<Float, Object> getDryPlcData();

    List<MonitorVO> getDashboardPlcData();

    LoginVO selectMyInfo(String userId);
}
