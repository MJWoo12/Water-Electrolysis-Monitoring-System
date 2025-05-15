package com.h2net.h2monitoringweb.common;

import com.h2net.h2monitoringweb.H2MonitoringWebApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

public class ServletInitializer extends SpringBootServletInitializer {
    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
        return application.sources(H2MonitoringWebApplication.class);
    }
}
