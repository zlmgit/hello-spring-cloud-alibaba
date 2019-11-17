package com.zlm.hello.spring.cloud.alibaba.nacos.provider.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.support.TransactionTemplate;

@Configuration
public class ConfigBean {

    public TransactionTemplate getTransactionTemplate(){
        return new TransactionTemplate();
    }
}
