package com.example.springboot.service;

import com.example.springboot.controller.request.PageRequest;
import com.example.springboot.entity.Borrow;
import com.example.springboot.entity.BorrowProfile;
import com.github.pagehelper.PageInfo;

import java.util.List;

public interface IBorrowService {
    List<BorrowProfile> list();

    PageInfo<BorrowProfile> page(PageRequest PageRequest);

    BorrowProfile getById(Integer id);

    void insert(Borrow borrow);

    void update(Borrow borrow);

    void deleteById(Integer id);
}
