package com.example.springboot.service.impl;

import com.example.springboot.controller.request.PageRequest;
import com.example.springboot.entity.*;
import com.example.springboot.exception.ServiceException;
import com.example.springboot.mapper.AccountsMapper;
import com.example.springboot.mapper.BookMapper;
import com.example.springboot.mapper.BorrowMapper;
import com.example.springboot.mapper.CommentMapper;
import com.example.springboot.service.IBorrowService;
import com.example.springboot.service.ICommentService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Slf4j
@Service
public class CommentService implements ICommentService {
    @Autowired
    CommentMapper commentMapper;


    @Override
    public List<Comment> list() {
        return commentMapper.list();
    }

    @Override
    public List<Comment> listByBookNo(String bookNo) {
        return commentMapper.listByBookNo(bookNo);
    }

    @Override
    public List<Comment> listByAccount(String account) {
        return commentMapper.listByAccount(account);
    }
}
