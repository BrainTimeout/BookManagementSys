package com.example.springboot.controller;

import com.example.springboot.comon.Result;
import com.example.springboot.controller.request.BorrowPageRequest;
import com.example.springboot.entity.Borrow;
import com.example.springboot.entity.BorrowProfile;
import com.example.springboot.service.IBorrowService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("/Borrow")
public class BorrowController {

    @Autowired
    IBorrowService borrowService;

    @GetMapping("/List")
    public Result list(){
        List<BorrowProfile> borrowList= borrowService.list();
        return Result.success(borrowList);
    }

    @GetMapping("/Page")
    public Result page(BorrowPageRequest borrowPageRequest){
        return Result.success(borrowService.page(borrowPageRequest));
    }

    @GetMapping("/{id}")
    public Result getById(@PathVariable Integer id){
        Borrow borrow = borrowService.getById(id);
        return Result.success(borrow);
    }

    @PutMapping("/Update")
    public Result update(@RequestBody Borrow borrow){
        borrowService.update(borrow);
        return Result.success();
    }

    @DeleteMapping("/Delete/{id}")
    public Result delete(@PathVariable Integer id){
        borrowService.deleteById(id);
        return Result.success();
    }

    @PostMapping("/Insert")
    public Result Join(@RequestBody Borrow borrow){
        borrowService.insert(borrow);
        return Result.success("添加成功");
    }

}
