package com.example.springboot.service;

import com.example.springboot.controller.dto.LoginDTO;
import com.example.springboot.controller.request.JoinRequest;
import com.example.springboot.controller.request.LoginRequest;

public interface IAuthService {

    void join(JoinRequest joinRequest);

    LoginDTO login(LoginRequest loginRequest);
}
