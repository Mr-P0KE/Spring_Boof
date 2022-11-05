package com.poke.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.poke.DTO.DishDTO;
import com.poke.PoJo.Dish;
import com.poke.PoJo.DishFlavor;
import com.poke.service.DishFlavorService;
import com.poke.service.DishService;
import com.poke.mapper.DishMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
* @author 56207
* @description 针对表【dish(菜品管理)】的数据库操作Service实现
* @createDate 2022-10-31 20:54:02
*/
@Service
@Slf4j
public class DishServiceImpl extends ServiceImpl<DishMapper, Dish>
    implements DishService{

    @Autowired
    private DishFlavorService dishFlavorService;

    //因为对两张表进行写操作，所以需要事务控制，在启动类上开启事务注解
    @Override
    @Transactional
    public void saveWithFlavor(DishDTO dishDTO) {
        super.save(dishDTO);
        Long id = dishDTO.getId();
        log.info("DishId为={}",id);
        for(DishFlavor dishFlavor: dishDTO.getFlavors()){
            dishFlavor.setDishId(id);
        }
        dishFlavorService.saveBatch(dishDTO.getFlavors());
    }

    @Override
    public DishDTO getByIdWithFlavor(Long id) {
        Dish byId = super.getById(id);
        LambdaQueryWrapper<DishFlavor> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.eq(DishFlavor::getDishId,id);
        List<DishFlavor> list = dishFlavorService.list(lambdaQueryWrapper);
        DishDTO d = new DishDTO();
        BeanUtils.copyProperties(byId,d);
        d.setFlavors(list);
        return d;
    }

    @Override
    public void updateWithFlavor(DishDTO dishDTO) {
        this.updateById(dishDTO);
         //需要先删除后更新口味表，因为口味表很特殊，每次修改可能需要添加数据，也可能需要删除数据，
      LambdaQueryWrapper<DishFlavor> lambdaQueryWrapper = new LambdaQueryWrapper<>();
      lambdaQueryWrapper.eq(DishFlavor::getDishId,dishDTO.getId());
      dishFlavorService.remove(lambdaQueryWrapper);

      List<DishFlavor> list =  dishDTO.getFlavors();
      for(DishFlavor dishFlavor : list){
          dishFlavor.setDishId(dishDTO.getId());
      }
      dishFlavorService.saveBatch(list);
    }

    @Override
    public void removeBeachWithDishFlavorById(List<Long> ids) {
            super.removeBatchByIds(ids);
            LambdaQueryWrapper<DishFlavor> qw = new LambdaQueryWrapper();
            qw.in(DishFlavor::getDishId,ids);
            dishFlavorService.remove(qw);

    }
}




