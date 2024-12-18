package com.example.springboot.controller.request;

import lombok.Data;

import java.util.Date;

@Data
public class AccountsRequest extends PageRequest {
    private String account;
    private String password;
    private String usertype;
    private Date banuntil;
}
