package com.example.springboot.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.util.Date;

@Data
public class Borrow {
    private Integer id;         // 对应表中的 id 字段
    private String bookNo;      // 对应表中的 bookNo 字段
    private String account;     // 对应表中的 account 字段
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    private Date dueDate;       // 对应表中的 dueDate 字段
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    private Date createtime;    // 对应表中的 createtime 字段
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    private Date updatetime;    // 对应表中的 updatetime 字段
}
