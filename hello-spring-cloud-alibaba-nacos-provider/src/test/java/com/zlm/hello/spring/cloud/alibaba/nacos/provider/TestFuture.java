package com.zlm.hello.spring.cloud.alibaba.nacos.provider;

import com.zlm.hello.spring.cloud.alibaba.nacos.provider.dao.UserMapper;
import com.zlm.hello.spring.cloud.alibaba.nacos.provider.model.User;
import com.zlm.hello.spring.cloud.alibaba.nacos.provider.thread.FutureTest;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

@Slf4j
@RunWith(SpringRunner.class)
@SpringBootTest(classes = NacosProviderApplication.class)
public class TestFuture {
    @Autowired
    private FutureTest futureTest;

    @Autowired
    private UserMapper userMapper;

    @Test
    public void testCheck(){
        List<User> users = userMapper.selectUsers();
        long start = System.currentTimeMillis();
        futureTest.check(users);
        long end = System.currentTimeMillis();
        System.out.println(end-start);
        System.out.println(FutureTest.MESSAGE_RETRY_TIME);
    }
}
