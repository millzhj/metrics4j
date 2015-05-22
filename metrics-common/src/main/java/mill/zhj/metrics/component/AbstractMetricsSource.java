package mill.zhj.metrics.component;

import java.util.Map;

import mill.zhj.metrics.MetricsSource;

public abstract class AbstractMetricsSource implements MetricsSource {

	protected String application;

	protected String context;

	public AbstractMetricsSource(String application, String context, Map<String, String> config) {
		this.application = application;
		this.context = context;
	}
}
