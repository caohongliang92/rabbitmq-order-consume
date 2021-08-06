package cn.caohongliang.amqp.rabbit.core;

import org.springframework.amqp.core.AmqpAdmin;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class RabbitCoreAutoConfiguration {
	@Bean
	public ConsumerManager consumerManager(List<BaseConsumer<?>> consumers) {
		return new ConsumerManager(consumers);
	}
}
