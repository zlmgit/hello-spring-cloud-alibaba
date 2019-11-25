package com.zlm.hello.spring.cloud.alibaba.nacos.provider2.demo;

import sun.security.krb5.internal.PAForUserEnc;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

public class Demo {
    public static void main(String[] args) {

        List list = new ArrayList<>();
        list.add(new User(1,"Zlm","dhvuodcbuodbcvuott"));
        list.add(new User(2,"Zlm","dhvuodcbuodbcvuott"));
        list.add(new User(3,"Zlm","dhvuodcbuodbcvuott"));
        list.add(new User(4,"Zlm","dhvuodcbuodbcvuott"));
        list.add(new User(5,"Zlm","dhvuodcbuodbcvuott"));
        list.stream().collect(Collectors.toMap(User::getId, u -> u));
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
