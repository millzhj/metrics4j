package com.zhj.metrics;

import org.junit.Test;

import mill.zhj.metrics.component.DefaultMetricsSystem;

public class DefaultMetricsSystemTestCase {
	
	@Test
	public void run() {
		final DefaultMetricsSystem metricsSystem = DefaultMetricsSystem.getInstance();

		Thread startThread = new Thread(new Runnable() {

			@Override
			public void run() {
				metricsSystem.start();
			}
		});

		startThread.start();

		try {
			Thread.sleep(20000);
		} catch (InterruptedException e) {
			throw new IllegalStateException(e);
		}
		metricsSystem.shutdown();
	}
}
