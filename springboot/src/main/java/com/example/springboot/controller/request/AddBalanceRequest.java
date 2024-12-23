package com.example.springboot.controller.request;

import lombok.Data;

@Data
public class AddBalanceRequest {
    private String account;
    private Integer amount;
}
