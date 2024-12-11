package com.example.springboot.mapper;

import com.example.springboot.controller.request.LoginRequest;
import com.example.springboot.entity.Login;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface LoginMapper {

    List<Login> list();

    List<Login> listByCondition(LoginRequest loginRequest);

    void update(Login login);
}
