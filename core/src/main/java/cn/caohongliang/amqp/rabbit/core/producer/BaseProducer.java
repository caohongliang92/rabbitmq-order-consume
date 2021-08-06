package cn.caohongliang.amqp.rabbit.core.producer;

import cn.caohongliang.amqp.rabbit.core.constants.RabbitQueue;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.springframework.amqp.core.MessageBuilder;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.Assert;

import java.nio.charset.StandardCharsets;
import java.util.UUID;

/**
 * 生产者
 *
 * @author caohongliang
 */
public abstract class BaseProducer<T> {
	private static final Gson gson = new GsonBuilder().disableHtmlEscaping().create();
	@Autowired
	private RabbitTemplate rabbitTemplate;

	/**
	 * 指定要发送的队列
	 *
	 * @return 队列
	 */
	public abstract RabbitQueue getQueue();

	/**
	 * 发送消息到MQ
	 *
	 * @param msg 消息
	 * @return 返回消息ID
	 */
	public String send(T msg) {
		return send(msg, 0);
	}

	/**
	 * 发送延迟消息到MQ
	 *
	 * @param msg   消息
	 * @param delay 延迟秒数
	 * @return 返回消息ID
	 */
	public String send(T msg, int delay) {
		Assert.isTrue(delay >= 0, "delay必须大于或等于0");
		//TODO caohongliang 2021/8/6 需要设置链路追踪
		String exchangeName = this.getQueue().getExchange().getExchangeName();
		String routingKey = this.getQueue().getRoutingKey();
		byte[] body = gson.toJson(msg).getBytes(StandardCharsets.UTF_8);
		MessageProperties properties = new MessageProperties();
		MessageBuilder messageBuilder = MessageBuilder.withBody(body).andProperties(properties);
		//设置消息ID
		String messageId = UUID.randomUUID().toString();
		messageBuilder.setMessageId(messageId);
		if (delay > 0) {
			//延迟队列，需要安装 rabbitmq_delayed_message_exchange 插件
			//https://www.rabbitmq.com/community-plugins.html
			properties.setDelay(delay);
		}
		rabbitTemplate.send(exchangeName, routingKey, messageBuilder.build());
		return messageId;
	}
}
