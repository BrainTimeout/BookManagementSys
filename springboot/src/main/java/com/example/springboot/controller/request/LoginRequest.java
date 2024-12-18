package com.example.springboot.controller.request;

import lombok.Data;

@Data
public class LoginRequest {
    private String account;
    private String password;
    private String usertype;
}
