package com.example.springboot.controller.request;

import lombok.Data;

import lombok.Data;

import java.util.Date;

@Data
public class UserPageRequest extends BaseRequest {
    private String account;    // 对应 account 字段
    private String username;   // 对应 username 字段
    private String phone;      // 对应 phone 字段
    private String address;    // 对应 address 字段
    private Integer age;       // 对应 age 字段
    private String sex;        // 对应 sex 字段
    private String detail;     // 对应其它条件
}


