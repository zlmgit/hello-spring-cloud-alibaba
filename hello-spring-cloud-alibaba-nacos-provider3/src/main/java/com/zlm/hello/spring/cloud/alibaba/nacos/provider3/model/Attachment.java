package com.zlm.hello.spring.cloud.alibaba.nacos.provider3.model;

import lombok.Data;

@Data
public class Attachment {
    private String id;
    private String name;
    private String type;
    private String size;
    private Object file;
}
