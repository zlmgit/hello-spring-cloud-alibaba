package com.zlm.hello.spring.cloud.alibaba.nacos.consumer;

import com.alibaba.cloud.sentinel.annotation.SentinelRestTemplate;
import com.netflix.loadbalancer.IRule;
import com.netflix.loadbalancer.RandomRule;
import com.zlm.hello.spring.cloud.alibaba.nacos.consumer.util.ExceptionUtil;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

@SpringBootApplication
@EnableDiscoveryClient
public class NacosConsumerApplication {
    public static void main(String[] args) {
        SpringApplication.run(NacosConsumerApplication.class, args);
    }

    @Bean
    public IRule initIRule() {
        return new RandomRule();
    }
    @Bean
//    @LoadBalanced
    @SentinelRestTemplate(blockHandler = "handleException", blockHandlerClass = ExceptionUtil.class,fallback = "handleFallBack",fallbackClass = ExceptionUtil.class)
    public RestTemplate initRestTemplate(){
        return new RestTemplate();
    }
}
