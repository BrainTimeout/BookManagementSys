package com.example.springboot.service;

import com.example.springboot.controller.dto.LoginDTO;
import com.example.springboot.controller.request.AccountsRequest;
import com.example.springboot.controller.request.LoginRequest;
import com.example.springboot.controller.request.UpdatePasswordRequest;
import com.example.springboot.entity.Accounts;

import java.util.List;

public interface IAccountsService {

    List<Accounts> list();

    Object page(AccountsRequest accountsRequest);

    void update(Accounts accounts);

    void updatePassword(UpdatePasswordRequest updatePasswordRequest);

    void delete(String account);

    void insert(Accounts accounts);

}
