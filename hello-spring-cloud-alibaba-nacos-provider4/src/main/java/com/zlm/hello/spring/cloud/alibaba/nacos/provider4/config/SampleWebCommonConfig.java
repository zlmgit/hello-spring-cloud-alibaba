package com.zlm.hello.spring.cloud.alibaba.nacos.provider4.config;

import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;

import javax.annotation.PostConstruct;
import java.util.stream.Stream;

@Configuration
public class SampleWebCommonConfig {
    // 系统启动之后，如果需要初始化的某些东东，几种不同的方法：

    // 1
    @PostConstruct
    public void postConstruct(){
        System.out.println("system started, triggered by postConstruct.");
    }

    // 5
    @Bean
    public CommandLineRunner initData(){
        return (args) -> {
            System.out.println("system started, triggered by CommandLineRunner.");
            Stream.of(args).forEach(System.out::println);
        };
    }

    // 4
    @Bean
    public ApplicationRunner initData2(){
        return (args) -> {
            System.out.println("system started, triggered by ApplicationRunner.");
            Stream.of(args.getSourceArgs()).forEach(System.out::println);
        };
    }

    // 3
    @EventListener
    public void onApplicationEvent(ContextRefreshedEvent event) {
        System.out.println("system started, triggered by ContextRefreshedEvent.");
    }

    // 2
    @Bean(initMethod = "init")
    public InitMethodBean initMethodBean(){
        return new InitMethodBean();
    }

    private static class InitMethodBean{
        void init(){
            System.out.println("system started, triggered by initMethod property.");
        }
    }
}
