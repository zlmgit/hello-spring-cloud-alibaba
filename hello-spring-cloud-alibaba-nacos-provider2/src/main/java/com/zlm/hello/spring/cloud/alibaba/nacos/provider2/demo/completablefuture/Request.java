package com.zlm.hello.spring.cloud.alibaba.nacos.provider2.demo.completablefuture;

import lombok.Data;

@Data
public class Request {

    private String method;
    private int param;
    private String servieName;
}
