package com.zlm.hello.spring.cloud.alibaba.nacos.consumer.feign.service;

import com.zlm.hello.spring.cloud.alibaba.nacos.consumer.feign.fallback.EchoServiceFallback;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(value = "nacos-provider" ,fallback = EchoServiceFallback.class )
public interface EchoService {

    @GetMapping(value = "/echo/{message}")
    String echo(@PathVariable("message") String message);
}
