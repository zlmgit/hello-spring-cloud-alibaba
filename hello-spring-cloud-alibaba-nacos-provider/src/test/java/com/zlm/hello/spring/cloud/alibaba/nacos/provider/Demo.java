package com.zlm.hello.spring.cloud.alibaba.nacos.provider;

import java.math.BigDecimal;

public class Demo {
    public static void main(String[] args) {
        BigDecimal bigDecimal = new BigDecimal("20");
        BigDecimal divide = bigDecimal.divide(BigDecimal.valueOf(100D), 4, BigDecimal.ROUND_HALF_UP);
        System.out.println(divide);
    }
}
