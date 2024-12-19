package com.example.springboot.service.impl;

import com.example.springboot.controller.dto.LoginDTO;
import com.example.springboot.controller.request.JoinRequest;
import com.example.springboot.controller.request.LoginRequest;
import com.example.springboot.entity.Accounts;
import com.example.springboot.entity.UserProfile;
import com.example.springboot.exception.ServiceException;
import com.example.springboot.mapper.AccountsMapper;
import com.example.springboot.mapper.UserProfileMapper;
import com.example.springboot.service.IAccountsService;
import com.example.springboot.service.IAuthService;
import com.example.springboot.service.IUserProfileService;
import org.apache.ibatis.javassist.expr.NewArray;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class AuthService implements IAuthService {

    @Autowired
    AccountsMapper accountsMapper;

    @Autowired
    UserProfileMapper userProfileMapper;

    @Override
    public LoginDTO login(LoginRequest loginRequest){
        Accounts accounts = accountsMapper.getByAccountAndPassword(loginRequest);
        if(accounts == null){
            throw new ServiceException("用户名或密码错误");
        }
        Date nowTime = new Date();
        if(accounts.getBanuntil().after(nowTime)){
            throw new ServiceException("该账号已被封禁,解禁时间为"+ accounts.getBanuntil());
        }
        if(!accounts.getUsertype().equals(loginRequest.getUsertype())){
            if(accounts.getUsertype().equals("admin")){
                accounts.setUsertype(loginRequest.getUsertype());
            }else{
                throw new ServiceException("您不是管理员");
            }
        }
        LoginDTO loginDTO = new LoginDTO();

        BeanUtils.copyProperties(accounts,loginDTO);

        UserProfile userProfile = userProfileMapper.getByAccount(accounts.getAccount());

        loginDTO.setUserProfile(userProfile);

        return loginDTO;
    }

    @Override
    public void join(JoinRequest joinRequest){
        Accounts accounts = joinRequest.getAccounts();
        Date nowTime = new Date();
        accounts.setBanuntil(nowTime);
        accountsMapper.insert(accounts);
        userProfileMapper.update(joinRequest.getUserProfile());
    }
}
