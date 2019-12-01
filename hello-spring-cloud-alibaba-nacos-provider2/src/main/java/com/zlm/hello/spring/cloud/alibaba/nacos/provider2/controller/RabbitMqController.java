package com.zlm.hello.spring.cloud.alibaba.nacos.provider2.controller;

import com.rabbitmq.tools.json.JSONUtil;
import com.zlm.hello.spring.cloud.alibaba.nacos.provider2.model.Order;
import com.zlm.hello.spring.cloud.alibaba.nacos.provider2.rabbitmq.RabbitSender;
import com.zlm.hello.spring.cloud.alibaba.nacos.provider2.utils.JsonUtils;
import com.zlm.hello.spring.cloud.alibaba.nacos.provider2.utils.SendMqMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageHeaders;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.text.SimpleDateFormat;
import java.util.*;

@RestController
public class RabbitMqController {

    @Autowired
    private RabbitSender rabbitSender;

    @Autowired
    private SendMqMessage sendMqMessage;

    @GetMapping("/sendMsg")
    public String sentMsg() throws Exception {
        Map<String, Object> properties = getHeadMap();
        String json = getJsonString();
        rabbitSender.send(json,properties);
        return "ok";
    }

    @GetMapping("/sendQueueMsg")
    public String sendQueueMsg() throws Exception {
        Map<String, Object> properties = getHeadMap();
        String json = getJsonString();
        rabbitSender.send(json,properties);
        return "ok";
    }

    @GetMapping("/amqpSendMsg")
    public String amqpSendMsg() throws Exception {
        String msg = "hello";
        sendMqMessage.sendMsg(msg);
        return "ok";
    }
    @GetMapping("/sendDirectMsg")
    public String sendDirectMsg() throws Exception {
        Map<String, Object> properties = getHeadMap();
        String json = getJsonString();
        String msg = "hello";
        rabbitSender.sendDirect(json,properties);
        return "ok";
    }
    @GetMapping("/sendFanoutMessage")
    public String sendFanoutMessage() throws Exception {
        Map<String, Object> properties = getHeadMap();
        String json = getJsonString();
        String msg = "hello";
        rabbitSender.sendFanoutMessage(json,properties);
        return "ok";
    }

    private Map<String, Object> getHeadMap() {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
        Map<String, Object> properties = new HashMap<>();
        properties.put("number", "12345");
        properties.put("send_time", simpleDateFormat.format(new Date()));
        return properties;
    }

    private String getJsonString() {
        List<Order> list = new ArrayList<>();
        list.add(new Order("1", "白菜"));
        list.add(new Order("2", "萝卜"));
        return JsonUtils.objectToJson(list);
    }
}
