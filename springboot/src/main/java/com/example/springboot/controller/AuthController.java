package com.example.springboot.controller;

import com.example.springboot.comon.Result;
import com.example.springboot.controller.request.JoinRequest;
import com.example.springboot.controller.request.LoginRequest;
import com.example.springboot.service.IAccountsService;
import com.example.springboot.service.IAuthService;
import com.example.springboot.service.IUserProfileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@CrossOrigin
@RestController
@RequestMapping("/Auth")
public class AuthController {

    @Autowired
    IAuthService authService;

    @PostMapping("/Join")
    public Result Join(@RequestBody JoinRequest joinRequest){
        authService.join(joinRequest);
        return Result.success();
    }

    @PostMapping("/Login")
    public Result login(@RequestBody LoginRequest loginRequest){
        return Result.success(authService.login(loginRequest));
    }
}
