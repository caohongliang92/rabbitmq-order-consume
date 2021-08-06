package cn.caohongliang.amqp.rabbit.core.config;

import lombok.*;

import java.io.Serializable;

/**
 * 一般消费的配置
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class GeneralConsumerConfig implements Serializable {
	private static final long serialVersionUID = 8025631721041407816L;
	/**
	 * 是否启用消费者
	 */
	private Boolean enable = true;
	/**
	 * 每个节点并发消费者的数量
	 */
	private Integer concurrentConsumers = 5;
	/**
	 * 每个并发消费者预拉取多少条数据
	 */
	private Integer prefetchCount = 2;

	public static GeneralConsumerConfig getDefaultConfig() {
		return new GeneralConsumerConfig();
	}
}