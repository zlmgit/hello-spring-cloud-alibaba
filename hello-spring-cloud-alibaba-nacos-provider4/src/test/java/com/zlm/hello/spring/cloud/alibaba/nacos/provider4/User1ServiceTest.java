package com.zlm.hello.spring.cloud.alibaba.nacos.provider4;

import com.zlm.hello.spring.cloud.alibaba.nacos.provider4.dao.User1Mapper;
import com.zlm.hello.spring.cloud.alibaba.nacos.provider4.model.User1;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@Slf4j
@RunWith(SpringRunner.class)
@SpringBootTest(classes = NacosProviderApplication4.class)
public class User1ServiceTest {

    @Autowired
    private User1Mapper user1Mapper;

    @Test
    public void insert(){
        User1 user1 = new User1();
        user1.setName("张三");
        user1Mapper.insertUser(user1);
    }
}
