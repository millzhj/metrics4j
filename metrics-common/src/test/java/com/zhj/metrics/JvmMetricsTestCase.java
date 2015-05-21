package com.zhj.metrics;

import java.util.HashMap;
import java.util.Map;

import mill.zhj.metrics.MetricsContext;
import mill.zhj.metrics.impl.DefaultMetricsContext;
import mill.zhj.metrics.sink.FileSink;
import mill.zhj.metrics.sources.JvmMetrics;

public class JvmMetricsTestCase {

	public static void main(String[] args) throws Exception {

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
		Thread.sleep(20000);
		context.stop();
	}
}
