package com.zlm.hello.spring.cloud.alibaba.nacos.provider.model;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

@Data
public class User implements Serializable {



    private static final long serialVersionUID = -1914801793478594984L;
    @ApiModelProperty("用户ID")
    private Integer id;
    @ApiModelProperty("用户名字")
    private String name;
    @ApiModelProperty("用户密码")
    private String password;
}
