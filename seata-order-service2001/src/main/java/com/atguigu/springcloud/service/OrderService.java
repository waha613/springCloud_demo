package com.atguigu.springcloud.service;

import com.atguigu.springcloud.domain.Order;

/**
 * @author ll4859332@hotmail.com
 * @version V1.0
 * @title
 * @description
 * @date 2022-06-22 16:48
 */
public interface OrderService {

    /**
     * 创建订单
     */
    void create(Order order);
}
