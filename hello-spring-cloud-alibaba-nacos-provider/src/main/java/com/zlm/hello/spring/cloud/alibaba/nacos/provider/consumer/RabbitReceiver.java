package com.zlm.hello.spring.cloud.alibaba.nacos.provider.consumer;

import com.rabbitmq.client.Channel;
import com.zlm.hello.spring.cloud.alibaba.nacos.provider.model.Order;
import org.springframework.amqp.rabbit.annotation.*;
import org.springframework.amqp.support.AmqpHeaders;
import org.springframework.messaging.Message;
import org.springframework.messaging.handler.annotation.Headers;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class RabbitReceiver {

	
	@RabbitListener(bindings = @QueueBinding(
			value = @Queue(value = "queue-1",
			durable="true"),
			exchange = @Exchange(value = "exchange-1",
			durable="true", 
			type= "topic", 
			ignoreDeclarationExceptions = "true"),
			key = "springboot.*"
			)
	)
	@RabbitHandler
	public void onMessage(Message message, Channel channel) throws Exception {
		System.err.println("--------------------------------------");
		System.err.println("消费端Payload: " + message.getPayload());
		Long deliveryTag = (Long)message.getHeaders().get(AmqpHeaders.DELIVERY_TAG);
		//手工ACK
		channel.basicAck(deliveryTag, false);
	}
	
	
	/**
	 * 
	 * 	spring.rabbitmq.listener.order.queue.name=queue-2
		spring.rabbitmq.listener.order.queue.durable=true
		spring.rabbitmq.listener.order.exchange.name=exchange-1
		spring.rabbitmq.listener.order.exchange.durable=true
		spring.rabbitmq.listener.order.exchange.type=topic
		spring.rabbitmq.listener.order.exchange.ignoreDeclarationExceptions=true
		spring.rabbitmq.listener.order.key=springboot.*
	 * @param order
	 * @param channel
	 * @param headers
	 * @throws Exception
	 */
	@RabbitListener(bindings = @QueueBinding(
			value = @Queue(value = "${spring.rabbitmq.listener.order.queue.name}",
			durable="${spring.rabbitmq.listener.order.queue.durable}"),
			exchange = @Exchange(value = "${spring.rabbitmq.listener.order.exchange.name}",
			durable="${spring.rabbitmq.listener.order.exchange.durable}", 
			type= "${spring.rabbitmq.listener.order.exchange.type}", 
			ignoreDeclarationExceptions = "${spring.rabbitmq.listener.order.exchange.ignoreDeclarationExceptions}"),
			key = "${spring.rabbitmq.listener.order.key}"
			)
	)
	@RabbitHandler
	public void onOrderMessage(@Payload Order order,
			Channel channel,
			@Headers Map<String, Object> headers) throws Exception {
		System.err.println("--------------------------------------");
		System.err.println("消费端order: " + order.getId());
		Long deliveryTag = (Long)headers.get(AmqpHeaders.DELIVERY_TAG);
		//手工ACK
		channel.basicAck(deliveryTag, false);
	}

	@RabbitListener(bindings = @QueueBinding(
			value = @Queue(value = "queue-2",
					durable="true"),
			exchange = @Exchange(value = "exchange-queue",
					durable="true",
					type= "direct",
					ignoreDeclarationExceptions = "true"),
			key = "springboot.queue"
	)
	)
	@RabbitHandler
	public void onQueueMessage(Message message, Channel channel) throws Exception {
		System.err.println("--------------------------------------");
		System.err.println("消费端message: " + message);
		System.err.println("消费端Payload: " + message.getPayload());
		Long deliveryTag = (Long)message.getHeaders().get(AmqpHeaders.DELIVERY_TAG);
		//手工ACK
		channel.basicAck(deliveryTag, false);
	}

	//直连模式，不指定交换机走默认交换机，发送端的routingKey与队列名需完全相同
	@RabbitListener(queuesToDeclare =@Queue(value = "key-direct",durable = "true"))
	@RabbitHandler
	public void onDirectMessage(Message message, Channel channel) throws Exception {
		System.err.println("--------------------------------------");
		System.err.println("消费端message: " + message);
		System.err.println("消费端Payload: " + message.getPayload());
		Long deliveryTag = (Long)message.getHeaders().get(AmqpHeaders.DELIVERY_TAG);
		//手工ACK
		channel.basicAck(deliveryTag, false);
	}

	//fanout模式，只要是与交换机绑定的队列，都会受到消息 不需要关心routingKey
	@RabbitListener(bindings = @QueueBinding(
			value = @Queue(value = "fanout-queue",
					durable="true"),
			exchange = @Exchange(value = "fanout-exchange",
					durable="true",
					type= "fanout",
					ignoreDeclarationExceptions = "true")
	)
	)
	@RabbitHandler
	public void onFanoutMessage(Message message, Channel channel) throws Exception {
		System.err.println("--------------------------------------");
		System.err.println("消费端message: " + message);
		System.err.println("消费端Payload: " + message.getPayload());
		Long deliveryTag = (Long)message.getHeaders().get(AmqpHeaders.DELIVERY_TAG);
		//手工ACK
		channel.basicAck(deliveryTag, false);
	}
}
