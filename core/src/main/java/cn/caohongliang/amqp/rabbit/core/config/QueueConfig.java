package cn.caohongliang.amqp.rabbit.core.config;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

/**
 * 队列配置
 */
@Getter
@Setter
public class QueueConfig implements Serializable {
	private static final long serialVersionUID = -4976714852585229887L;

	/**
	 * 队列名称
	 */
	private String queue;
	/**
	 * 一般消费者配置
	 */
	private GeneralConsumerConfig generalConfig;
	/**
	 * 顺序消费者配置
	 */
	private OrderlyConsumerConfig orderlyConfig;
}
