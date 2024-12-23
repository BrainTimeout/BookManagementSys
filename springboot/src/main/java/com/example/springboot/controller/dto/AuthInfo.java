package com.example.springboot.controller.dto;

import com.example.springboot.entity.UserProfile;
import lombok.Data;

@Data
public class AuthInfo {
    private String token;
    private String usertype;
    private Integer balance;
    private UserProfile userProfile;
}
