package com.h2net.h2monitoringweb.monitor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/api")
public class MonitorController {
    @Autowired
    MonitorService monitorService;

    @GetMapping("/water")
    public Map<Float, Object> waterSection(){
        return monitorService.getPlcData();
    }
}
