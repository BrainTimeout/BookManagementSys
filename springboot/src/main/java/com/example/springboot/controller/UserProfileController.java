package com.example.springboot.controller;

import com.example.springboot.comon.Result;
import com.example.springboot.controller.request.UserProfilePageRequest;
import com.example.springboot.entity.UserProfile;
import com.example.springboot.service.IUserProfileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("/UserProfile")
public class UserProfileController {

    @Autowired
    IUserProfileService userProfileService;

    @GetMapping("/List")
    public Result list(){
        List<UserProfile> userProfileList = userProfileService.list();
        return Result.success(userProfileList);
    }

    @GetMapping("/Page")
    public Result page(UserProfilePageRequest userProfilePageRequest){
        return Result.success(userProfileService.page(userProfilePageRequest));
    }

    @PutMapping ("/Update")
    public Result update(@RequestBody UserProfile userProfile){
        userProfileService.update(userProfile);
        return Result.success();
    }

}
