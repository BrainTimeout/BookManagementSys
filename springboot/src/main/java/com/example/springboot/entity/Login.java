package com.example.springboot.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.util.Date;

@Data
public class Login {
    private String account;
    private String password;
    private String usertype;
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    private Date banuntil;
}
