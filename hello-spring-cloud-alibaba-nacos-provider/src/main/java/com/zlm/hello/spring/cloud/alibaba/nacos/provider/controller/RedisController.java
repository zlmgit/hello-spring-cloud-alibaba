package com.zlm.hello.spring.cloud.alibaba.nacos.provider.controller;

import com.zlm.hello.spring.cloud.alibaba.nacos.provider.aspect.ActionLog;
import com.zlm.hello.spring.cloud.alibaba.nacos.provider.model.User;
import com.zlm.hello.spring.cloud.alibaba.nacos.provider.redis.RedisService;
import com.zlm.hello.spring.cloud.alibaba.nacos.provider.utils.JsonUtils;
import com.zlm.hello.spring.cloud.alibaba.nacos.provider.utils.RedisUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@Api(tags = "redis测试控制器")
public class RedisController {

    @Autowired
    private RedisService redisService;

    @Autowired
    private RedisUtil redisUtil;

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
    @GetMapping("/increment/{key}")
    @ApiOperation("获取缓存过期时间")
    public Long increment(@PathVariable String key){
        Long increment = redisService.increment(key);
        return increment;
    }
    @GetMapping("/insertList")
    public Long insertList(){

        List list = new ArrayList<>();
        list.add(new User(1,"Zlm","123"));
        list.add(new User(2,"Zlm","456"));
        list.add(new User(3,"Zlm","789"));
        list.add(new User(4,"Zlm","321"));
        list.add(new User(5,"Zlm","987"));


        return redisUtil.rightPushAll("list-zlm",list);
    }
    @GetMapping("/getList")
    public Object getList(){
        List<User> listUser= Arrays.asList(redisUtil.listRange("list-zlm", 0, redisUtil.listSize("list-zlm")).toArray(new User[0]));
        return listUser;
    }
    @GetMapping("/insertMap")
    public void insertMap(){

        List<User> list = new ArrayList<>();
        list.add(new User(1,"Zlm","dhvuodcbuodbcvuott"));
        list.add(new User(2,"Zlm","dhvuodcbuodbcvuott"));
        list.add(new User(3,"Zlm","dhvuodcbuodbcvuott"));
        list.add(new User(4,"Zlm","dhvuodcbuodbcvuott"));
        list.add(new User(5,"Zlm","dhvuodcbuodbcvuott"));
        Map<String, String> collect = list.stream().collect(Collectors.toMap( item->String.valueOf(item.getId()), u -> JsonUtils.objectToJson(u)));
        redisUtil.putAll("hash-zlm",collect);
    }

    @GetMapping("/getMapValue/{key}")
    public Object getMapValue(@PathVariable String key){
        Object o = redisUtil.getHashKey("hash-zlm", key);
        System.out.println(o);
        return o;
    }

    @GetMapping("/getHashValues")
    public Object getHashValues(){

        Map<Integer, String> entries = (Map<Integer, String>)(Object)redisUtil.getHashEntries("hash-zlm");
        //List<User> users = entries.values().stream().filter(item -> item.getName().equals("zlm")).collect(Collectors.toList());
        return entries;
    }

    @GetMapping("/delete/{key}")
    public Boolean deleteKey(@PathVariable String key){


        return  redisUtil.delete(key);
    }

    /**
     * 添加set
     * @return
     */
    @GetMapping("/insertSet/{value}")
    public Long insertSet(@PathVariable String value){


        return  redisUtil.add("zlm-set",value);
    }
    /**
     * 判断set是否有value
     * @return
     */
    @GetMapping("/isMember/{value}")
    public Boolean isMember(@PathVariable String value){

        return  redisUtil.isMember("zlm-set",value);
    }

}
