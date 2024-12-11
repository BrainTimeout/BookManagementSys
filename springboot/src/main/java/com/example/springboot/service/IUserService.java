package com.example.springboot.service;

import com.example.springboot.comon.Result;
import com.example.springboot.controller.request.UserPageRequest;
import com.example.springboot.entity.User;
import org.springframework.stereotype.Service;

import java.util.List;

public interface IUserService {

    List<User> list();

    Object page(UserPageRequest userPageRequest);

    void update(User user);

    Result insert(User user);
}
