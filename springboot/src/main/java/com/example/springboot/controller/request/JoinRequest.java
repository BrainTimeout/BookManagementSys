package com.example.springboot.controller.request;

import com.example.springboot.entity.Accounts;
import com.example.springboot.entity.UserProfile;
import lombok.Data;

@Data
public class JoinRequest {
    private Accounts accounts;
    private UserProfile userProfile;
}
