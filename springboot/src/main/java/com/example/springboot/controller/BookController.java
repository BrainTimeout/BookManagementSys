package com.example.springboot.controller;

import com.example.springboot.comon.Result;
import com.example.springboot.controller.request.BookPageRequest;
import com.example.springboot.entity.Book;
import com.example.springboot.service.IBookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("/Book")
public class BookController {

    @Autowired
    IBookService bookService;

    @GetMapping("/List")
    public Result list(){
        List<Book> bookList= bookService.list();
        return Result.success(bookList);
    }

    @GetMapping("/Page")
    public Result page(BookPageRequest bookPageRequest){
        return Result.success(bookService.page(bookPageRequest));
    }

    @GetMapping("/GetById/{id}")
    public Result getById(@PathVariable Integer id){
        Book book = bookService.getById(id);
        return Result.success(book);
    }

    @GetMapping("/GetByBookNo/{bookNo}")
    public Result getByBookNo(@PathVariable String bookNo){
        Book book = bookService.getByBookNo(bookNo);
        return Result.success(book);
    }

    @PutMapping("/Update")
    public Result update(@RequestBody Book book){
        bookService.update(book);
        return Result.success();
    }

    @DeleteMapping("/Delete/{id}")
    public Result delete(@PathVariable Integer id){
        bookService.deleteById(id);
        return Result.success();
    }

    @PostMapping("/Insert")
    public Result Join(@RequestBody Book book){
        bookService.insert(book);
        return Result.success("添加成功");
    }

}
