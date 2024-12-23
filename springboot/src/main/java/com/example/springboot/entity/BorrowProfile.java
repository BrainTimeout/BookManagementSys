package com.example.springboot.entity;

import lombok.Data;

@Data
public class BorrowProfile extends Borrow{
    private String name;           // 书籍名称
    private String phone;          // 用户电话
    private String username;       // 用户名
    private Integer score; // 图书积分
}
