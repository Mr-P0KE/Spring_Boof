package com.poke.service;

import com.poke.PoJo.Orders;
import com.baomidou.mybatisplus.extension.service.IService;

/**
* @author 56207
* @description 针对表【orders(订单表)】的数据库操作Service
* @createDate 2022-10-31 20:54:02
*/
public interface OrdersService extends IService<Orders> {

    void submit(Orders orders);
}
