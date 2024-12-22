package com.example.springboot.service.impl;

import com.example.springboot.controller.request.BorrowRequest;
import com.example.springboot.controller.request.PageRequest;
import com.example.springboot.controller.request.RevertRequest;
import com.example.springboot.entity.*;
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

import java.time.LocalDate;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.List;
import java.util.Objects;

@Slf4j
@Service
public class BorrowService implements IBorrowService {
    @Autowired
    BorrowMapper borrowMapper;

    @Autowired
    AccountsMapper accountsMapper;

    @Autowired
    BookMapper bookMapper;

    private Integer Reward = 25;
    private Integer Punish = 1;

    @Override
    public List<BorrowProfile> list(){ return borrowMapper.list();}

    @Override
    public List<BorrowProfile> listByAccount(String account) {
        return borrowMapper.listByAccount(account);
    }

    @Override
    public PageInfo<BorrowProfile> page(PageRequest pageRequest) {
        PageHelper.startPage(pageRequest.getPageNum(),pageRequest.getPageSize());
        List<BorrowProfile> borrowProfileList = borrowMapper.listByCondition(pageRequest);
        return new PageInfo<>(borrowProfileList);
    }

    @Override
    public BorrowProfile getById(Integer id){ return borrowMapper.getById(id);};

    @Override
    public void insert(BorrowRequest borrowRequest){
        Accounts accounts = accountsMapper.getByAccount(borrowRequest.getAccount());
        Book book = bookMapper.getByBookNo(borrowRequest.getBookNo());
        if(Objects.isNull(accounts)){
            throw new ServiceException("用户不存在");
        }
        // 密码检验
        if(!accounts.getPassword().equals(AuthService.securePass(borrowRequest.getPassword()))){
            throw new ServiceException("用户密码错误");
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
        if(borrowRequest.getDays()==null){
            throw new ServiceException("请输入正确的借书天数");
        }
        Integer balance = accounts.getBalance();
        Integer score = book.getScore() * borrowRequest.getDays();
        if(score>balance){
            throw new ServiceException("余额不足");
        }
        accounts.setBalance(balance-score);

        LocalDate currentDate = LocalDate.now();
        // 计算到期日期
        LocalDate calculatedDueDate = currentDate.plusDays(borrowRequest.getDays());

        borrowRequest.setDueDate(calculatedDueDate);

        bookMapper.update(book);
        accountsMapper.update(accounts);
        borrowMapper.insert(borrowRequest);
    };

    @Override
    public void update(Borrow borrow){
        borrowMapper.update(borrow);
    };

    @Override
    public void deleteById(Integer id){ borrowMapper.deleteById(id);}

    @Override
    public PageInfo<RevertProfile> revertPage(PageRequest pageRequest) {
        PageHelper.startPage(pageRequest.getPageNum(),pageRequest.getPageSize());
        List<RevertProfile> revertProfiles = borrowMapper.listRevertByCondition(pageRequest);
        return new PageInfo<>(revertProfiles);
    }

    @Override
    public void revertBook(RevertRequest revertRequest) {
        // 加一本书
        bookMapper.revertOneBook(revertRequest.getBookNo());
        Book book = bookMapper.getByBookNo(revertRequest.getBookNo());

        Borrow borrow = borrowMapper.getByAccountAndBookNo(revertRequest);

        if(borrow == null) throw new ServiceException("没有该记录");

        try{
            // 删除该条记录
            borrowMapper.deleteById(borrow.getId());
        }catch(Exception e){
            throw new ServiceException("删除记录失败");
        }


        Revert revert = new Revert();
        LocalDate currentDate = LocalDate.now();
        // 逾期时间减当前时间，若大于零说明没超时
        long daysBetween = ChronoUnit.DAYS.between(currentDate ,borrow.getDueDate());


        // 返还积分
        int score = borrow.getDays()*book.getScore();
        if(daysBetween>=0){
            score = score + Reward;
        }else{
            score = score + Punish * (int)daysBetween;
        }
        Accounts accounts = accountsMapper.getByAccount(revertRequest.getAccount());
        accounts.setBalance(score);
        try{
            accountsMapper.updateBalance(accounts);
        }catch(Exception e){
            throw new ServiceException("用户积分更新失败");
        }

        revert.setBookNo(borrow.getBookNo());
        revert.setAccount(borrow.getAccount());
        revert.setDueDate(borrow.getDueDate());
        revert.setRevertDate(currentDate);
        revert.setDays(borrow.getDays()-(int)daysBetween);
        try{
            borrowMapper.insertRevert(revert);
        }catch(Exception e){
            throw new ServiceException("还书记录失败");
        }
    }

    @Override
    public void deleteRevertById(Integer id) {
        borrowMapper.deleteRevertById(id);
    }
}
