package com.zlm.hello.spring.cloud.alibaba.nacos.consumer.controller;

import com.alibaba.csp.sentinel.annotation.SentinelResource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.loadbalancer.LoadBalancerClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
public class NacosConsumerController {
    @Autowired
    private LoadBalancerClient loadBalancerClient;

    @Autowired
    private RestTemplate restTemplate;

    @Value("${spring.application.name}")
    private String appName;

    @GetMapping(value = "/echo/app/name")
    public String echo() {
        //使用 LoadBalanceClient 和 RestTemplate 结合的方式来访问
        ServiceInstance serviceInstance = loadBalancerClient.choose("nacos-provider");
        String url = String.format("http://%s:%s/echo/%s", serviceInstance.getHost(), serviceInstance.getPort(), appName);
        return restTemplate.getForObject(url, String.class);
    }

    @GetMapping(value = "/invoke/{dynamicService}")
    @SentinelResource(value = "testDynamicService", fallback = "testDynamicServiceFallback")
    public String testDynamicService(@PathVariable String dynamicService ) {
        //使用 LoadBalanceClient 和 RestTemplate 结合的方式来访问
        ServiceInstance serviceInstance = loadBalancerClient.choose(dynamicService);
        if (serviceInstance == null) {
            return "你要测试的服务未找到";
        }
        String url = String.format("http://%s:%s/echoByKai/%s", serviceInstance.getHost(), serviceInstance.getPort(), dynamicService);
        return restTemplate.getForObject(url, String.class);
    }

    public String testDynamicServiceFallback() {
        return "由于调用底层服务发生未知异常";
    }
}
