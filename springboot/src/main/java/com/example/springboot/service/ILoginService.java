package com.example.springboot.service;

import com.example.springboot.comon.Result;
import com.example.springboot.controller.request.LoginRequest;
import com.example.springboot.controller.request.UserPageRequest;
import com.example.springboot.entity.Login;
import com.example.springboot.entity.User;

import java.util.List;

public interface ILoginService {

    List<Login> list();

    Object page(LoginRequest loginRequest);

    void update(Login login);

}
