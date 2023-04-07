package com.poke.Controller;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.poke.PoJo.Category;
import com.poke.Util.R;
import com.poke.service.CategoryService;
import com.poke.service.impl.CategoryServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Slf4j
@RequestMapping("/category")
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    @GetMapping("/page")
    public R<Page> page(Integer page,Integer pageSize){
        log.info("page = {},pageSize = {}" ,page,pageSize);

        Page pageInfo = new Page(page,pageSize);

        QueryWrapper<Category> qw = new QueryWrapper<>();
        qw.orderByDesc("sort");
        categoryService.page(pageInfo,qw);
        return R.success(pageInfo);
    }

    @PostMapping
    public R<String> Insert(@RequestBody Category category){
        log.info("category={}",category);
        categoryService.save(category);
        return  R.success("添加成功");
    }

    @PutMapping
    public R<String> update(@RequestBody Category category){
        log.info("菜品信息={}",category.toString());
//        Long id = (Long) session.getAttribute("employee");
//        employee.setUpdateTime(LocalDateTime.now());
//        employee.setUpdateUser(id);
        categoryService.updateById(category);
        return R.success("修改成功111");
    }

    @DeleteMapping
    public  R<String> delete(Long ids){
        log.info("删除分类，id为={}",ids);
        categoryService.remove(ids);
        return R.success("删除成功啦");
    }

    @GetMapping("/list")
    public R<List<Category>> listR(Category category){
        LambdaQueryWrapper<Category> qw = new LambdaQueryWrapper();
        qw.eq(category.getType()!= null,Category::getType,category.getType())
                .orderByAsc(Category::getSort)
                .orderByDesc(Category::getCreateTime);
        List<Category> list = categoryService.list(qw);
        return R.success(list);
    }
}
