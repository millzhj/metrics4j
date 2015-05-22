package mill.zhj.metrics.component;

import java.util.Collection;
import java.util.Collections;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;

import mill.zhj.metrics.MetricsRecord;
import mill.zhj.metrics.MetricsSink;
import mill.zhj.metrics.MetricsSource;

import com.google.common.collect.Maps;

public class MetricsContext {

	/**
	 * Default period in seconds at which data is sent to the metrics system.
	 */
	public static final int DEFAULT_PERIOD = 5;

	private String contextName;

	private String application;

	private Map<String, MetricsSink> metricsSinks = Maps.newConcurrentMap();

	private MetricsSource metricsSource;

	private int period = DEFAULT_PERIOD;

	private long delay = 5;

	private ScheduledExecutorService scheduledExecutorService;

	private AtomicBoolean started = new AtomicBoolean(false);

	public MetricsContext(String application, String contextName) {
		this.application = application;
		this.contextName = contextName;
		scheduledExecutorService = Executors.newScheduledThreadPool(1);
	}

	public String getContextName() {
		return contextName;
	}

	public int getPeriod() {
		return period;
	}

	public void start() {
		if (!started.get()) {
			scheduledExecutorService.scheduleAtFixedRate(new Runnable() {

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

	public void stop() {
		started.compareAndSet(true, false);
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

	public void registerMetricsSink(MetricsSink sink) {
		metricsSinks.put(sink.getName(), sink);
	}

	public void unregisterMetricsSink(String sinkName) {
		metricsSinks.remove(sinkName);
	}

	public void setMetricsSource(MetricsSource source) {
		this.metricsSource = source;
	}

	public String getApplication() {
		return application;
	}

	public MetricsSource getMetricsSource() {
		return metricsSource;

	}

	public Collection<MetricsSink> getMetricsSinks() {
		return Collections.unmodifiableCollection(metricsSinks.values());
	}

	public void setPeriod(int period) {
		this.period = period;
	}

}
