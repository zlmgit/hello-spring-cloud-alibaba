package com.zlm.hello.spring.cloud.alibaba.nacos.provider.demo;

import com.zlm.hello.spring.cloud.alibaba.nacos.provider.model.User;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

public class Demo {
    public static void main(String[] args) {

        List<User> list = new ArrayList<>();
        list.add(new User(1,"Zlm","dhvuodcbuodbcvuott"));
        list.add(new User(2,"Zlm","dhvuodcbuodbcvuott"));
        list.add(new User(3,"Zlm","dhvuodcbuodbcvuott"));
        list.add(new User(4,"Zlm","dhvuodcbuodbcvuott"));
        list.add(new User(5,"Zlm","dhvuodcbuodbcvuott"));
        Map<Integer, User> collect = list.stream().collect(Collectors.toMap(User::getId, u -> u));
        System.out.println(collect);
    }

}
