package com.h2net.h2monitoringweb.monitor;

import com.h2net.h2monitoringweb.login.LoginService;
import com.h2net.h2monitoringweb.login.LoginVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api")
public class MonitorController {
    @Autowired
    MonitorService monitorService;
    @Autowired
    LoginService loginService;

    @GetMapping("/water")
    public Map<Float, Object> waterSection(){
        return monitorService.getWaterPlcData();
    }

    @GetMapping("/wet")
    public Map<Float, Object> wetSection(){
        return monitorService.getWetPlcData();
    }

    @GetMapping("/dry")
    public Map<Float, Object> drySection(){
        return monitorService.getDryPlcData();
    }

    @GetMapping("/dashboard")
    public List<MonitorVO> dashboardSection(){
        return monitorService.getDashboardPlcData();
    }

    @GetMapping("/myPage")
    public LoginVO myPage(@CookieValue(value = "loginToken", required = false) String token){
        String userId = loginService.getUserIdByToken(token); // token -> userId 매핑
        LoginVO vo = new LoginVO();
        vo.setId(userId);
        LoginVO userInfo = monitorService.getMyInfo(vo.getId());
        if (userInfo != null) {
            if (userInfo.getId().equals(userId)) {
                return userInfo;
            } else {
                return null;
            }
        }
        return null;
    }
}
