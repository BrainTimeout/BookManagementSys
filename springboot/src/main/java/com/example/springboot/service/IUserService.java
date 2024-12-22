package com.example.springboot.service;

import com.example.springboot.controller.dto.AuthInfo;
import com.example.springboot.controller.request.JoinRequest;
import com.example.springboot.controller.request.LoginRequest;
import com.example.springboot.controller.request.UpdatePasswordRequest;

public interface IUserService {

    void updatePassword(UpdatePasswordRequest updatePasswordRequest);

}
