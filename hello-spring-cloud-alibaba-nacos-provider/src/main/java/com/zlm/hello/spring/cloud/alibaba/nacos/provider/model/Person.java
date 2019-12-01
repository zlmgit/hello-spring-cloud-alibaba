package com.zlm.hello.spring.cloud.alibaba.nacos.provider.model;

import lombok.Data;

@Data
public class Person {

    private String id;

    private String name;

    private String sex;

    public Person() {
    }

    public Person(String id, String name, String sex) {
        this.id = id;
        this.name = name;
        this.sex = sex;
    }
}
