package com.example.springboot.service;

import com.example.springboot.entity.Comment;
import com.github.pagehelper.PageInfo;

import java.util.List;

public interface ICommentService {
    List<Comment> list();

    List<Comment> listByBookNo(String bookNo);

    List<Comment> listByAccount(String account);

}
