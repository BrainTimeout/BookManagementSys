package com.example.springboot.controller.request;

import com.example.springboot.entity.Borrow;
import lombok.Data;

@Data
public class BorrowRequest extends Borrow {
    private String password;
}
