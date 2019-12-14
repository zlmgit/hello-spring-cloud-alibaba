package com.zlm.hello.spring.cloud.alibaba.nacos.provider.service.impl;

import com.zlm.hello.spring.cloud.alibaba.nacos.provider.dao.UserMapper;
import com.zlm.hello.spring.cloud.alibaba.nacos.provider.model.User;
import com.zlm.hello.spring.cloud.alibaba.nacos.provider.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.TransactionCallback;
import org.springframework.transaction.support.TransactionTemplate;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Slf4j
@Service
@Transactional
public class UserServiceImpl implements UserService {
    @Autowired
    private UserMapper userMapper;

    @Autowired
    private TransactionTemplate transactionTemplate;

    @Override
    public User selectUserOne(Integer id) {
        log.info("通过Id查询用户selectUserOne({})", id);
        return userMapper.selectUserOne(id);
    }

    @Override
    public int updateUserById(User user) {
        int i = userMapper.updateUserById(user);
        return i;
    }

    @Override
    public int insertUser(User user) {

        return userMapper.insertUser(user);
    }

    @Override
    public void testTransactional(User user) {
        int result = (int) transactionTemplate.execute(new TransactionCallback<Object>() {
            @Override
            public Object doInTransaction(TransactionStatus transactionStatus) {
                int i = insertUser(user);
                if (i == 1) {
                    int i1 = updateUserById(user);
                }
                int z = 1 / 0;
                return i;
            }
        });
    }

    @Override
    public void testTransactional2(User user) {
        updateUserById(user);
        insertUser(user);
        int i = 1 / 0;
    }

    @Override
    public int insertForeach() {
        List<User> users = new ArrayList<>();
        for(int i = 0;i<10;i++){
            String string = UUID.randomUUID().toString();
            User user = new User();
            user.setName(string.substring(0,5)+i);
            user.setPassword(string);
            users.add(user);
        }
        int i = userMapper.insertForeach(users);
        users.forEach(System.err::println);
        return i;
    }
}
