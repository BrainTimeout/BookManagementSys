package com.example.springboot.service.impl;

import cn.hutool.core.collection.CollUtil;
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

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Slf4j
@Service
public class CategoryService implements ICategoryService {
    @Autowired
    CategoryMapper categoryMapper;

    @Override
    public List<Category> list(){ return categoryMapper.list();};

    // 递归生成菜单树
    private List<Category> createTree(Integer pid, List<Category> categories) {
        List<Category> categoryTree = new ArrayList<>();
        for (Category category : categories) {
            if(pid == null){
                if(category.getPid() == null){
                    categoryTree.add(category);
                    category.setChildren(createTree(category.getId(), categories));
                }
            }else{
                if (pid.equals(category.getPid())) {
                    categoryTree.add(category);
                    category.setChildren(createTree(category.getId(), categories));
                }
            }
            if(CollUtil.isEmpty(category.getChildren())){
                category.setChildren(null);
            }
        }
        return categoryTree;
    }

    @Override
    public List<Category> tree() {
        // 查询出所有的菜单数据集合
        List<Category> categories = categoryMapper.list();
        // 生成菜单树
        return createTree(null, categories);
    }


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
