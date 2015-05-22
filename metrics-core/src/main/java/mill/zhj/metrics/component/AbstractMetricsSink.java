package mill.zhj.metrics.component;

import java.util.Map;

import mill.zhj.metrics.MetricsSink;

public abstract class AbstractMetricsSink implements MetricsSink {

	protected String name;

	public AbstractMetricsSink(String name, Map<String, String> conf) {
		this.name = name;
	}

	@Override
	public String getName() {
		return name;
	}

}
