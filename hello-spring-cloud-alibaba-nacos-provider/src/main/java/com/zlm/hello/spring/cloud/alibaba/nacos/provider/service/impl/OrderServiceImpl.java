package com.zlm.hello.spring.cloud.alibaba.nacos.provider.service.impl;

import com.zlm.hello.spring.cloud.alibaba.nacos.provider.dao.OrderMapper;
import com.zlm.hello.spring.cloud.alibaba.nacos.provider.model.Order;
import com.zlm.hello.spring.cloud.alibaba.nacos.provider.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service
public class OrderServiceImpl implements OrderService {

    @Autowired
    private OrderMapper orderMapper;
    @Override
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public int insertOrder(Order order) {
        int i = 10/0;
        return orderMapper.insertOrder(order);
    }
}
