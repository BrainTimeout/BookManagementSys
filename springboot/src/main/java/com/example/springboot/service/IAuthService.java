package com.example.springboot.service;

import com.example.springboot.controller.dto.LoginDTO;
import com.example.springboot.controller.request.JoinRequest;
import com.example.springboot.controller.request.LoginRequest;
import com.example.springboot.controller.request.UpdatePasswordRequest;
import com.example.springboot.entity.UserProfile;

public interface IAuthService {

    void join(JoinRequest joinRequest);

    LoginDTO login(LoginRequest loginRequest);

    void updatePassword(UpdatePasswordRequest updatePasswordRequest);

    UserProfile getProfile(LoginRequest loginRequest);
}
