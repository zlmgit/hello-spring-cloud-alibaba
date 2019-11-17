package com.zlm.hello.spring.cloud.alibaba.nacos.provider.service.impl;

import com.zlm.hello.spring.cloud.alibaba.nacos.provider.dao.UserMapper;
import com.zlm.hello.spring.cloud.alibaba.nacos.provider.model.User;
import com.zlm.hello.spring.cloud.alibaba.nacos.provider.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.TransactionCallback;
import org.springframework.transaction.support.TransactionTemplate;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserMapper userMapper;

    @Autowired
    private TransactionTemplate transactionTemplate;

    @Override
    public User selectUserOne(Integer id) {
        return userMapper.selectUserOne(id);
    }

    @Override
    public int updateUserById(User user) {
        int i = userMapper.updateUserById(user);
        return i;
    }

    @Override
    public int insertUser(User user) {
        int result = (int) transactionTemplate.execute(new TransactionCallback<Object>() {
            @Override
            public Object doInTransaction(TransactionStatus transactionStatus) {
                int i = userMapper.insertUser(user);
                int z = 1/0;
                if (i == 1) {
                    int i1 = updateUserById(user);
                }
                return i;
            }

        });
        return result;
    }
}
