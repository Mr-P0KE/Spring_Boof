package com.poke.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.poke.PoJo.DishFlavor;
import com.poke.service.DishFlavorService;
import com.poke.mapper.DishFlavorMapper;
import org.springframework.stereotype.Service;

/**
* @author 56207
* @description 针对表【dish_flavor(菜品口味关系表)】的数据库操作Service实现
* @createDate 2022-10-31 20:54:02
*/
@Service
public class DishFlavorServiceImpl extends ServiceImpl<DishFlavorMapper, DishFlavor>
    implements DishFlavorService{

}




