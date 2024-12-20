package com.example.springboot.controller;

import com.example.springboot.comon.Result;
import com.example.springboot.controller.request.CategoryPageRequest;
import com.example.springboot.controller.request.PageRequest;
import com.example.springboot.entity.Category;
import com.example.springboot.service.ICategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("/Category")
public class CategoryController {

    @Autowired
    ICategoryService categoryService;

    @GetMapping("/List")
    public Result list(){
        List<Category> categoryList= categoryService.list();
        return Result.success(categoryList);
    }

    @GetMapping("/Page")
    public Result page(CategoryPageRequest categoryPageRequest){
        return Result.success(categoryService.page(categoryPageRequest));
    }

    @GetMapping("/{id}")
    public Result getById(@PathVariable Integer id){
        Category category = categoryService.getById(id);
        return Result.success(category);
    }

    @PutMapping("/Update")
    public Result update(@RequestBody Category category){
        categoryService.update(category);
        return Result.success();
    }

    @DeleteMapping("/Delete/{id}")
    public Result delete(@PathVariable Integer id){
        categoryService.deleteById(id);
        return Result.success();
    }

    @PostMapping("/Insert")
    public Result Join(@RequestBody Category category){
        categoryService.insert(category);
        return Result.success("添加成功");
    }

}
