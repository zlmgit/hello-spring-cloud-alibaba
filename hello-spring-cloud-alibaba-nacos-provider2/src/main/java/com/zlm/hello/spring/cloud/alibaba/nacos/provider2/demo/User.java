package com.zlm.hello.spring.cloud.alibaba.nacos.provider2.demo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.hibernate.validator.constraints.Length;
import org.springframework.data.annotation.Transient;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import java.io.Serializable;

@Data
public class User implements Serializable {



    private static final long serialVersionUID = -1914801793478594984L;


    private Integer id;


    private String name;


    private String password;

    private Car car;

    public User(Integer id, String name, String password) {
        this.id = id;
        this.name = name;
        this.password = password;
    }

    public User() {
    }
}
