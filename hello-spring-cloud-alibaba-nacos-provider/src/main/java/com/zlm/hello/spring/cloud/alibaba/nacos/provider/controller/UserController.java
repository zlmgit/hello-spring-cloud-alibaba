package com.zlm.hello.spring.cloud.alibaba.nacos.provider.controller;

import com.zlm.hello.spring.cloud.alibaba.nacos.provider.aspect.ActionLog;
import com.zlm.hello.spring.cloud.alibaba.nacos.provider.model.User;
import com.zlm.hello.spring.cloud.alibaba.nacos.provider.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.stream.Collectors;
@Slf4j
@RestController
@Api(tags = "用户控制器")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping("getUser/{id}")
    @ApiOperation("根据用户ID用户")
    @ApiImplicitParams(@ApiImplicitParam(name = "id" ,value = "用户id" ,required = true,dataTypeClass = Integer.class))
    public User selectUserById(@PathVariable Integer id) {
        return userService.selectUserOne(id);
    }

    @PostMapping("updateUser")
    @ApiOperation("根据用户Id更新用户")
    public int updateUser(User user){
        return userService.updateUserById(user);
    }

    @PostMapping("insertUser")
    @ApiOperation("新增用户")
    public String insertUser(@RequestBody @Valid User user , BindingResult result){
        if (result.hasErrors()) {
            String resp = result.getAllErrors().stream().map(ObjectError::getDefaultMessage).collect(Collectors.joining(","));
            return resp;
        }
        int i = userService.insertUser(user);
        System.err.println(user);
        return i > 0 ? "新增用户成功" : "新增用户失败";
    }

    @PostMapping("transactiona1")
    @ApiOperation("编程编程式事务")
    @ActionLog(value ="事务测试1" ,module = "用户模块" , action = "transactiona1" , printReturnArg = true)
    public void transactional(User user){
        userService.testTransactional2(user);
    }

    @PostMapping("transactiona2")
    @ApiOperation("注解事务")
    public void transactiona2(User user){
        userService.testTransactional2(user);
    }

    @PostMapping("insertForeach")
    @ApiOperation("注解事务")
    public void insertForeach(){
        userService.insertForeach();
    }
}
