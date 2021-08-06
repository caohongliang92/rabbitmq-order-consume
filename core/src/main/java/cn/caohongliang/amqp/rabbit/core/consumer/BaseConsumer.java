package cn.caohongliang.amqp.rabbit.core.consumer;

import cn.caohongliang.amqp.rabbit.core.config.CustomRabbitProperties;
import cn.caohongliang.amqp.rabbit.core.config.GeneralConsumerConfig;
import cn.caohongliang.amqp.rabbit.core.listener.OnMessageListener;
import cn.caohongliang.amqp.rabbit.core.constants.RabbitQueue;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;

import java.lang.reflect.ParameterizedType;

/**
 * 消费者
 *
 * @param <T>
 * @author caohongliang
 */
@Slf4j
public abstract class BaseConsumer<T> implements InitializingBean {
	private static final Gson gson = new GsonBuilder().disableHtmlEscaping().create();
	@Autowired
	private AmqpAdmin amqpAdmin;
	@Autowired
	private ConnectionFactory connectionFactory;
	@Autowired
	private CustomRabbitProperties rabbitProperties;
	private SimpleMessageListenerContainer container;
	private final Class<T> genericType;

	public BaseConsumer() {
		//获取泛型
		genericType = getGenericType();
	}

	@SuppressWarnings("all")
	private Class<T> getGenericType() {
		return (Class<T>) ((ParameterizedType) this.getClass().getGenericSuperclass()).getActualTypeArguments()[0];
	}

	/**
	 * 指定要消费的队列
	 *
	 * @return 队列
	 */
	public abstract RabbitQueue getQueue();


	/**
	 * 消费消息
	 *
	 * @param msg 消息内容
	 * @return 是否消费成功，返回false会让该条消息重新发到其他消费者进行消费
	 */
	public abstract boolean onMessage(T msg);

	@Override
	public void afterPropertiesSet() throws Exception {
		this.container = new SimpleMessageListenerContainer(connectionFactory);
		container.setAmqpAdmin(amqpAdmin);
	}

	/**
	 * 启动消费者
	 *
	 * @return
	 */
	public void start() {
		if (!this.isOrderly()) {
			//启动普通消费者
			startGeneralConsumers();
		} else {
			//顺序消费者
			startOrderlyConsumers();
		}
	}

	private void startGeneralConsumers() {
		String queueName = this.getQueue().getQueueName();
		GeneralConsumerConfig config = rabbitProperties.getGeneralConsumerConfig(queueName);

		Queue queue = new Queue(queueName, true);
		Exchange exchange = this.getQueue().getExchange().getExchange();
		Binding binding = BindingBuilder.bind(queue).to(exchange).with(this.getQueue().getRoutingKey()).noargs();
		amqpAdmin.declareQueue(queue);
		amqpAdmin.declareExchange(exchange);
		amqpAdmin.declareBinding(binding);

		container.setDefaultRequeueRejected(false);
		container.setPrefetchCount(config.getPrefetchCount());
		container.setConcurrentConsumers(config.getConcurrentConsumers());
		//手动提交ack
		container.setAcknowledgeMode(AcknowledgeMode.MANUAL);
		container.setMessageListener(new OnMessageListener<>(this));
		container.start();
		container.addQueues(queue);
		log.info("消息队列：消费者 {} 已启动", queue.getName());
	}

	private void startOrderlyConsumers() {
	}


	/**
	 * 将收到的json格式的消息体转成对应类型的对象
	 *
	 * @param body json格式的body
	 * @return type
	 */
	public T convertBody(String body) {
		return gson.fromJson(body, genericType);
	}

	/**
	 * 是否为顺序消费的消费者
	 *
	 * @return true=顺序消费
	 */
	private boolean isOrderly() {
		return this instanceof BaseOrderlyConsumer;
	}
}
