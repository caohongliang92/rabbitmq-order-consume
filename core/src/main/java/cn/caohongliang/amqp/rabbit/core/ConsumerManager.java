package cn.caohongliang.amqp.rabbit.core;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.InitializingBean;

import java.util.ArrayList;
import java.util.List;

/**
 * 消费者管理器
 */
@Slf4j
public class ConsumerManager implements InitializingBean {
	/**
	 * 顺序消费者
	 */
	private List<BaseOrderlyConsumer<?>> orderlyConsumers;
	/**
	 * 其他消费者
	 */
	private List<BaseConsumer<?>> otherConsumers;

	public ConsumerManager(List<BaseConsumer<?>> consumers) {
		orderlyConsumers = new ArrayList<>();
		otherConsumers = new ArrayList<>();
		consumers.forEach(consumer -> {
			if (consumer instanceof BaseOrderlyConsumer) {
				orderlyConsumers.add((BaseOrderlyConsumer<?>) consumer);
			} else {
				otherConsumers.add(consumer);
			}
		});
	}

	@Override
	public void afterPropertiesSet() throws Exception {
		otherConsumers.forEach(BaseConsumer::start);
		orderlyConsumers.forEach(BaseConsumer::start);
	}
}
