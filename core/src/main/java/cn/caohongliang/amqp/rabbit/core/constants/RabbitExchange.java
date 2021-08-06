package cn.caohongliang.amqp.rabbit.core.constants;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.amqp.core.AbstractExchange;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Exchange;
import org.springframework.amqp.core.FanoutExchange;

/**
 * 定义RabbitMQ的交换机
 *
 * @author caohongliang
 */
@Getter
@AllArgsConstructor
public enum RabbitExchange {
	/**
	 * 直接转发到匹配routing_key的队列
	 */
	direct("direct"),
	/**
	 * 直接转发到匹配routing_key的队列（延迟队列）
	 */
	direct_delay("direct_delay"),
	/**
	 * 发布订阅模式
	 */
	fanout("fanout"),
	;
	/**
	 * 交换机名称
	 */
	private String exchangeName;

	public Exchange getExchange() {
		switch (this) {
			case direct:
			case direct_delay:
				return new DirectExchange(this.getExchangeName(), true, false);
			case fanout:
				return new FanoutExchange(this.getExchangeName(), true, false);
			default:
				throw new RuntimeException("异常的交换机");
		}
	}
}
