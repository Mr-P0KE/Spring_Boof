package com.poke.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.poke.PoJo.ShoppingCart;
import com.poke.service.ShoppingCartService;
import com.poke.mapper.ShoppingCartMapper;
import org.springframework.stereotype.Service;

/**
* @author 56207
* @description 针对表【shopping_cart(购物车)】的数据库操作Service实现
* @createDate 2022-10-31 20:54:03
*/
@Service
public class ShoppingCartServiceImpl extends ServiceImpl<ShoppingCartMapper, ShoppingCart>
    implements ShoppingCartService{

}




