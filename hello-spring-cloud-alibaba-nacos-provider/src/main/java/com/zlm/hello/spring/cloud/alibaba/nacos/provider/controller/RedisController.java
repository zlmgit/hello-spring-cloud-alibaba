package com.zlm.hello.spring.cloud.alibaba.nacos.provider.controller;

import com.zlm.hello.spring.cloud.alibaba.nacos.provider.aspect.ActionLog;
import com.zlm.hello.spring.cloud.alibaba.nacos.provider.redis.RedisService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Api(tags = "redis测试控制器")
public class RedisController {

    @Autowired
    private RedisService redisService;

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

    @ActionLog(value ="获取缓存过期时间" ,module = "缓存模块" , action = "/getExpireTime/{key}" , printReturnArg = true)
    @GetMapping("/getExpireTime/{key}")
    @ApiOperation("获取缓存过期时间")
    public Long mqProvider(@PathVariable String key){
        Long expire = redisService.getExpireByKey(key);
        return expire;
    }
}
