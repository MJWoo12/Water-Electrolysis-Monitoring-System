package com.h2net.h2monitoringweb.admin;

import lombok.Data;

@Data
public class AdminDTO {
    private class auth{
        private String userId;
        private String newAuth;
    }
}
