package cn.caohongliang.amqp.rabbit.core;

/**
 * 有序消费队列的消费者
 *
 * @author caohongliang
 */
public abstract class BaseOrderlyConsumer<T> extends BaseConsumer<T> {

	@Override
	public int maxThread() {
		return 1;
	}

	@Override
	public int getPrefetchCount() {
		return 10;
	}
}
