package com.h2net.h2monitoringweb.monitor;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MonitorViewController {
    @GetMapping("/monitor")
    public String monitor(){
        return "forward:/index.html";
    }
}
