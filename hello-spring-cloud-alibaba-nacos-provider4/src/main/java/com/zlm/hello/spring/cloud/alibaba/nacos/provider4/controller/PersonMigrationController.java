package com.zlm.hello.spring.cloud.alibaba.nacos.provider4.controller;

import com.zlm.hello.spring.cloud.alibaba.nacos.provider4.service.PersonMigrationService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("migration")
@RestController
@Slf4j
public class PersonMigrationController implements InitializingBean {

    @Autowired
    private PersonMigrationService personMigrationService;


    @PostMapping("/person/downSql")
    public String downSql()   {
        personMigrationService.downSql();
        return "ok";
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        System.out.println("控制器启动================================");
    }
}
