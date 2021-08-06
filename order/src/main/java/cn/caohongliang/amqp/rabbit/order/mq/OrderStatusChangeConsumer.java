package cn.caohongliang.amqp.rabbit.order.mq;

import cn.caohongliang.amqp.rabbit.core.BaseOrderlyConsumer;
import cn.caohongliang.amqp.rabbit.core.RabbitQueue;
import cn.caohongliang.amqp.rabbit.order.dto.OrderStatusChangeDTO;
import org.springframework.stereotype.Component;

@Component
public class OrderStatusChangeConsumer extends BaseOrderlyConsumer<OrderStatusChangeDTO> {

	@Override
	public RabbitQueue getQueue() {
		return RabbitQueue.order_status_change;
	}

	@Override
	public boolean onMessage(OrderStatusChangeDTO msg) {
		return false;
	}
}
