package com.example.springboot.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.util.Date;

@Data
public class Accounts {
    private String account;
    private String password;
    private String usertype;
    private Integer balance;
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    private Date banuntil;
}
