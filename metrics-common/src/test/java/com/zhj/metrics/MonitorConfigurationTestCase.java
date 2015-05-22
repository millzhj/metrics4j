package com.zhj.metrics;

import java.util.List;

import junit.framework.Assert;
import mill.zhj.metrics.MetricsContext;
import mill.zhj.metrics.impl.MonitorConfiguration;

import org.junit.Test;

public class MonitorConfigurationTestCase {

	@Test
	public void parse() {
		MonitorConfiguration configuration = MonitorConfiguration.create("src/test/resource/monitor-test.xml");
		String application = configuration.getApplication();
		List<MetricsContext> contexts = configuration.getMetricsContexts();
		Assert.assertEquals("testApp", application);
		Assert.assertEquals(1, contexts.size());
		MetricsContext context = contexts.get(0);
		Assert.assertEquals("jvm", context.getContextName());

	}
}
