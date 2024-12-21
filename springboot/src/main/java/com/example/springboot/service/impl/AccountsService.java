package com.example.springboot.service.impl;

import com.example.springboot.controller.request.AccountsRequest;
import com.example.springboot.controller.request.AddBalanceRequest;
import com.example.springboot.entity.Accounts;

import com.example.springboot.exception.ServiceException;
import com.example.springboot.mapper.AccountsMapper;
import com.example.springboot.service.IAccountsService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AccountsService implements IAccountsService {

    @Autowired
    AccountsMapper accountsMapper;

    @Override
    public List<Accounts> list() {
        return accountsMapper.list();
    }

    @Override
    public Object page(AccountsRequest accountsRequest) {
        PageHelper.startPage(accountsRequest.getPageNum(),accountsRequest.getPageSize());
        List<Accounts> accountsList = accountsMapper.listByCondition(accountsRequest);
        return new PageInfo<>(accountsList);
    }

    @Override
    public Accounts getByAccount(String account){
        return accountsMapper.getByAccount(account);
    }

    @Override
    public void update(Accounts accounts) {
        accountsMapper.update(accounts);
    }

    @Override
    public void delete(String account) { accountsMapper.delete(account); }

    @Override
    public void addBalance(AddBalanceRequest updateBalanceRequest) {
        Integer amount = updateBalanceRequest.getAmount();
        String account = updateBalanceRequest.getAccount();
        if(amount == null || account == null){
            throw new ServiceException("充值失败");
        }
        Accounts accounts = accountsMapper.getByAccount(account);
        accounts.setBalance(accounts.getBalance()+amount);
        accountsMapper.update(accounts);
    }

}
