package cn.caohongliang.amqp.rabbit.core.listener;

import cn.caohongliang.amqp.rabbit.core.consumer.BaseConsumer;
import cn.caohongliang.amqp.rabbit.core.util.RabbitUtils;
import com.rabbitmq.client.Channel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.listener.api.ChannelAwareMessageListener;

import java.nio.charset.StandardCharsets;

@Slf4j
public class OnMessageListener<T> implements ChannelAwareMessageListener {
	private final BaseConsumer<T> consumer;

	public OnMessageListener(BaseConsumer<T> consumer) {
		this.consumer = consumer;
	}

	@Override
	public void onMessage(Message message, Channel channel) throws Exception {
		try {
			RabbitUtils.setContext(RabbitUtils.Context.builder()
					.message(message)
					.channel(channel)
					.build());
			//转成对应类型的消息
			T msg = this.consumer.convertBody(new String(message.getBody(), StandardCharsets.UTF_8));
			//执行消费
			boolean result = this.consumer.onMessage(msg);
				long deliveryTag = message.getMessageProperties().getDeliveryTag();
			if (result) {
				//multiple=false，只对当前消息进行确认，multiple=true对当前及之前收到的所有消息进行确认
				channel.basicAck(deliveryTag, false);
			} else {
				//TODO caohongliang 2021/8/6 需要判断执行失败的次数
				//拒绝并放回队列
				channel.basicReject(deliveryTag, true);
			}
		} finally {
			RabbitUtils.destroy();
		}
	}
}
