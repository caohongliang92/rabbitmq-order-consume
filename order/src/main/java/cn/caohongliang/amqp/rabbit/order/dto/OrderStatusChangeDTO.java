package cn.caohongliang.amqp.rabbit.order.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * 订单状态变化
 *
 * @author caohongliang
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class OrderStatusChangeDTO implements Serializable {
	/**
	 * 订单ID
	 */
	private long orderId;
	/**
	 * 订单状态
	 */
	private int status;
}
