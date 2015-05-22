package com.zhj.metrics;

import java.util.HashMap;
import java.util.Map;

import mill.zhj.metrics.component.MetricsContext;
import mill.zhj.metrics.sink.FileSink;
import mill.zhj.metrics.sources.JvmMetrics;

import org.junit.Test;

public class JvmMetricsTestCase {

	@Test
	public void run() {

		JvmMetrics metricsSource = new JvmMetrics("testApp", "jvm", new HashMap<String, String>());
		Map<String, String> conf = new HashMap<String, String>();
		// conf.put("filename", "test.txt");
		FileSink metricsSink = new FileSink("fileSink", conf);

		final MetricsContext context = new MetricsContext("testApp", "jvm");
		context.setMetricsSource(metricsSource);
		context.registerMetricsSink(metricsSink);
		Thread startThread = new Thread(new Runnable() {

			@Override
			public void run() {
				context.start();
			}
		});

		startThread.start();
		try {
			Thread.sleep(20000);
		} catch (InterruptedException e) {
			throw new IllegalStateException(e);
		}
		context.stop();
	}
}
