package com.example.springboot.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.util.Date;

@Data
public class UserProfile {
    private String account;    // 对应 account 字段
    private String username;   // 对应 username 字段
    private int age;           // 对应 age 字段
    private String sex;        // 对应 sex 字段
    private String phone;      // 对应 phone 字段
    private String address;    // 对应 address 字段
    private String email;      // 对应 email 字段
    private String introduce;  // 对应 introduce 字段
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    private Date createtime;   // 对应 createtime 字段
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    private Date updatetime; // 对应 updatetime 字段
}

