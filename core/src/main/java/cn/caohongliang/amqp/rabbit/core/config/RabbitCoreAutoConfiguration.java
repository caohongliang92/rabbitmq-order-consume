package cn.caohongliang.amqp.rabbit.core.config;

import cn.caohongliang.amqp.rabbit.core.ConsumerManager;
import cn.caohongliang.amqp.rabbit.core.constants.Constants;
import cn.caohongliang.amqp.rabbit.core.consumer.BaseConsumer;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@EnableConfigurationProperties({CustomRabbitProperties.class})
@ConditionalOnProperty(prefix = Constants.PROPERTIES_PREFIX, name = "enable", matchIfMissing = true)
@Configuration
@Slf4j
public class RabbitCoreAutoConfiguration {
	@Bean
	public ConsumerManager consumerManager(List<BaseConsumer<?>> consumers) {
		return new ConsumerManager(consumers);
	}
}
