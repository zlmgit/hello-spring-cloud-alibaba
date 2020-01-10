package com.zlm.hello.spring.cloud.alibaba.nacos.provider;

import com.zlm.hello.spring.cloud.alibaba.nacos.provider.model.User;
import org.apache.commons.lang3.StringUtils;
import org.junit.Test;

import java.math.BigDecimal;
import java.util.*;
import java.util.concurrent.locks.ReentrantLock;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class Demo {
    public static void main(String[] args) {
        BigDecimal bigDecimal = new BigDecimal("20");
        BigDecimal divide = bigDecimal.divide(BigDecimal.valueOf(100D), 4, BigDecimal.ROUND_HALF_UP);
        System.out.println(divide);
        double pow = Math.pow(5, 2);
        List<Integer> MESSAGE_DELAY_LEVEL = Arrays.asList(1,5,10,30,16,60,120,180,240,300,360,420,480,540,600,1200,1800,3600,7200);
       // System.out.println(">>>>>>"+MESSAGE_DELAY_LEVEL.get(18));
        System.out.println( StringUtils.join("hello","-","world"));;
    }
    @Test
    public void testLambda(){
        List<User> list = new ArrayList<>();
        list.add(new User(1,"Zlm",null));
        list.add(new User(2,"Zlm","456"));
        list.add(new User(3,"Zlm","789"));
        list.add(new User(4,"Zlm","321"));
        list.add(new User(1,"zs","987"));
        Map<Integer, List<User>> collect = list.parallelStream().collect(Collectors.groupingBy(User::getId));
        //System.out.println(collect);
        list.sort(Comparator.comparing(u -> Optional.ofNullable(u.getPassword()).orElse("")));
        System.out.println(list);
    }

    @Test
    public void test(){
        List<Integer> list1 = new ArrayList<>();
        IntStream.range(0, 10000).forEach(list1::add);
        long start = System.currentTimeMillis();
        List collect = IntStream.range(0, 5).boxed()
                .parallel()
                .map(item->{
                    if(item==1){
                        try {
                            Thread.sleep(500);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                    if(item==2){
                        try {
                            Thread.sleep(1000);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                    if(item==3){
                        try {
                            Thread.sleep(900);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                    return "rw"+item;
                })
                //.sorted()
                .collect(Collectors.toList());
        long end = System.currentTimeMillis();
        System.out.println("耗时："+(end-start));
        System.out.println("串行执行的大小：" + list1.size());
        System.out.println("并行执行的大小：" + collect);


        int i =0;
        for(int j = 0 ; j<5;j++){
            i++;
            System.out.println(i-1);
        }
    }

    @Test
    public void testDecimal(){
        BigDecimal decimal = new BigDecimal("2sdd");
       /* BigDecimal decimal1 = new BigDecimal("2.00");
        BigDecimal decimal2 = new BigDecimal("0.018");
        BigDecimal setScale = decimal.multiply(decimal1).setScale(2, BigDecimal.ROUND_HALF_UP);
        System.err.println(decimal.compareTo(decimal1)!=0);*/
        ReentrantLock lock = new ReentrantLock();
    }
}
