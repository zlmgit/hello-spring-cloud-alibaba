package com.zlm.hello.spring.cloud.alibaba.nacos.provider;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;

public class Demo {
    public static void main(String[] args) {
        BigDecimal bigDecimal = new BigDecimal("20");
        BigDecimal divide = bigDecimal.divide(BigDecimal.valueOf(100D), 4, BigDecimal.ROUND_HALF_UP);
        System.out.println(divide);
        double pow = Math.pow(5, 2);
        List<Integer> MESSAGE_DELAY_LEVEL = Arrays.asList(1,5,10,30,16,60,120,180,240,300,360,420,480,540,600,1200,1800,3600,7200);
        System.out.println(">>>>>>"+MESSAGE_DELAY_LEVEL.get(18));
    }
}
