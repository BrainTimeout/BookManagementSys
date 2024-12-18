package com.example.springboot.service;

import com.example.springboot.controller.request.UserProfilePageRequest;
import com.example.springboot.entity.UserProfile;

import java.util.List;

public interface IUserProfileService {

    List<UserProfile> list();

    Object page(UserProfilePageRequest userProfilePageRequest);

    void update(UserProfile userProfile);

}
