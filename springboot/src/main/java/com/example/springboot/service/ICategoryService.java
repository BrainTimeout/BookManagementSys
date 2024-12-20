package com.example.springboot.service;

import com.example.springboot.controller.request.CategoryPageRequest;
import com.example.springboot.controller.request.PageRequest;
import com.example.springboot.controller.request.UserProfilePageRequest;
import com.example.springboot.entity.Category;
import com.github.pagehelper.PageInfo;

import java.util.List;

public interface ICategoryService {
    List<Category> list();

    PageInfo<Category> page(CategoryPageRequest categoryPageRequest);

    Category getById(Integer id);

    void insert(Category category);

    void update(Category category);

    void deleteById(Integer id);
}
