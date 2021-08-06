package cn.caohongliang.amqp.rabbit.order.controller;

import cn.caohongliang.amqp.rabbit.order.dto.OrderStatusChangeDTO;
import cn.caohongliang.amqp.rabbit.order.mq.OrderStatusChangeProducer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.concurrent.atomic.AtomicInteger;

@RestController
@RequestMapping("/order")
public class OrderController {
	@Resource
	private OrderStatusChangeProducer orderStatusChangeProducer;
	private AtomicInteger ai = new AtomicInteger();

	@RequestMapping("/producer1")
	public Object producer1(){
		OrderStatusChangeDTO body = OrderStatusChangeDTO.builder()
				.orderId(ai.addAndGet(1))
				.status(1)
				.build();
		orderStatusChangeProducer.send(body, 10);
		return body;
	}
}
