package com.example.springboot.service;

import com.example.springboot.controller.dto.AuthInfo;
import com.example.springboot.controller.request.JoinRequest;
import com.example.springboot.controller.request.LoginRequest;
import com.example.springboot.controller.request.UpdatePasswordRequest;
import com.example.springboot.entity.UserProfile;

public interface IAuthService {

    void join(JoinRequest joinRequest);

    AuthInfo login(LoginRequest loginRequest);

    void updatePassword(UpdatePasswordRequest updatePasswordRequest);

    AuthInfo getAuthInfo(String account);
}
