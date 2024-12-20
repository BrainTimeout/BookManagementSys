package com.example.springboot.service.impl;

import com.example.springboot.controller.request.CategoryPageRequest;
import com.example.springboot.controller.request.PageRequest;
import com.example.springboot.controller.request.UserProfilePageRequest;
import com.example.springboot.entity.Category;
import com.example.springboot.entity.UserProfile;
import com.example.springboot.mapper.CategoryMapper;
import com.example.springboot.service.ICategoryService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Slf4j
@Service
public class CategoryService implements ICategoryService {
    @Autowired
    CategoryMapper categoryMapper;

    @Override
    public List<Category> list(){ return categoryMapper.list();};

    @Override
    public PageInfo<Category> page(CategoryPageRequest categoryPageRequest) {
        PageHelper.startPage(categoryPageRequest.getPageNum(),categoryPageRequest.getPageSize());
        List<Category> userProfileList = categoryMapper.listByCondition(categoryPageRequest);
        return new PageInfo<>(userProfileList);
    }

    @Override
    public Category getById(Integer id){ return categoryMapper.getById(id);};

    @Override
    public void insert(Category category){
        categoryMapper.insert(category);
    };

    @Override
    public void update(Category category){
        categoryMapper.update(category);
    };

    @Override
    public void deleteById(Integer id){ categoryMapper.deleteById(id);};
}
