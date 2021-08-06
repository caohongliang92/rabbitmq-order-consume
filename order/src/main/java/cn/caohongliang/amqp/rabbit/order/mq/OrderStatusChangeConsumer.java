package cn.caohongliang.amqp.rabbit.order.mq;

import cn.caohongliang.amqp.rabbit.core.consumer.BaseConsumer;
import cn.caohongliang.amqp.rabbit.core.consumer.BaseOrderlyConsumer;
import cn.caohongliang.amqp.rabbit.core.constants.RabbitQueue;
import cn.caohongliang.amqp.rabbit.core.util.RabbitUtils;
import cn.caohongliang.amqp.rabbit.order.dto.OrderStatusChangeDTO;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

@Slf4j
@Component
public class OrderStatusChangeConsumer extends BaseConsumer<OrderStatusChangeDTO> {

	@Override
	public RabbitQueue getQueue() {
		return RabbitQueue.order_status_change;
	}

	@Override
	public boolean onMessage(OrderStatusChangeDTO msg) {
		Gson gson = new GsonBuilder().disableHtmlEscaping().create();
		log.info("消费消息：messageId={}, json={}", RabbitUtils.getMessageId(), gson.toJson(msg));
		try {
			TimeUnit.SECONDS.sleep(1);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		return false;
	}
}
