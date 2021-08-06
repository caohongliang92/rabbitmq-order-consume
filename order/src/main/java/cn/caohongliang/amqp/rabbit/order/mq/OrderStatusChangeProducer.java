package cn.caohongliang.amqp.rabbit.order.mq;

import cn.caohongliang.amqp.rabbit.core.producer.BaseOrderlyProducer;
import cn.caohongliang.amqp.rabbit.core.constants.RabbitQueue;
import cn.caohongliang.amqp.rabbit.order.dto.OrderStatusChangeDTO;
import org.springframework.stereotype.Component;

@Component
public class OrderStatusChangeProducer extends BaseOrderlyProducer<OrderStatusChangeDTO> {

	@Override
	public RabbitQueue getQueue() {
		return RabbitQueue.order_status_change;
	}
}
