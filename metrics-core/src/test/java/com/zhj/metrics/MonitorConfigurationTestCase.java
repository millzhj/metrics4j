package com.zhj.metrics;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import junit.framework.Assert;
import mill.zhj.metrics.MetricsSink;
import mill.zhj.metrics.MetricsSource;
import mill.zhj.metrics.component.MetricsContext;
import mill.zhj.metrics.component.MonitorConfiguration;

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
		Assert.assertEquals(6, context.getPeriod());
		MetricsSource source = context.getMetricsSource();
		Assert.assertEquals("mill.zhj.metrics.sources.JvmMetrics", source.getClass().getName());

		Collection<MetricsSink> sinks = context.getMetricsSinks();
		Assert.assertEquals(1, sinks.size());
		Iterator<MetricsSink> iterator = sinks.iterator();
		boolean hasNext = iterator.hasNext();
		Assert.assertEquals(true, hasNext);
		MetricsSink sink = iterator.next();
		Assert.assertEquals("fileSink", sink.getName());
		Assert.assertEquals("mill.zhj.metrics.sink.FileSink", sink.getClass().getName());
	}
}
