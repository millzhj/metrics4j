package mill.zhj.metrics.impl;

import java.util.Collections;
import java.util.List;

import com.google.common.collect.Lists;

import mill.zhj.metrics.MetricsContext;

public class MonitorConfiguration {

	private String application;

	private List<MetricsContext> contexts = Lists.newArrayList();

	private MonitorConfiguration() {
	}

	public static MonitorConfiguration create(String file) {
		MonitorConfiguration configuration = null;
		return configuration;
	}

	public String getApplication() {
		return application;
	}

	public List<MetricsContext> getMetricsContexts() {

		return Collections.unmodifiableList(contexts);
	}
}
