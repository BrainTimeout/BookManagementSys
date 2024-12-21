package com.example.springboot.service.impl;

import com.example.springboot.controller.request.PageRequest;
import com.example.springboot.entity.Accounts;
import com.example.springboot.entity.Book;
import com.example.springboot.entity.Borrow;
import com.example.springboot.entity.BorrowProfile;
import com.example.springboot.exception.ServiceException;
import com.example.springboot.mapper.AccountsMapper;
import com.example.springboot.mapper.BookMapper;
import com.example.springboot.mapper.BorrowMapper;
import com.example.springboot.service.IBorrowService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Slf4j
@Service
public class BorrowService implements IBorrowService {
    @Autowired
    BorrowMapper BorrowMapper;

    @Autowired
    AccountsMapper accountsMapper;

    @Autowired
    BookMapper bookMapper;

    @Override
    public List<BorrowProfile> list(){ return BorrowMapper.list();}

    @Override
    public List<BorrowProfile> listByAccount(String account) {
        return BorrowMapper.listByAccount(account);
    }

    @Override
    public PageInfo<BorrowProfile> page(PageRequest pageRequest) {
        PageHelper.startPage(pageRequest.getPageNum(),pageRequest.getPageSize());
        List<BorrowProfile> borrowProfileList = BorrowMapper.listByCondition(pageRequest);
        return new PageInfo<>(borrowProfileList);
    }

    @Override
    public BorrowProfile getById(Integer id){ return BorrowMapper.getById(id);};

    @Override
    public void insert(Borrow borrow){
        Accounts accounts = accountsMapper.getByAccount(borrow.getAccount());
        Book book = bookMapper.getByBookNo(borrow.getBookNo());
        if(Objects.isNull(accounts)){
            throw new ServiceException("用户不存在");
        }
        if(Objects.isNull(book)){
            throw new ServiceException("该书不存在");
        }
        Integer bookNums = book.getNums();
        // 检验书籍数量并减一
        if(bookNums <= 0){
            throw new ServiceException("当前暂无该书");
        }
        book.setNums(bookNums-1);
        // 检验用户积分并减去书籍积分
        Integer balance = accounts.getBalance();
        Integer score = book.getScore();
        if(score>balance){
            throw new ServiceException("余额不足");
        }
        accounts.setBalance(balance-score);
        bookMapper.update(book);
        accountsMapper.update(accounts);
        BorrowMapper.insert(borrow);
    };

    @Override
    public void update(Borrow borrow){
        BorrowMapper.update(borrow);
    };

    @Override
    public void deleteById(Integer id){ BorrowMapper.deleteById(id);};
}
