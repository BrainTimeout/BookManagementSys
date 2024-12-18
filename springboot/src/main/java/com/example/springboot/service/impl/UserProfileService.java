package com.example.springboot.service.impl;

import com.example.springboot.controller.request.UserProfilePageRequest;
import com.example.springboot.entity.UserProfile;
import com.example.springboot.mapper.UserProfileMapper;
import com.example.springboot.service.IUserProfileService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserProfileService implements IUserProfileService {

    @Autowired
    UserProfileMapper userProfileMapper;

    @Override
    public List<UserProfile> list() {
        return userProfileMapper.list();
    }

    @Override
    public Object page(UserProfilePageRequest userProfilePageRequest) {
        PageHelper.startPage(userProfilePageRequest.getPageNum(),userProfilePageRequest.getPageSize());
        List<UserProfile> userProfileList = userProfileMapper.listByCondition(userProfilePageRequest);
        return new PageInfo<>(userProfileList);
    }

    @Override
    public void update(UserProfile userProfile) {
        userProfileMapper.update(userProfile);
    }

}
