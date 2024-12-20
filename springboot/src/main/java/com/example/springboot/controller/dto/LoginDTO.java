package com.example.springboot.controller.dto;

import com.example.springboot.entity.UserProfile;
import lombok.Data;

@Data
public class LoginDTO {
    private String token;
    private String usertype;
    private UserProfile userProfile;
}
