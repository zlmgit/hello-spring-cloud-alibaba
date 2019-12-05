package com.zlm.hello.spring.cloud.alibaba.nacos.provider.service;

import com.zlm.hello.spring.cloud.alibaba.nacos.provider.model.User;

import java.util.List;

public interface UserService {

    /**
     * 根据Id查询用户
     * @param id
     * @return
     */
    User selectUserOne(Integer id);


    /**
     * 根据用户Id更新用户
     * @param user
     * @return
     */
    int updateUserById(User user);

    /**
     * 新增用户
     * @param user
     * @return
     */
    int insertUser(User user);

    /**
     * 事务测试
     * 两条都会回滚
     */
    void testTransactional(User user);

    /**
     * 事务测试
     * 两条都会回滚
     */
    void testTransactional2(User user);

    int insertForeach();
}
