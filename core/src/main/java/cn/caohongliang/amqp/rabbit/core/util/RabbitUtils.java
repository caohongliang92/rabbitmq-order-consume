package cn.caohongliang.amqp.rabbit.core.util;

import com.rabbitmq.client.Channel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import org.springframework.amqp.core.Message;

/**
 * RabbitMQ相关的工具类
 */
public class RabbitUtils {
	private static final ThreadLocal<Context> CONTEXT_THREAD_LOCAL = new ThreadLocal<>();

	/**
	 * 获取消息ID，只允许在BaseConsumer#onMessage方法中调用
	 *
	 * @return
	 */
	public static String getMessageId() {
		Context context = currentContext();
		if (context == null) {
			return "";
		}
		return context.getMessage().getMessageProperties().getMessageId();
	}

	/**
	 * 获取当前Context，只允许在BaseConsumer#onMessage方法中调用
	 *
	 * @return context
	 */
	public static Context currentContext() {
		return CONTEXT_THREAD_LOCAL.get();
	}

	/**
	 * 设置Context
	 *
	 * @param context context
	 */
	public static void setContext(Context context) {
		CONTEXT_THREAD_LOCAL.set(context);
	}

	/**
	 * 销毁上下文
	 */
	public static void destroy() {
		CONTEXT_THREAD_LOCAL.remove();
	}

	@Getter
	@Builder
	public static class Context {
		private Message message;
		private Channel channel;
	}
}
