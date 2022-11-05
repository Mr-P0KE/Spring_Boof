package com.poke.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.poke.Comon.CustomException;
import com.poke.PoJo.Category;
import com.poke.PoJo.Dish;
import com.poke.PoJo.Setmeal;
import com.poke.service.CategoryService;
import com.poke.mapper.CategoryMapper;
import com.poke.service.DishService;
import com.poke.service.SetmealService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
* @author 56207
* @description 针对表【category(菜品及套餐分类)】的数据库操作Service实现
* @createDate 2022-10-31 20:54:02
*/
@Service
public class CategoryServiceImpl extends ServiceImpl<CategoryMapper, Category>
    implements CategoryService{

    @Autowired
    private DishService dishService;

    @Autowired
    private SetmealService setmealService;


    @Override
    public void remove(Long id) {
        LambdaQueryWrapper<Dish> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.eq(Dish::getCategoryId,id);
        long count = dishService.count(lambdaQueryWrapper);
        if(count > 0){
            //抛出异常
            throw new CustomException("有菜品关联，无法删除");
        }
        LambdaQueryWrapper<Setmeal> lambdaQueryWrapper1 = new LambdaQueryWrapper<>();
        lambdaQueryWrapper1.eq(Setmeal::getCategoryId,id);
        long count1 = setmealService.count(lambdaQueryWrapper1);
        if(count1 >0){
            //抛出异常
            throw new CustomException("有套餐关联，无法删除");
        }
        super.removeById(id);
    }
}




