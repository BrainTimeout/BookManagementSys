package com.example.springboot.controller.request;

import lombok.Data;

@Data
public class UpdatePasswordRequest {
    private String account;
    private String password;
}
