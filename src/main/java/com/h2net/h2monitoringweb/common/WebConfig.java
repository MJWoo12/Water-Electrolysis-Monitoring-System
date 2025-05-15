package com.h2net.h2monitoringweb.common;

import com.h2net.h2monitoringweb.login.LoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class WebConfig {
    @Autowired
    LoginService loginService;
    @Bean
    public FilterRegistrationBean<CustomFilter> customFilter() {
        FilterRegistrationBean<CustomFilter> registrationBean = new FilterRegistrationBean<>();
        CustomFilter customFilter = new CustomFilter();
        customFilter.setLoginService(loginService);
        registrationBean.setOrder(1);
        registrationBean.setFilter(customFilter);
        registrationBean.addUrlPatterns("/*");
        return registrationBean;
    }
}
