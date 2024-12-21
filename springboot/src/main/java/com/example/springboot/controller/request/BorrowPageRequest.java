package com.example.springboot.controller.request;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;


@Data
public class BorrowPageRequest extends PageRequest{
    private String account;    // 用户账号
    private String bookNo;     // 书籍编号
    private String username;   // 用户名
    private String bookName;   // 图书名称
    private String dueDate;
    private String sortOrder;  // 排序顺序（'ascending' 或 'descending'）
}
