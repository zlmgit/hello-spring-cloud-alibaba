package com.zlm.hello.spring.cloud.alibaba.nacos.provider;

import com.google.common.base.Charsets;
import com.zlm.hello.spring.cloud.alibaba.nacos.provider.dao.UserMapper;
import com.zlm.hello.spring.cloud.alibaba.nacos.provider.model.User;
import com.zlm.hello.spring.cloud.alibaba.nacos.provider.service.UserService;
import com.zlm.hello.spring.cloud.alibaba.nacos.provider.thread.CompletableFutureDemo;
import com.zlm.hello.spring.cloud.alibaba.nacos.provider.utils.RedisUtil;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.RedisTemplate;
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

    @Autowired
    private RedisTemplate redisTemplate;

    @Autowired
    private RedisUtil redisUtil;

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
        //IntStream.range(0,10).boxed().forEach(System.err::println);

    }
    @Test
    public void testBatchRedis(){
        long start = System.currentTimeMillis();
        //使用pipeline方式
        redisTemplate.executePipelined(new RedisCallback<List<Object>>() {
            @Override
            public List<Object> doInRedis(RedisConnection connection) throws DataAccessException {
                for (int i =0; i<1000 ; i++) {
                    String key = "hello" + ":" +i + "#" + "world";
                    byte[] rawKey = key.getBytes(Charsets.UTF_8);
                    connection.setEx(rawKey, 10, (i + "#" + "value").getBytes(Charsets.UTF_8));
                }
                return null;
            }
        });
        long end = System.currentTimeMillis();
        System.err.println("耗时："+(end-start));
        //
    }
    @Test
    public void testBatchRedis1(){
        long start = System.currentTimeMillis();
        for (int i =0; i<1000 ; i++) {
            String key = "hello" + ":" +i + "#" + "world";
            redisUtil.setForTimeMS(key,(i + "#" + "value"),10);
        }
        long end = System.currentTimeMillis();
        System.err.println("耗时："+(end-start));
        //2668
    }
}
