package com.zlm.hello.spring.cloud.alibaba.nacos.provider.service;

import com.zlm.hello.spring.cloud.alibaba.nacos.provider.model.Order;

public interface OrderService {

    int insertOrder(Order order);
}
