package com.poke.Controller;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.poke.Comon.BaseContext;
import com.poke.PoJo.Dish;
import com.poke.PoJo.Setmeal;
import com.poke.PoJo.ShoppingCart;
import com.poke.Util.R;
import com.poke.service.DishService;
import com.poke.service.ShoppingCartService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Slf4j
@RequestMapping("/shoppingCart")
public class ShoppingCartController {

    @Autowired
    private ShoppingCartService shoppingCartService;
    
    @Autowired
    private DishService dishService;

//    http://localhost:8080/shoppingCart/list

    @GetMapping("/list")
    public R<List<ShoppingCart>> getCart(){
        Long currentId = BaseContext.getCurrentId();
        LambdaQueryWrapper<ShoppingCart> shoppingCartLambdaQueryWrapper = new LambdaQueryWrapper<>();
        shoppingCartLambdaQueryWrapper.eq(ShoppingCart::getUserId,currentId)
                .orderByAsc(ShoppingCart::getCreateTime);
        List<ShoppingCart> list = shoppingCartService.list(shoppingCartLambdaQueryWrapper);
        return R.success(list);
    }

    @PostMapping("add")
    public R<ShoppingCart> add(@RequestBody ShoppingCart shoppingCart){
        log.info("购物车中 ：={}",shoppingCart);
        Long userId = BaseContext.getCurrentId();
        shoppingCart.setUserId(userId);

        //判断添加的是菜品还是套餐
        Long dishId = shoppingCart.getDishId();
        LambdaQueryWrapper<ShoppingCart> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.eq(ShoppingCart::getUserId,userId)
                .eq(dishId != null,ShoppingCart::getDishId,dishId)
                .eq(dishId == null,ShoppingCart::getSetmealId,shoppingCart.getSetmealId());
        ShoppingCart one = shoppingCartService.getOne(lambdaQueryWrapper);

        //判断点餐物品是否存在
        if(!ObjectUtils.isEmpty(one)){
            one.setNumber(one.getNumber()+1);
            shoppingCartService.updateById(one);
        }else{
            shoppingCart.setNumber(1);
            shoppingCartService.save(shoppingCart);
            one = shoppingCart;
        }
        return  R.success(one);
    }

    //    http://localhost:8080/shoppingCart/sub 修改数量
    @PostMapping("/sub")
    public R<String> update(@RequestBody ShoppingCart shoppingCart){

        Long userId = BaseContext.getCurrentId();
        shoppingCart.setUserId(userId);

        LambdaQueryWrapper<ShoppingCart> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.eq(shoppingCart.getDishId()!= null,ShoppingCart::getDishId,shoppingCart.getDishId())
                .eq(shoppingCart.getSetmealId()!=null,ShoppingCart::getSetmealId,shoppingCart.getSetmealId())
                .eq(ShoppingCart::getUserId,userId);
        ShoppingCart one = shoppingCartService.getOne(lambdaQueryWrapper);
        one.setNumber(one.getNumber() -1);
        shoppingCartService.updateById(one);
        return R.success("修改成功");

    }

    //http://localhost:8080/shoppingCart/clean 清空
    @DeleteMapping("/clean")
    public R<String> delete(){
        Long userId = BaseContext.getCurrentId();
        LambdaQueryWrapper<ShoppingCart> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.eq(ShoppingCart::getUserId,userId);
        shoppingCartService.remove(lambdaQueryWrapper);
        return R.success("删除成功");
    }

}
