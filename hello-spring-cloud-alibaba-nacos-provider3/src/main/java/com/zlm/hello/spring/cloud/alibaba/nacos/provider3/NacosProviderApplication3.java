package com.zlm.hello.spring.cloud.alibaba.nacos.provider3;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
@MapperScan("com.zlm.hello.spring.cloud.alibaba.nacos.provider3.dao")
public class NacosProviderApplication3 {
    public static void main(String[] args) {
        SpringApplication.run(NacosProviderApplication3.class);
    }
}
