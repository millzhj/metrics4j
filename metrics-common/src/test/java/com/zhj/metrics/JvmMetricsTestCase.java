package com.zhj.metrics;

import java.util.HashMap;
import java.util.Map;

import org.junit.Test;

import mill.zhj.metrics.MetricsContext;
import mill.zhj.metrics.impl.DefaultMetricsContext;
import mill.zhj.metrics.sink.FileSink;
import mill.zhj.metrics.sources.JvmMetrics;

public class JvmMetricsTestCase {

	@Test
	public void run() {

		JvmMetrics metricsSource = new JvmMetrics("testApp", "jvm", new HashMap<String, String>());
		Map<String, String> conf = new HashMap<String, String>();
		// conf.put("filename", "test.txt");
		FileSink metricsSink = new FileSink("fileSink", conf);

		final MetricsContext context = new DefaultMetricsContext("testApp", "jvm");
		context.registerSource(metricsSource);
		context.registerSink(metricsSink);
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
