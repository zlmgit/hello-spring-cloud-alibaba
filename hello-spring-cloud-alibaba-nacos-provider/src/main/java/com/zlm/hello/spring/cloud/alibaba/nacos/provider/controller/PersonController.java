package com.zlm.hello.spring.cloud.alibaba.nacos.provider.controller;

import com.zlm.hello.spring.cloud.alibaba.nacos.provider.PersonEnum;
import com.zlm.hello.spring.cloud.alibaba.nacos.provider.model.Person;
import com.zlm.hello.spring.cloud.alibaba.nacos.provider.utils.ExportOutUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;

@RestController
@Api(tags = "个人中心")
@RequestMapping("/person")
public class PersonController {

    @GetMapping("/export")
    @ApiOperation(value = "项目流水记录导出", notes = "洽谈邀请记录导出，by:lister")
    public void export(HttpServletRequest request, HttpServletResponse response) throws Exception {
        List<Person> data = new ArrayList<>();
        data.add(new Person("1","zs","20"));
        data.add(new Person("2","ls","30"));
        data.add(new Person("3","zl","40"));
        ExportOutUtil.export(request, response, "人员列表导出", "人员列表导出",
                PersonEnum.values(), data);
    }
}
