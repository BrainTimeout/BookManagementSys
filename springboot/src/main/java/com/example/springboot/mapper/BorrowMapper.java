package com.example.springboot.mapper;

import com.example.springboot.controller.request.PageRequest;
import com.example.springboot.entity.Borrow;
import com.example.springboot.entity.BorrowProfile;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface BorrowMapper {
    List<BorrowProfile> list();

    List<BorrowProfile> listByCondition(PageRequest PageRequest);

    BorrowProfile getById(Integer id);

    void insert(Borrow borrow);

    void update(Borrow borrow);

    void deleteById(Integer id);
}
