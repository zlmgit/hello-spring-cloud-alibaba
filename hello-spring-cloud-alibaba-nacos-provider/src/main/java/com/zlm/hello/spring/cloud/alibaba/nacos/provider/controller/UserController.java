package com.zlm.hello.spring.cloud.alibaba.nacos.provider.controller;

import com.zlm.hello.spring.cloud.alibaba.nacos.provider.aspect.ActionLog;
import com.zlm.hello.spring.cloud.alibaba.nacos.provider.model.User;
import com.zlm.hello.spring.cloud.alibaba.nacos.provider.redis.RedisService;
import com.zlm.hello.spring.cloud.alibaba.nacos.provider.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.stream.Collectors;

@RestController
@Api(tags = "用户控制器")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private RedisService redisService;

    @GetMapping("getUser/{id}")
    @ApiOperation("根据用户ID用户")
    @ApiImplicitParams(@ApiImplicitParam(name = "id" ,value = "用户id" ,required = true,dataTypeClass = Integer.class))
    public User selectUserById(@PathVariable Integer id) {
        return userService.selectUserOne(id);
    }

    @PostMapping("insert/{key}/{value}")
    @ApiOperation("Redis新增接口插入key-value")
    public String insertRedis(@PathVariable(value = "key") String key ,@PathVariable(value = "value") String value){
        redisService.put(key,value ,60 );
        return "Ok";
    }
    @ActionLog(module = "用户模块" , action = "get/{key}" )
    @GetMapping("get/{key}")
    @ApiOperation("Redis根据Key获取值")
    public String getKey(@PathVariable(value = "key")String key ){

        Object json = redisService.get(key);
        if (json != null){
            return String.valueOf(json);
        }
        return "not_ok";
    }

    @ActionLog(value ="获取缓存过期时间" ,module = "用户模块" , action = "/getExpireTime/{key}" , printReturnArg = true)
    @GetMapping("/getExpireTime/{key}")
    @ApiOperation("获取缓存过期时间")
    public Long mqProvider(@PathVariable String key){
        Long expire = redisService.getExpireByKey(key);
        return expire;
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
        return i > 0 ? "新增用户成功" : "新增用户失败";
    }


}
