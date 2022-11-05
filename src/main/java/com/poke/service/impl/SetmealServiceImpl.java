package com.poke.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.poke.Comon.CustomException;
import com.poke.DTO.DishDTO;
import com.poke.DTO.SetmealDto;
import com.poke.PoJo.Dish;
import com.poke.PoJo.Setmeal;
import com.poke.PoJo.SetmealDish;
import com.poke.service.SetmealDishService;
import com.poke.service.SetmealService;
import com.poke.mapper.SetmealMapper;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
* @author 56207
* @description ????setmeal(???)????????????Service???
* @createDate 2022-10-31 20:54:02
*/
@Service
public class SetmealServiceImpl extends ServiceImpl<SetmealMapper, Setmeal>
    implements SetmealService{
    @Autowired
    private SetmealDishService setmealDishService;


    @Transactional
    @Override
    public void saveWithDish(SetmealDto setmealDto) {
        super.save(setmealDto);

        List<SetmealDish> setmealDishes = setmealDto.getSetmealDishes();
        for(SetmealDish setmealDish : setmealDishes){
            setmealDish.setSetmealId(String.valueOf(setmealDto.getId()));
        }
        setmealDishService.saveBatch(setmealDishes);
    }

    @Override
    public SetmealDto getWithDish(Long id) {
        Setmeal setmeal = super.getById(id);
        SetmealDto setmealDto = new SetmealDto();
        BeanUtils.copyProperties(setmeal,setmealDto);

        LambdaQueryWrapper<SetmealDish> lambdaQueryWrapper =new LambdaQueryWrapper<>();
        lambdaQueryWrapper.eq(SetmealDish::getSetmealId,setmeal.getId());
        List<SetmealDish> list = setmealDishService.list(lambdaQueryWrapper);
        setmealDto.setSetmealDishes(list);

        return setmealDto;
    }

    @Transactional
    @Override
    public void UpdateWithSetmeal(SetmealDto setmealDto) {
        super.updateById(setmealDto);

        LambdaQueryWrapper<SetmealDish> lambdaQueryWrapper  = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.eq(SetmealDish::getSetmealId,setmealDto.getId());
        setmealDishService.remove(lambdaQueryWrapper);

        List<SetmealDish> list = setmealDto.getSetmealDishes();
        for(SetmealDish setmealDish : list){
            setmealDish.setSetmealId(String.valueOf(setmealDto.getId()));
        }
        setmealDishService.saveBatch(list);
    }

    @Transactional
    @Override
    public void removeWithDish(List<Long> ids) {
            LambdaQueryWrapper<Setmeal> lambdaQueryWrapper = new LambdaQueryWrapper<>();
            lambdaQueryWrapper.eq(Setmeal::getStatus,1)
                    .in(Setmeal::getId,ids);
        long count = super.count(lambdaQueryWrapper);
        if(count > 0) {
            throw new CustomException("有套餐正在售卖，无法删除");

        } else{
            super.removeByIds(ids);
            LambdaQueryWrapper<SetmealDish> lambdaQueryWrapper1 = new LambdaQueryWrapper<>();
            lambdaQueryWrapper1.in(SetmealDish::getSetmealId,ids);
            setmealDishService.remove(lambdaQueryWrapper1);
        }
    }
}




