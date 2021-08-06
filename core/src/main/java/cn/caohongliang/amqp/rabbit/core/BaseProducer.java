package cn.caohongliang.amqp.rabbit.core;

/**
 * 生产者
 *
 * @author caohongliang
 */
public abstract class BaseProducer {

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
	 */
	public abstract void send(Object msg);
}
