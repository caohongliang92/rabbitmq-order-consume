package cn.caohongliang.amqp.rabbit.core.config;

import lombok.*;

import java.io.Serializable;

/**
 * 顺序消费者的配置
 *
 * @author caohongliang
 */
@Getter
@Setter
@NoArgsConstructor
public class OrderlyConsumerConfig implements Serializable {
	private static final long serialVersionUID = 8025631721041407816L;
	/**
	 * 是否启用消费者
	 */
	private Boolean enable = true;
	/**
	 * 总消费者数量
	 */
	private Integer totalCount = 10;

	public static OrderlyConsumerConfig getDefaultConfig() {
		return new OrderlyConsumerConfig();
	}
}
