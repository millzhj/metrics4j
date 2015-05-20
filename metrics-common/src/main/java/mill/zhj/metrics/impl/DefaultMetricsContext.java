package mill.zhj.metrics.impl;

import mill.zhj.metrics.MetricsContext;
import mill.zhj.metrics.MetricsRecord;
import mill.zhj.metrics.MetricsSink;
import mill.zhj.metrics.MetricsSource;

public class DefaultMetricsContext implements MetricsContext {

	private String contextName;

	private int period = 5;// 5 seconds

	public DefaultMetricsContext(String contextName) {
		this.contextName = contextName;
	}

	@Override
	public String getContextName() {
		return contextName;
	}

	@Override
	public void close() {
		// TODO Auto-generated method stub

	}

	@Override
	public int getPeriod() {
		return period;
	}

	@Override
	public MetricsRecord getMetricsRecord() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void start() {
		// TODO Auto-generated method stub

	}

	@Override
	public void stop() {
		// TODO Auto-generated method stub

	}

	@Override
	public void registerSink(MetricsSink sink) {
		// TODO Auto-generated method stub

	}

	@Override
	public void unregisterSink(MetricsSink sink) {
		// TODO Auto-generated method stub

	}

	@Override
	public void registerSource(MetricsSource source) {
		// TODO Auto-generated method stub

	}

	@Override
	public void unregisterSource(MetricsSource source) {
		// TODO Auto-generated method stub

	}

}
