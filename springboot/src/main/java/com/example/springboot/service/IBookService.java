package com.example.springboot.service;

import com.example.springboot.controller.request.PageRequest;
import com.example.springboot.entity.Book;
import com.github.pagehelper.PageInfo;

import java.util.List;

public interface IBookService {
    List<Book> list();

    PageInfo<Book> page(PageRequest PageRequest);

    Book getById(Integer id);

    Book getByBookNo(String bookNo);

    void insert(Book book);

    void update(Book book);

    void deleteById(Integer id);
}
