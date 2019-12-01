package com.zlm.hello.spring.cloud.alibaba.nacos.provider3;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
public class NacosProviderApplication3 {
    public static void main(String[] args) {
        SpringApplication.run(NacosProviderApplication3.class);
    }
}
