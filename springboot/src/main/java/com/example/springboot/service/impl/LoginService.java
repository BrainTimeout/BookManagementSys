package com.example.springboot.service.impl;

import com.example.springboot.controller.request.LoginRequest;
import com.example.springboot.entity.Login;

import com.example.springboot.mapper.LoginMapper;
import com.example.springboot.service.ILoginService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LoginService implements ILoginService {

    @Autowired
    LoginMapper loginMapper;

    @Override
    public List<Login> list() {
        return loginMapper.list();
    }

    @Override
    public Object page(LoginRequest loginRequest) {
        PageHelper.startPage(loginRequest.getPageNum(),loginRequest.getPageSize());
        List<Login> logins = loginMapper.listByCondition(loginRequest);
        return new PageInfo<>(logins);
    }

    @Override
    public void update(Login login) {
        loginMapper.update(login);
    }
}
