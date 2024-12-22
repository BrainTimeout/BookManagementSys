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
import com.example.springboot.service.IAccountsService;
import com.example.springboot.service.IAuthService;
import com.example.springboot.service.IUserService;
import com.example.springboot.utils.TokenUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class UserService implements IUserService {

    @Autowired
    AccountsMapper accountsMapper;

    @Autowired
    UserProfileMapper userProfileMapper;

    @Autowired
    AuthService authService;

    @Override
    public void updatePassword(UpdatePasswordRequest updatePasswordRequest) {
        if(!authService.checkAccounts(updatePasswordRequest.getAccount(),updatePasswordRequest.getOldPassword())){
            throw new ServiceException("旧密码错误");
        }
        authService.updatePassword(updatePasswordRequest);
    }

}
