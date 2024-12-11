package com.example.springboot.controller;

import com.example.springboot.comon.Result;
import com.example.springboot.controller.request.LoginRequest;
import com.example.springboot.entity.Login;
import com.example.springboot.service.ILoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("/login")
public class LoginController {
    @Autowired
    ILoginService loginService;

    @GetMapping("/List")
    public Result list(){
        List<Login> logins = loginService.list();
        return Result.success(logins);
    }

    @GetMapping("/Page")
    public Result page(LoginRequest loginRequest){
        return Result.success(loginService.page(loginRequest));
    }

    @PostMapping("/Update")
    public Result update(@RequestBody Login login){
        loginService.update(login);
        return Result.success();
    }

}
