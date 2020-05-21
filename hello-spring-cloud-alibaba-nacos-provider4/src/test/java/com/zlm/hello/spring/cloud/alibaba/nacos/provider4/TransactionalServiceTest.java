package com.zlm.hello.spring.cloud.alibaba.nacos.provider4;

import com.zlm.hello.spring.cloud.alibaba.nacos.provider4.service.TransactionalService;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@Slf4j
@RunWith(SpringRunner.class)
@SpringBootTest(classes = NacosProviderApplication4.class)
public class TransactionalServiceTest {

    @Autowired
    private TransactionalService transactionalService;

    @Test
    public void notransaction_exception_required_required(){
        transactionalService.transaction_required_required_exception_try();
    }
}
