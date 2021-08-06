package cn.caohongliang.amqp.rabbit.core;

import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * 消费者
 *
 * @param <T>
 * @author caohongliang
 */
@Slf4j
public abstract class BaseConsumer<T> implements InitializingBean {
	@Autowired
	private AmqpAdmin amqpAdmin;
	@Autowired
	private ConnectionFactory connectionFactory;
	private SimpleMessageListenerContainer container;

	public BaseConsumer() {

	}

	/**
	 * 返回该节点上最大允许的线程数量（消费者数量）
	 *
	 * @return 必须大于0
	 */
	protected int maxThread() {
		return 5;
	}

	/**
	 * 返回本地缓冲区消息数量
	 *
	 * @return 大于或等于0
	 */
	protected int getPrefetchCount() {
		return 20;
	}

	/**
	 * 是否允许消费者启动
	 *
	 * @return 默认允许
	 */
	protected boolean isEnable() {
		return true;
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
	 * @return 是否消费成功
	 */
	public abstract boolean onMessage(T msg);

	@Override
	public void afterPropertiesSet() throws Exception {
		container = new SimpleMessageListenerContainer(connectionFactory);
		container.setConnectionFactory(connectionFactory);
		container.setAmqpAdmin(amqpAdmin);
		container.setDefaultRequeueRejected(false);
		container.setAcknowledgeMode(AcknowledgeMode.MANUAL);
	}

	public void start() {
		if (!this.isEnable()) {
			log.info("消息队列：{} 没有启用", this.getQueue().getQueueName());
			return;
		}
		Queue queue = new Queue(this.getQueue().getQueueName(), true);
		Exchange exchange = this.getQueue().getExchange().getExchange();
		Binding binding = BindingBuilder.bind(queue).to(exchange).with(queue.getName()).noargs();
		amqpAdmin.declareQueue(queue);
		amqpAdmin.declareExchange(exchange);
		amqpAdmin.declareBinding(binding);
		container.setPrefetchCount(getPrefetchCount());
		container.start();
		container.addQueues(queue);
		log.info("消息队列：{} 已启动", queue.getName());
	}
}
