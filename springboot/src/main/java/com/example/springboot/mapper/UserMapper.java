package com.example.springboot.mapper;

import com.example.springboot.controller.request.UserPageRequest;
import com.example.springboot.entity.User;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface UserMapper {

    List<User> list();

    List<User> listByCondition(UserPageRequest userPageRequest);

    void update(User user);

    void insert(User user);
}
