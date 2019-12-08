package com.zlm.hello.spring.cloud.alibaba.nacos.provider;

import com.zlm.hello.spring.cloud.alibaba.nacos.provider.dao.UserMapper;
import com.zlm.hello.spring.cloud.alibaba.nacos.provider.model.User;
import com.zlm.hello.spring.cloud.alibaba.nacos.provider.service.UserService;
import com.zlm.hello.spring.cloud.alibaba.nacos.provider.thread.CompletableFutureDemo;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;
import java.util.stream.IntStream;

@Slf4j
@RunWith(SpringRunner.class)
@SpringBootTest(classes = NacosProviderApplication.class)
public class TestFuture {
    @Autowired
    private CompletableFutureDemo completableFutureDemo;

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private UserService userService;

    @Test
    public void testCheck(){
        List<User> users = userMapper.selectUsers();
        long start = System.currentTimeMillis();
        completableFutureDemo.checkStream(users);
        long end = System.currentTimeMillis();
        System.out.println("耗时："+(end-start));
        users.forEach(System.err::println);
    }
    @Test
    public void insertUser(){
        userService.insertForeach();

    }
    @Test
    public void testStream(){
        IntStream.range(0,10).boxed().forEach(System.err::println);

    }
}
