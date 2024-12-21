package com.example.springboot.service.impl;

import cn.hutool.core.util.StrUtil;
import cn.hutool.crypto.SecureUtil;
import com.example.springboot.controller.dto.AuthInfo;
import com.example.springboot.controller.request.JoinRequest;
import com.example.springboot.controller.request.LoginRequest;
import com.example.springboot.controller.request.UpdatePasswordRequest;
import com.example.springboot.entity.Accounts;
import com.example.springboot.entity.UserProfile;
import com.example.springboot.exception.ServiceException;
import com.example.springboot.mapper.AccountsMapper;
import com.example.springboot.mapper.UserProfileMapper;
import com.example.springboot.service.IAuthService;
import com.example.springboot.utils.TokenUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class AuthService implements IAuthService {

    @Autowired
    AccountsMapper accountsMapper;

    @Autowired
    UserProfileMapper userProfileMapper;

    //默认密码
    private static final String DEFAULT_PASS = "123";
    private static final String PASS_SALT = "npu";

    private String securePass(String password){
        return SecureUtil.md5(password+PASS_SALT);
    }


    @Override
    public AuthInfo getAuthInfo(LoginRequest loginRequest) {
        AuthInfo authInfo = new AuthInfo();
        loginRequest.setAccount(loginRequest.getAccount());
        // 加码
        loginRequest.setPassword(securePass(loginRequest.getPassword()));
        Accounts accounts = accountsMapper.getByAccountAndPassword(loginRequest);
        if(accounts == null){
            throw new ServiceException("用户名或密码错误");
        }
        authInfo.setBalance(accounts.getBalance());
        authInfo.setUserProfile(userProfileMapper.getByAccount(accounts.getAccount()));
        return authInfo;
    }

    @Override
    public AuthInfo login(LoginRequest loginRequest){
        // 加码
        loginRequest.setPassword(securePass(loginRequest.getPassword()));
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

        String token;
        if(accounts.getUsertype().equals("admin")){
            token = TokenUtils.genToken(accounts.getAccount(), accounts.getPassword());
        }else{
            token = null;
        }

        AuthInfo authInfo = new AuthInfo();

        authInfo.setToken(token);
        authInfo.setUsertype(accounts.getUsertype());
        authInfo.setBalance(accounts.getBalance());

        UserProfile userProfile = userProfileMapper.getByAccount(accounts.getAccount());

        authInfo.setUserProfile(userProfile);

        return authInfo;
    }

    @Override
    public void join(JoinRequest joinRequest){
         try {
             Accounts accounts = joinRequest.getAccounts();
             if (StrUtil.isBlank(accounts.getPassword())) {
                 accounts.setPassword(DEFAULT_PASS);
             }
             // 加密
             accounts.setPassword(securePass(accounts.getPassword()));
             accountsMapper.insert(accounts);
             userProfileMapper.update(joinRequest.getUserProfile());
         }catch(Exception e){
             throw new ServiceException("该账号已存在");
        }
    }

    @Override
    public void updatePassword(UpdatePasswordRequest updatePasswordRequest) {
        updatePasswordRequest.setPassword(securePass(updatePasswordRequest.getPassword()));
        accountsMapper.updatePassword(updatePasswordRequest);
    }

}
