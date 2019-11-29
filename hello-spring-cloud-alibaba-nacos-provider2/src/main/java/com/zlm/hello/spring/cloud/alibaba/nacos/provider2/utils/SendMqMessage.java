package com.zlm.hello.spring.cloud.alibaba.nacos.provider2.utils;

import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Created with IntelliJ IDEA.
 *
 * @author: jinweiwei
 * @date: 2019/1/31
 * Time: 10:07
 * To change this template use File | Settings | File Templates.
 * Description:
 */
@Component
public class SendMqMessage {

    @Autowired
    AmqpTemplate amqpTemplate;

    /**
     * 往指定交换机发送消息
     * @param msg
     */
    public void sendMsg(String msg){

        amqpTemplate.convertAndSend("exchange-1","springboot.abc",msg);
    }


}
