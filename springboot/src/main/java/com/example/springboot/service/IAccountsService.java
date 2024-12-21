package com.example.springboot.service;

import com.example.springboot.controller.request.AccountsRequest;
import com.example.springboot.controller.request.AddBalanceRequest;
import com.example.springboot.entity.Accounts;

import java.util.List;

public interface IAccountsService {

    List<Accounts> list();

    Object page(AccountsRequest accountsRequest);

    Accounts getByAccount(String account);

    void update(Accounts accounts);

    void delete(String account);

    void addBalance(AddBalanceRequest updateBalanceRequest);
}
