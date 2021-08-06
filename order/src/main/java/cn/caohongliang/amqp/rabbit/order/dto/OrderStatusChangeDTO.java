package cn.caohongliang.amqp.rabbit.order.dto;

import lombok.Data;

import java.io.Serializable;

/**
 * 订单状态变化
 *
 * @author caohongliang
 */
@Data
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
