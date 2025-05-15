package com.h2net.h2monitoringweb.monitor;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class MonitorViewController {
    @GetMapping("/monitor/water")
    public String waterSection(){
        return "forward:/index.html";
    }
    @GetMapping("/monitor/dry")
    public String drySection(){
//        return "/monitor/drySection";
        return "/monitor/drySection";
    }

    @GetMapping("/monitor/wet")
    public String wetSection(){
        return "/monitor/wetSection";
    }
}
