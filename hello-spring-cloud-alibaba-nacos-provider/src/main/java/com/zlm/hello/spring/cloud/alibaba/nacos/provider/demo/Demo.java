package com.zlm.hello.spring.cloud.alibaba.nacos.provider.demo;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.zlm.hello.spring.cloud.alibaba.nacos.provider.model.User;
import org.apache.commons.lang3.StringUtils;

import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

public class Demo {
    public static void main(String[] args) {

        List<User> list = new ArrayList<>();
        list.add(new User(1,"Zlm","zxcsdv"));
        list.add(new User(2,"Zlm","dhvuodcbuodbcvuott"));
        list.add(new User(1,"hhh","dhvuodcbuodbcvuott"));
        list.add(new User(4,"Zlm","dhvuodcbuodbcvuott"));
        list.add(new User(5,"Zlm","dhvuodcbuodbcvuott"));
        Map<String,Object> map = new HashMap();
        map.put("name","Zlm");map.put("age",10);
        Map<String,Object> map1 = new HashMap();
        map1.put("name","hhh");map1.put("age",20);
        List<Map<String,Object>> lists = new ArrayList<>();
        lists.add(map);lists.add(map1);
        List<String> names = lists.stream().map(item -> (String) item.get("name")).collect(Collectors.toList());
        List<User> users = names.stream().map(item ->
                list.stream().filter(user -> item.equals(user.getName()))
                        .findAny()
                        .orElse(null)
        ).filter(Objects::nonNull).collect(Collectors.toList());
        ObjectMapper mapper = new ObjectMapper();
        //System.out.println(users);

        /*System.out.println(StringUtils.isEmpty(null));
        System.out.println(StringUtils.isEmpty(""));
        System.out.println(StringUtils.isEmpty(" "));
        System.out.println(StringUtils.isEmpty("baba"));
        System.out.println(StringUtils.isEmpty("baba "));*/
        System.err.println(StringUtils.isBlank(null));
        System.err.println(StringUtils.isBlank(""));
        System.err.println(StringUtils.isBlank("  "));
        System.err.println(StringUtils.isBlank("bbjh"));
        System.err.println(StringUtils.isBlank("Bab "));

    }

}
