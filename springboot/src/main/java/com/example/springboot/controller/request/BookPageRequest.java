package com.example.springboot.controller.request;

import lombok.Data;


@Data
public class BookPageRequest extends PageRequest{
    private int id;
    private String name;
    private String author;
    private String publisher;
    private String category;
    private String detail;
    private Integer score;
    private String sortOrder;
}
