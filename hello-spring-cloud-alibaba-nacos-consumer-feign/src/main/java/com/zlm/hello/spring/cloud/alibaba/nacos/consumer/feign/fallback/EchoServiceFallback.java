package com.zlm.hello.spring.cloud.alibaba.nacos.consumer.feign.fallback;

import com.zlm.hello.spring.cloud.alibaba.nacos.consumer.feign.service.EchoService;
import org.springframework.stereotype.Component;

@Component
public class EchoServiceFallback implements EchoService {
    @Override
    public String echo(String message) {
        return "请求失败请检查你的网络";
    }
}
