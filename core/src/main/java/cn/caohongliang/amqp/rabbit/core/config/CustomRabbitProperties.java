package cn.caohongliang.amqp.rabbit.core.config;

import cn.caohongliang.amqp.rabbit.core.constants.Constants;
import cn.caohongliang.amqp.rabbit.core.util.GsonUtils;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@Getter
@Setter
@ConfigurationProperties(prefix = Constants.PROPERTIES_PREFIX)
public class CustomRabbitProperties {
	/**
	 * 是否启用
	 */
	private Boolean enable;
	/**
	 * 队列的相关配置
	 */
	private List<QueueConfig> queueConfigs;

	/**
	 * 获取队列配置
	 * @param queueName
	 * @return
	 */
	public Optional<QueueConfig> getQueueConfig(String queueName) {
		return Optional.ofNullable(this.queueConfigs)
				.orElse(Collections.emptyList()).stream()
				.filter(k -> k.getQueue() != null && k.getQueue().trim().equals(queueName))
				.findFirst();
	}

	public GeneralConsumerConfig getGeneralConsumerConfig(String queueName){
		return getQueueConfig(queueName)
				.map(QueueConfig::getGeneralConfig)
				.orElse(GeneralConsumerConfig.getDefaultConfig());
	}

	public OrderlyConsumerConfig getOrderlyConsumerConfig(String queueName){
		return getQueueConfig(queueName)
				.map(QueueConfig::getOrderlyConfig)
				.orElse(OrderlyConsumerConfig.getDefaultConfig());
	}
}
