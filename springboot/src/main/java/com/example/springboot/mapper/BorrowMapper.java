package com.example.springboot.mapper;

import com.example.springboot.controller.request.PageRequest;
import com.example.springboot.controller.request.RevertRequest;
import com.example.springboot.entity.Borrow;
import com.example.springboot.entity.BorrowProfile;
import com.example.springboot.entity.Revert;
import com.example.springboot.entity.RevertProfile;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface BorrowMapper {
    List<Borrow> list();

    List<Revert> revertList();

    List<BorrowProfile> listByAccount(String account);

    List<BorrowProfile> listByCondition(PageRequest PageRequest);

    BorrowProfile getById(Integer id);

    Borrow getByBookNo(String bookNo);

    Borrow getByAccountAndBookNo(RevertRequest revertRequest);

    void insert(Borrow borrow);

    void update(Borrow borrow);

    void deleteById(Integer id);

    List<RevertProfile> listRevertByCondition(PageRequest PageRequest);

    void insertRevert(Revert revert);

    void deleteRevertById(Integer id);
}
