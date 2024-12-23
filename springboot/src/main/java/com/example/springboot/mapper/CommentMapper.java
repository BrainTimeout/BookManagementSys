package com.example.springboot.mapper;

import com.example.springboot.controller.request.PageRequest;
import com.example.springboot.entity.Borrow;
import com.example.springboot.entity.BorrowProfile;
import com.example.springboot.entity.Comment;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface CommentMapper {
    List<Comment> list();

    List<Comment> listByBookNo(String bookNo);

    List<Comment> listByAccount(String account);

    void insert(Comment comment);
}
