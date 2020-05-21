package com.zlm.hello.spring.cloud.alibaba.nacos.provider4.service;

import com.zlm.hello.spring.cloud.alibaba.nacos.provider4.model.User1;

public interface User1Service {
    void addRequired(User1 user);

    void addRequiresNew(User1 user);

    void addNested(User1 user);

}
