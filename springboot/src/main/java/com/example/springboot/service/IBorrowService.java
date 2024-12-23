package com.example.springboot.service;

import com.example.springboot.controller.request.BorrowRequest;
import com.example.springboot.controller.request.PageRequest;
import com.example.springboot.controller.request.RevertRequest;
import com.example.springboot.entity.Borrow;
import com.example.springboot.entity.BorrowProfile;
import com.example.springboot.entity.Revert;
import com.example.springboot.entity.RevertProfile;
import com.github.pagehelper.PageInfo;

import java.util.List;

public interface IBorrowService {
    List<Borrow> list();

    List<Revert> revertList();

    List<BorrowProfile> listByAccount(String account);

    PageInfo<BorrowProfile> page(PageRequest pageRequest);

    BorrowProfile getById(Integer id);

    void insert(BorrowRequest borrowRequest);

    void update(Borrow borrow);

    void deleteById(Integer id);

    PageInfo<RevertProfile> revertPage(PageRequest pageRequest);

    void revertBook(RevertRequest revertRequest);

    void deleteRevertById(Integer id);
}
