package com.zlm.hello.spring.cloud.alibaba.nacos.provider;

import lombok.extern.slf4j.Slf4j;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;

@Slf4j
@RunWith(SpringRunner.class)
@SpringBootTest(classes = NacosProviderApplication1.class)
@AutoConfigureMockMvc
public class TestUserImpl {

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void testGetUser() throws Exception {
        int status = mockMvc
                .perform(MockMvcRequestBuilders
                        .get("/getUser/1")
                        )
                .andDo(MockMvcResultHandlers.print())
                .andReturn().getResponse().getStatus();
        if (status == 200) {
            log.info("请求成功");
        } else {
            log.info("请求失败，状态码为：{}", status);
        }

        Assert.assertEquals(status, 200);
    }
}
