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
* @description ��Ա�dish(��Ʒ����)�������ݿ����Serviceʵ��
* @createDate 2022-10-31 20:54:02
*/
@Service
@Slf4j
public class DishServiceImpl extends ServiceImpl<DishMapper, Dish>
    implements DishService{

    @Autowired
    private DishFlavorService dishFlavorService;

    //��Ϊ�����ű����д������������Ҫ������ƣ����������Ͽ�������ע��
    @Override
    @Transactional
    public void saveWithFlavor(DishDTO dishDTO) {
        super.save(dishDTO);
        Long id = dishDTO.getId();
        log.info("DishIdΪ={}",id);
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
         //��Ҫ��ɾ������¿�ζ����Ϊ��ζ������⣬ÿ���޸Ŀ�����Ҫ������ݣ�Ҳ������Ҫɾ�����ݣ�
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




