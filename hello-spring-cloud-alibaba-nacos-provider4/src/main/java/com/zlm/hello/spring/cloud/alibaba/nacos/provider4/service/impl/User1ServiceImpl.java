package com.zlm.hello.spring.cloud.alibaba.nacos.provider4.service.impl;

import com.zlm.hello.spring.cloud.alibaba.nacos.provider4.dao.User1Mapper;
import com.zlm.hello.spring.cloud.alibaba.nacos.provider4.model.User1;
import com.zlm.hello.spring.cloud.alibaba.nacos.provider4.service.User1Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service
public class User1ServiceImpl implements User1Service {
    @Autowired
    private User1Mapper user1Mapper;
    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public void addRequired(User1 user) {
        user1Mapper.insertUser(user);
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void addRequiresNew(User1 user){
        user1Mapper.insertUser(user);
    }

    @Override
    @Transactional(propagation = Propagation.NESTED)
    public void addNested(User1 user){
        user1Mapper.insertUser(user);
    }

}
