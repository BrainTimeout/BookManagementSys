package com.example.springboot.controller.request;

import lombok.Data;

@Data
public class CategoryPageRequest extends PageRequest{
    private Integer id;
    private String name;
    private String remark;
    private String sortOrder;
}
