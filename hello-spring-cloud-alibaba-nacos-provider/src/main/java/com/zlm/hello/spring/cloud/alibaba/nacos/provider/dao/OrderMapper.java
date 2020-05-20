package com.zlm.hello.spring.cloud.alibaba.nacos.provider.dao;

import com.zlm.hello.spring.cloud.alibaba.nacos.provider.model.Order;
import com.zlm.hello.spring.cloud.alibaba.nacos.provider.model.User;

import java.util.List;


public interface OrderMapper {

    int insertOrder(Order order);
}
