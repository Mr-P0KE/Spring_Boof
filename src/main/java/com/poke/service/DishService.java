package com.poke.service;

import com.poke.DTO.DishDTO;
import com.poke.PoJo.Dish;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
* @author 56207
* @description 针对表【dish(菜品管理)】的数据库操作Service
* @createDate 2022-10-31 20:54:02
*/
public interface DishService extends IService<Dish> {

    public void saveWithFlavor(DishDTO dishDTO);

    public DishDTO getByIdWithFlavor(Long id);

    public void updateWithFlavor(DishDTO dishDTO);

    void removeBeachWithDishFlavorById(List<Long> ids);
}
