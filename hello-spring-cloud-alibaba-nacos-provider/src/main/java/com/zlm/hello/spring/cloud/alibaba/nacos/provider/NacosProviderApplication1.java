package com.zlm.hello.spring.cloud.alibaba.nacos.provider;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@EnableDiscoveryClient
@MapperScan("com.zlm.hello.spring.cloud.alibaba.nacos.provider.dao")
public class NacosProviderApplication1 {
    public static void main(String[] args) {
        SpringApplication.run(NacosProviderApplication1.class, args);
    }

    @RestController
    public class EchoController {

        @Value("${server.port}")
        private String port;

        // 注入配置文件上下文
        @Autowired
        private ConfigurableApplicationContext applicationContext;

        @GetMapping(value = "/echo/{message}")
        public String echo(@PathVariable String message) {
            return "Hello Nacos Discovery " + message + " i am from port " + port;
        }
        // 从上下文中读取配置
        @GetMapping(value = "/hi")
        public String sayHi() {
            return "Hello " + applicationContext.getEnvironment().getProperty("user.name");
        }

    }
}
