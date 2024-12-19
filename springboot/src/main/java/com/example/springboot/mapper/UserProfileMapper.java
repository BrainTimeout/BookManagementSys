package com.example.springboot.mapper;

import com.example.springboot.controller.request.UserProfilePageRequest;
import com.example.springboot.entity.UserProfile;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface UserProfileMapper {

    List<UserProfile> list();

    List<UserProfile> listByCondition(UserProfilePageRequest userProfilePageRequest);

    UserProfile getByAccount(String Account);

    void update(UserProfile userProfile);
}
