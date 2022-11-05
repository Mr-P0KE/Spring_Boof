package com.poke.service;

import com.poke.DTO.SetmealDto;
import com.poke.PoJo.Setmeal;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
* @author 56207
* @description 针对表【setmeal(套餐)】的数据库操作Service
* @createDate 2022-10-31 20:54:02
*/
public interface SetmealService extends IService<Setmeal> {

    //保存套餐，需要两个表操作，Setmeal与SetmealDish
    public void saveWithDish(SetmealDto setmealDto);

    public SetmealDto getWithDish(Long id);

    void UpdateWithSetmeal(SetmealDto setmealDto);

    void removeWithDish(List<Long> ids);
}
