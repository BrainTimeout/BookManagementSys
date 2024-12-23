package com.example.springboot.controller.request;

import lombok.Data;

@Data
public class RevertRequest {
    private String account;
    private String bookNo;
}
