package com.zlm.hello.spring.cloud.alibaba.nacos.provider;

import com.zlm.hello.spring.cloud.alibaba.nacos.provider.utils.ExcelInterface;

public enum PersonEnum implements ExcelInterface {

    ID("id", "ID"),
    NAME("name", "姓名"),
    SEX("sex", "性别"),
    ;

    private String key;
    private String name;

    PersonEnum(String key, String name) {
        this.key = key;
        this.name = name;
    }
    @Override
    public String getKey() {
        return key;
    }

    @Override
    public String getName() {
        return name;
    }

}
