package com.h2net.h2monitoringweb.mapper;

import org.apache.ibatis.annotations.Mapper;

import java.util.Map;

@Mapper
public interface MonitorMapper {
    Map<Float, Object> getPlcData();
}
