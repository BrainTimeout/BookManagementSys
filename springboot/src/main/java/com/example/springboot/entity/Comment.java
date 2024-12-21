package com.example.springboot.entity;

import lombok.Data;

@Data
public class Comment {
    private Integer id;
    private String bookNo;
    private String account;
    private String content;
}
