package cn.caohongliang.amqp.rabbit.core.constants;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * RabbitMQ的队列
 * 完整的队列名由 consumerSystem.name() + "_" + suffixQueueName 组成
 *
 * @author caohongliang
 */
@Getter
@AllArgsConstructor
public enum RabbitQueue {
	/**
	 * 订单状态变化
	 */
	order_status_change(RabbitExchange.direct, ConsumerSystem.order, "status_change"),
	;
	/**
	 * 交换机
	 */
	private RabbitExchange exchange;
	/**
	 * 队列对应消费者所属的系统
	 */
	private ConsumerSystem consumerSystem;
	/**
	 * 队列名后缀，完整的队列名由 consumerSystem.name() + "_" + suffixQueueName 组成
	 */
	private String suffixQueueName;

	/**
	 * 获取队列名称
	 * @return 队列名
	 */
	public String getQueueName() {
		return this.consumerSystem.name() + "_" + this.suffixQueueName;
	}

	/**
	 * 获取队列名称（顺序消费）
	 * @param index 队列角标
	 * @return 队列名
	 */
	public String getQueueName(int index) {
		return getQueueName() + "-" + index;
	}

	/**
	 * 获取RoutingKey
	 * @return routing_key
	 */
	public String getRoutingKey() {
		return this.getQueueName();
	}

	/**
	 * 获取RoutingKey（顺序消费）
	 * @param index 队列角标
	 * @return routing_key
	 */
	public String getRoutingKey(int index) {
		return this.getQueueName();
	}
}
