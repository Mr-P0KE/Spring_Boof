package com.poke.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.IdWorker;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.poke.Comon.BaseContext;
import com.poke.Comon.CustomException;
import com.poke.PoJo.*;
import com.poke.service.*;
import com.poke.mapper.OrdersMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ObjectUtils;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

/**
* @author 56207
* @description 针对表【orders(订单表)】的数据库操作Service实现
* @createDate 2022-10-31 20:54:02
*/
@Service
public class OrdersServiceImpl extends ServiceImpl<OrdersMapper, Orders>
    implements OrdersService{

    @Autowired
    private ShoppingCartService cartService;

    @Autowired
    private UserService userService;

    @Autowired
    private AddressBookService addressBookService;

    @Autowired
    private OrderDetailService orderDetailService;
    @Transactional
    @Override
    public void submit(Orders orders) {
        Long userId = BaseContext.getCurrentId();

        //查询购物车
        LambdaQueryWrapper<ShoppingCart> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.eq(ShoppingCart::getUserId,userId);
        List<ShoppingCart> shoppingCarts = cartService.list(lambdaQueryWrapper);

        if(CollectionUtils.isEmpty(shoppingCarts))
            throw new CustomException("购物车中没有食物，请选择后下单");


        //获取用户以及地址信息
        User u  = userService.getById(userId);
        AddressBook addressBook = addressBookService.getById(orders.getAddressBookId());
        if(ObjectUtils.isEmpty(addressBook))
            throw new CustomException("购物车为空，无法下单");

        long orderId = IdWorker.getId();//订单号

        //总金额  原子操作，保证多线程安全。
        AtomicInteger amount = new AtomicInteger(0);

        List<OrderDetail> list = new LinkedList<>();
        for(ShoppingCart item : shoppingCarts){
            OrderDetail orderDetail = new OrderDetail();
            orderDetail.setOrderId(orderId);
            orderDetail.setNumber(item.getNumber());
            orderDetail.setDishFlavor(item.getDishFlavor());
            orderDetail.setDishId(item.getDishId());
            orderDetail.setSetmealId(item.getSetmealId());
            orderDetail.setName(item.getName());
            orderDetail.setImage(item.getImage());
            orderDetail.setAmount(item.getAmount());
            list.add(orderDetail);
            //multiply乘法   addAndGet加
            amount.addAndGet(item.getAmount().multiply(new BigDecimal(item.getNumber())).intValue());
        }

        orders.setId(orderId);
        orders.setStatus(2);
        orders.setAmount(new BigDecimal(amount.get()));//总金额
        orders.setUserId(userId);
        orders.setNumber(String.valueOf(orderId));
        orders.setUserName(u.getName());
        orders.setConsignee(addressBook.getConsignee());
        orders.setPhone(addressBook.getPhone());
        orders.setAddress((addressBook.getProvinceName() == null ? "" : addressBook.getProvinceName())
                + (addressBook.getCityName() == null ? "" : addressBook.getCityName())
                + (addressBook.getDistrictName() == null ? "" : addressBook.getDistrictName())
                + (addressBook.getDetail() == null ? "" : addressBook.getDetail()));

        //订单表插入数据
        super.save(orders);

        //订单明细表中插入数据
        orderDetailService.saveBatch(list);

        //清空购物车数据
        cartService.remove(lambdaQueryWrapper);
    }
}




