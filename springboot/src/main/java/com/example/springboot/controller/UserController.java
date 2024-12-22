package com.example.springboot.controller;

import com.example.springboot.comon.Result;
import com.example.springboot.controller.request.BookPageRequest;
import com.example.springboot.controller.request.LoginRequest;
import com.example.springboot.entity.Book;
import com.example.springboot.entity.BorrowProfile;
import com.example.springboot.entity.Comment;
import com.example.springboot.service.IAuthService;
import com.example.springboot.service.IBookService;
import com.example.springboot.service.IBorrowService;
import com.example.springboot.service.ICommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("/User")
public class UserController {

    @Autowired
    IBookService bookService;

    @Autowired
    IBorrowService borrowService;

    @Autowired
    IAuthService authService;

    @Autowired
    ICommentService commentService;

    @GetMapping("/List")
    public Result list(){
        List<Book> bookList= bookService.list();
        return Result.success(bookList);
    }

    @GetMapping("/ListByAccount/{account}")
    public Result list(@PathVariable String account){
        List<BorrowProfile> borrowProfiles= borrowService.listByAccount(account);
        return Result.success(borrowProfiles);
    }

    @GetMapping("/Page")
    public Result page(BookPageRequest bookPageRequest){
        return Result.success(bookService.page(bookPageRequest));
    }

    @GetMapping("/{id}")
    public Result getById(@PathVariable Integer id){ return Result.success(bookService.getById(id));}

    @GetMapping("/Comment/{bookNo}")
    public Result commentListByBookNo(@PathVariable String bookNo){
        List<Comment> comments = commentService.listByBookNo(bookNo);
        return Result.success(comments);
    }

    @GetMapping("/GetAuthInfo/{account}")
    public Result getAuthInfo(@PathVariable String account){
        return Result.success(authService.getAuthInfo(account));
    }

}
