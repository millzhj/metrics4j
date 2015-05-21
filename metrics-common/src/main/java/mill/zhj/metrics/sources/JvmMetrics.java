/*
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package mill.zhj.metrics.sources;

import java.lang.management.ManagementFactory;
import java.lang.management.MemoryMXBean;
import java.lang.management.MemoryUsage;
import java.lang.management.ThreadInfo;
import java.lang.management.ThreadMXBean;
import java.lang.management.GarbageCollectorMXBean;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

import mill.zhj.metrics.MetricsInfo;
import mill.zhj.metrics.MetricsRecord;
import mill.zhj.metrics.MetricsRecordBuilder;
import mill.zhj.metrics.MetricsSource;
import mill.zhj.metrics.impl.MetricsRecordBuilderImpl;
import static mill.zhj.metrics.sources.JvmMetricsInfo.*;

/**
 * JVM and logging related metrics. Mostly used by various servers as a part of the metrics they export.
 */

public class JvmMetrics implements MetricsSource {

	static final float M = 1024 * 1024;
	final MemoryMXBean memoryMXBean = ManagementFactory.getMemoryMXBean();
	final List<GarbageCollectorMXBean> gcBeans = ManagementFactory.getGarbageCollectorMXBeans();
	final ThreadMXBean threadMXBean = ManagementFactory.getThreadMXBean();
	final ConcurrentHashMap<String, MetricsInfo[]> gcInfoCache = new ConcurrentHashMap<String, MetricsInfo[]>();

	final static String DEFAULT_CONTEXT_NAME = "jvm";
	private String contextName = DEFAULT_CONTEXT_NAME;

	public JvmMetrics() {

	}

	public JvmMetrics(String contextName) {
		this.contextName = contextName;
	}

	private MetricsRecordBuilder getMetrics() {
		MetricsRecordBuilder rb = new MetricsRecordBuilderImpl();
		rb.setContext(contextName);
		getMemoryUsage(rb);
		// getGcUsage(rb);
		getThreadUsage(rb);
		return rb;
	}

	@Override
	public MetricsRecord getMetricsRecord() {
		MetricsRecordBuilder rb = getMetrics();
		return rb.getRecord();
	}

	private void getMemoryUsage(MetricsRecordBuilder rb) {
		MemoryUsage memNonHeap = memoryMXBean.getNonHeapMemoryUsage();
		MemoryUsage memHeap = memoryMXBean.getHeapMemoryUsage();
		Runtime runtime = Runtime.getRuntime();
		rb.addGauge(MemNonHeapUsedM, memNonHeap.getUsed() / M)
				.addGauge(MemNonHeapCommittedM, memNonHeap.getCommitted() / M)
				.addGauge(MemNonHeapMaxM, memNonHeap.getMax() / M)
				.addGauge(MemHeapUsedM, memHeap.getUsed() / M)
				.addGauge(MemHeapCommittedM, memHeap.getCommitted() / M)
				.addGauge(MemHeapMaxM, memHeap.getMax() / M)
				.addGauge(MemMaxM, runtime.maxMemory() / M);
	}

	// private void getGcUsage(MetricsRecordBuilder rb) {
	// long count = 0;
	// long timeMillis = 0;
	// for (GarbageCollectorMXBean gcBean : gcBeans) {
	// long c = gcBean.getCollectionCount();
	// long t = gcBean.getCollectionTime();
	// MetricsInfo[] gcInfo = getGcInfo(gcBean.getName());
	// rb.addCounter(gcInfo[0], c).addCounter(gcInfo[1], t);
	// count += c;
	// timeMillis += t;
	// }
	// rb.addCounter(GcCount, count).addCounter(GcTimeMillis, timeMillis);
	//
	// }

	// private MetricsInfo[] getGcInfo(String gcName) {
	// MetricsInfo[] gcInfo = gcInfoCache.get(gcName);
	// if (gcInfo == null) {
	// gcInfo = new MetricsInfo[2];
	// gcInfo[0] = Interns.info("GcCount" + gcName, "GC Count for " + gcName);
	// gcInfo[1] = Interns.info("GcTimeMillis" + gcName, "GC Time for " + gcName);
	// MetricsInfo[] previousGcInfo = gcInfoCache.putIfAbsent(gcName, gcInfo);
	// if (previousGcInfo != null) {
	// return previousGcInfo;
	// }
	// }
	// return gcInfo;
	// }

	private void getThreadUsage(MetricsRecordBuilder rb) {
		int threadsNew = 0;
		int threadsRunnable = 0;
		int threadsBlocked = 0;
		int threadsWaiting = 0;
		int threadsTimedWaiting = 0;
		int threadsTerminated = 0;
		long threadIds[] = threadMXBean.getAllThreadIds();
		for (ThreadInfo threadInfo : threadMXBean.getThreadInfo(threadIds, 0)) {
			if (threadInfo == null)
				continue; // race protection
			switch (threadInfo.getThreadState()) {
				case NEW:
					threadsNew++;
					break;
				case RUNNABLE:
					threadsRunnable++;
					break;
				case BLOCKED:
					threadsBlocked++;
					break;
				case WAITING:
					threadsWaiting++;
					break;
				case TIMED_WAITING:
					threadsTimedWaiting++;
					break;
				case TERMINATED:
					threadsTerminated++;
					break;
			}
		}
		rb.addGauge(ThreadsNew, threadsNew)
				.addGauge(ThreadsRunnable, threadsRunnable)
				.addGauge(ThreadsBlocked, threadsBlocked)
				.addGauge(ThreadsWaiting, threadsWaiting)
				.addGauge(ThreadsTimedWaiting, threadsTimedWaiting)
				.addGauge(ThreadsTerminated, threadsTerminated);
	}

	@Override
	public void stop() {
		// TODO Auto-generated method stub

	}
}
