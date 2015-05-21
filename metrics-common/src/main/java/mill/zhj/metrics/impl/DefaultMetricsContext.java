package mill.zhj.metrics.impl;

import java.util.Map;
import java.util.Set;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;

import com.google.common.collect.Maps;

import mill.zhj.metrics.MetricsContext;
import mill.zhj.metrics.MetricsRecord;
import mill.zhj.metrics.MetricsSink;
import mill.zhj.metrics.MetricsSource;

public class DefaultMetricsContext implements MetricsContext {

	private String contextName;

	private String application;

	private Map<String, MetricsSink> metricsSinks = Maps.newConcurrentMap();

	private MetricsSource metricsSource;

	private int period = DEFAULT_PERIOD;

	private long delay = 5;

	private ScheduledExecutorService scheduledExecutorService;

	private AtomicBoolean started = new AtomicBoolean(false);

	public DefaultMetricsContext(String application, String contextName) {
		this.application = application;
		this.contextName = contextName;
		scheduledExecutorService = Executors.newScheduledThreadPool(1);
	}

	@Override
	public String getContextName() {
		return contextName;
	}

	@Override
	public int getPeriod() {
		return period;
	}

	@Override
	public void start() {
		if (!started.get()) {
			scheduledExecutorService.scheduleAtFixedRate(new Runnable() {

				@Override
				public void run() {
					MetricsRecord record = metricsSource.getMetricsRecord();
					Set<Map.Entry<String, MetricsSink>> entrySet = metricsSinks.entrySet();
					for (Map.Entry<String, MetricsSink> entry : entrySet) {
						MetricsSink metricsSink = entry.getValue();
						metricsSink.putMetrics(record);
						metricsSink.flush();
					}
				}
			}, delay, period, TimeUnit.SECONDS);
		}
		started.compareAndSet(false, true);
	}

	@Override
	public void stop() {
		try {
			scheduledExecutorService.shutdown();
		} catch (Exception e) {
		}
		try {
			metricsSource.stop();
		} catch (Exception e) {
		}

		try {
			Set<Map.Entry<String, MetricsSink>> entrySet = metricsSinks.entrySet();
			for (Map.Entry<String, MetricsSink> entry : entrySet) {
				MetricsSink metricsSink = entry.getValue();
				try {
					metricsSink.stop();
				} catch (Exception e) {
				}

			}
		} catch (Exception e) {
		}

	}

	@Override
	public void registerSink(MetricsSink sink) {
		metricsSinks.put(sink.getName(), sink);
	}

	@Override
	public void unregisterSink(MetricsSink sink) {
		unregisterSink(sink.getName());
	}

	@Override
	public void unregisterSink(String sinkName) {
		metricsSinks.remove(sinkName);
	}

	@Override
	public void registerSource(MetricsSource source) {
		this.metricsSource = source;
	}

	@Override
	public String getApplication() {
		return application;
	}

}
