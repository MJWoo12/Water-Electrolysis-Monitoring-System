package com.h2net.h2monitoringweb.monitor;

import com.h2net.h2monitoringweb.mapper.MonitorMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class MonitorService {
    @Autowired
    MonitorMapper monitorMapper;

    public Map<Float, Object> getPlcData(){
        return monitorMapper.getPlcData();
    }
}
