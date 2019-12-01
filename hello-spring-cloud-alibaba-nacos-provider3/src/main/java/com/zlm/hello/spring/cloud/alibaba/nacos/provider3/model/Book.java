package com.zlm.hello.spring.cloud.alibaba.nacos.provider3.model;

import java.util.Date;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

@Document(collection="book")
@Data
public class Book {

    @Id
    @Field("id")
    private String id;
    @Field("price")
    private Integer price;
    @Field("name")
    private String name;
    @Field("info")
    private String info;
    @Field("publish")
    private String publish;
    @Field("createTime")
    private Date createTime;
    @Field("updateTime")
    private Date updateTime;
}

