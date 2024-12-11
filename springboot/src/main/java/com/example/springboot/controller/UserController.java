package com.example.springboot.controller;

import com.example.springboot.comon.Result;
import com.example.springboot.controller.request.UserPageRequest;
import com.example.springboot.entity.User;
import com.example.springboot.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    IUserService userService;

    @GetMapping("/InfoList")
    public Result list(){
        List<User> users = userService.list();
        return Result.success(users);
    }

    @GetMapping("/InfoPage")
    public Result page(UserPageRequest userPageRequest){
        return Result.success(userService.page(userPageRequest));
    }

    @PostMapping ("/UpdateInfo")
    public Result update(@RequestBody User user){
        userService.update(user);
        return Result.success();
    }

    @PostMapping("/UserJoin")
    public Result insert(@RequestBody User user){
        return userService.insert(user);
    }


}
