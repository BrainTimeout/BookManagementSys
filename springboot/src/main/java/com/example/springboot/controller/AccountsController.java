package com.example.springboot.controller;

import com.example.springboot.comon.Result;
import com.example.springboot.controller.request.AccountsRequest;
import com.example.springboot.controller.request.LoginRequest;
import com.example.springboot.controller.request.UpdatePasswordRequest;
import com.example.springboot.entity.Accounts;
import com.example.springboot.service.IAccountsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("/Accounts")
public class AccountsController {
    @Autowired
    IAccountsService accountsService;

    @GetMapping("/List")
    public Result list(){
        List<Accounts> accountsList = accountsService.list();
        return Result.success(accountsList);
    }

    @GetMapping("/Page")
    public Result page(AccountsRequest accountsRequest){
        return Result.success(accountsService.page(accountsRequest));
    }

    @PutMapping("/Update")
    public Result update(@RequestBody Accounts accounts){
        accountsService.update(accounts);
        return Result.success();
    }

    @PutMapping("/UpdatePassword")
    public Result updatePassword(@RequestBody UpdatePasswordRequest updatePasswordRequest){
        accountsService.updatePassword(updatePasswordRequest);
        return Result.success();
    }

    @DeleteMapping("/Delete/{account}")
    public Result delete(@PathVariable String account){
        accountsService.delete(account);
        return Result.success();
    }
}
