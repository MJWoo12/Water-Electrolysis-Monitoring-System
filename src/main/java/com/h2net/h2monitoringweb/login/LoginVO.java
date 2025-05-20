package com.h2net.h2monitoringweb.login;

import lombok.Data;

@Data
public class LoginVO {
    private String id;
    private String name;
    private String password;
    private String phone;
    private String organization;
    private String auth;
    private String reg_date;
}
