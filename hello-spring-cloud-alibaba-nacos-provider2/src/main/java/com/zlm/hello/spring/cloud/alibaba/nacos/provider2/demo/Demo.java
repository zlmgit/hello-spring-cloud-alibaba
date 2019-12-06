package com.zlm.hello.spring.cloud.alibaba.nacos.provider2.demo;

import com.alibaba.druid.support.json.JSONUtils;
import com.zlm.hello.spring.cloud.alibaba.nacos.provider2.utils.JsonUtils;
import org.apache.commons.collections4.MapUtils;
import org.junit.jupiter.api.Test;
import sun.security.krb5.internal.PAForUserEnc;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

public class Demo {
    public static void main(String[] args) {

        List<User> list = new ArrayList<>();
        list.add(new User(1,"Zlm","123"));
        list.add(new User(2,"Zlm","456"));
        list.add(new User(3,"Zlm","789"));
        list.add(new User(4,"Zlm","321"));
        list.add(new User(5,"Zlm","987"));
        //list.stream().collect(Collectors.toMap(User::getId, u -> u));
        //System.err.println(String.format("%s_%s","hello","world"));
        //testSwitch(list,6,"lm","123");

        Car car = new Car();
        car.setColor("yello");
        User user = new User();
        user.setName("zs");
        user.setCar(car);
        //System.out.println(JsonUtils.objectToJson(user));
        Map<String, Object> map = new HashMap<>();
        map.put("hello","world");
        List<Map<String, Object>> result = new ArrayList<>();
        result.add(map);
        map.put("zlm","test");
        System.out.println(result);
        System.err.println(MapUtils.getInteger(map,"hehe"));
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

    public static void testSwitch(List<User> users , Integer id,String name ,String password){
        User u = users.stream().filter(item -> {
            switch (id) {
                case 1:
                    return name.equals(item.getName()) && password.equals(item.getPassword());
                case 2:
                    return name.equals(item.getName()) && id.equals(item.getId());
                default:
                    throw new RuntimeException("支付方式未成功匹配");
            }
        }).findFirst().orElse(null);
        System.out.println(u);
    }
    @Test
    public void test(){
        Map<String,Object> map = new HashMap();
        map.put("name","Zlm");map.put("age",10);
        Map<String,Object> map1 = new HashMap();
        map1.put("name","hhh");map1.put("age",20);
        List<Map<String,Object>> lists = new ArrayList<>();
        lists.add(map);
        lists.add(map1);
        lists.get(1).put("name","zs");
        System.out.println(map1);
    }
}
