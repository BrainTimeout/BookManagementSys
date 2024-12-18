package com.example.springboot.mapper;

import com.example.springboot.controller.request.AccountsRequest;
import com.example.springboot.controller.request.LoginRequest;
import com.example.springboot.entity.Accounts;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface AccountsMapper {

    List<Accounts> list();

    List<Accounts> listByCondition(AccountsRequest accountsRequest);

    Accounts getByAccountAndPassword(LoginRequest loginRequest);

    void update(Accounts accounts);

    void delete(String account);

    void insert(Accounts accounts);
}
