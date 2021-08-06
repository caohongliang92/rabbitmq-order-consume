package cn.caohongliang.amqp.rabbit.core;

import cn.caohongliang.amqp.rabbit.core.consumer.BaseConsumer;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.InitializingBean;

import java.util.List;

/**
 * 消费者管理器
 */
@Slf4j
public class ConsumerManager implements InitializingBean {
	/**
	 * 消费者
	 */
	private List<BaseConsumer<?>> consumers;

	public ConsumerManager(List<BaseConsumer<?>> consumers) {
		this.consumers = consumers;
	}

	@Override
	public void afterPropertiesSet() throws Exception {
		consumers.forEach(BaseConsumer::start);
	}
}
