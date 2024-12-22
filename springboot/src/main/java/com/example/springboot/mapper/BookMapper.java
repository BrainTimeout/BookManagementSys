package com.example.springboot.mapper;

import com.example.springboot.controller.request.PageRequest;
import com.example.springboot.entity.Book;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface BookMapper {
    List<Book> list();

    List<Book> listByCondition(PageRequest PageRequest);

    Book getById(Integer id);

    Book getByBookNo(String bookNo);

    void insert(Book book);

    void update(Book book);

    void revertOneBook(String bookNo);

    void deleteById(Integer id);
}
