package com.h2net.h2monitoringweb.monitor;

import com.h2net.h2monitoringweb.login.LoginVO;
import com.h2net.h2monitoringweb.mapper.MonitorMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class MonitorService {
    @Autowired
    MonitorMapper monitorMapper;

    public Map<Float, Object> getWaterPlcData(){
        return monitorMapper.getWaterPlcData();
    }

    public Map<Float, Object> getWetPlcData(){
        return monitorMapper.getWetPlcData();
    }

    public Map<Float, Object> getDryPlcData(){
        return monitorMapper.getDryPlcData();
    }

    public List<MonitorVO> getDashboardPlcData(){
        return monitorMapper.getDashboardPlcData();
    }

    public LoginVO getMyInfo(String userId){
        return monitorMapper.selectMyInfo(userId);
    }
}
