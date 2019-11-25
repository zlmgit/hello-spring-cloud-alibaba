package com.zlm.hello.spring.cloud.alibaba.nacos.provider2.demo;

import java.util.concurrent.atomic.AtomicInteger;

public class Demo {
    public static void main(String[] args) {



    }
    public static int getInc(){
        int i = 1;

        try {
            Thread.sleep(100);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return ++i;
    }
    public static int getInc1(){
        int i = 1;
        AtomicInteger atomicInteger = new AtomicInteger(i);
        return atomicInteger.incrementAndGet();
    }
}
