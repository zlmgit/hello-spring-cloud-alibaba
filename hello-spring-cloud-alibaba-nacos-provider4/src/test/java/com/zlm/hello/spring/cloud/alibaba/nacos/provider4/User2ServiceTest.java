package com.zlm.hello.spring.cloud.alibaba.nacos.provider4;

import com.zlm.hello.spring.cloud.alibaba.nacos.provider4.dao.User2Mapper;
import com.zlm.hello.spring.cloud.alibaba.nacos.provider4.model.User2;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@Slf4j
@RunWith(SpringRunner.class)
@SpringBootTest(classes = NacosProviderApplication4.class)
public class User2ServiceTest {

    @Autowired
    private User2Mapper user2Mapper;

    @Test
    public void insert(){
        User2 user2 = new User2();
        user2.setName("张三");
        user2Mapper.insertUser(user2);
    }
}
