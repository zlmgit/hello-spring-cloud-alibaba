package com.zlm.hello.spring.cloud.alibaba.nacos.provider2.model;

import lombok.Data;

import java.io.Serializable;
@Data
public class Order implements Serializable {

	private String id;
	private String name;
	
	public Order() {
	}
	public Order(String id, String name) {
		super();
		this.id = id;
		this.name = name;
	}

}
