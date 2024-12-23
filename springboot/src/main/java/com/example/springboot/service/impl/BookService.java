package com.example.springboot.service.impl;

import com.example.springboot.controller.request.PageRequest;
import com.example.springboot.entity.Book;
import com.example.springboot.exception.ServiceException;
import com.example.springboot.mapper.BookMapper;
import com.example.springboot.service.IBookService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
public class BookService implements IBookService {
    @Autowired
    BookMapper bookMapper;

    @Override
    public List<Book> list(){ return bookMapper.list();};

    @Override
    public PageInfo<Book> page(PageRequest pageRequest) {
        PageHelper.startPage(pageRequest.getPageNum(),pageRequest.getPageSize());
        List<Book> userProfileList = bookMapper.listByCondition(pageRequest);
        return new PageInfo<>(userProfileList);
    }

    @Override
    public Book getById(Integer id){ return bookMapper.getById(id);}

    @Override
    public Book getByBookNo(String bookNo) {
        return bookMapper.getByBookNo(bookNo);
    }

    @Override
    public void insert(Book Book){

        try{
            bookMapper.insert(Book);
        }catch(Exception e){
            throw new ServiceException("数据插入异常",e);
        }
    };

    @Override
    public void update(Book Book){
        bookMapper.update(Book);
    };

    @Override
    public void deleteById(Integer id){ bookMapper.deleteById(id);};
}
