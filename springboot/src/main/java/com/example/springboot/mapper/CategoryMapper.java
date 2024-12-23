package com.example.springboot.mapper;

import com.example.springboot.controller.request.CategoryPageRequest;
import com.example.springboot.controller.request.PageRequest;
import com.example.springboot.entity.Category;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface CategoryMapper {
    List<Category> list();

    List<Category> listByCondition(CategoryPageRequest categoryPageRequest);

    Category getById(Integer id);

    void insert(Category category);

    void update(Category category);

    void deleteById(Integer id);
}
