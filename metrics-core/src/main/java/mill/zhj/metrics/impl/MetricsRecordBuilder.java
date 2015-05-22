/**
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

package mill.zhj.metrics.impl;

import java.util.Collections;
import java.util.List;

import mill.zhj.metrics.Metric;
import mill.zhj.metrics.MetricsInfo;
import mill.zhj.metrics.MetricsRecord;

import com.google.common.collect.Lists;

public class MetricsRecordBuilder {

	private final long timestamp;
	private final List<Metric> metrics;
	private String context;
	private String application;

	public MetricsRecordBuilder() {
		timestamp = System.currentTimeMillis();
		metrics = Lists.newArrayList();

	}

	public MetricsRecordBuilder addCounter(MetricsInfo info, int value) {
		metrics.add(new MetricCounterInt(info, value));
		return this;
	}

	public MetricsRecordBuilder addCounter(MetricsInfo info, long value) {
		metrics.add(new MetricCounterLong(info, value));
		return this;
	}

	public MetricsRecordBuilder addGauge(MetricsInfo info, int value) {
		metrics.add(new MetricGaugeInt(info, value));
		return this;
	}

	public MetricsRecordBuilder addGauge(MetricsInfo info, long value) {
		metrics.add(new MetricGaugeLong(info, value));
		return this;
	}

	public MetricsRecordBuilder addGauge(MetricsInfo info, float value) {
		metrics.add(new MetricGaugeFloat(info, value));
		return this;
	}

	public MetricsRecordBuilder addGauge(MetricsInfo info, double value) {
		metrics.add(new MetricGaugeDouble(info, value));
		return this;
	}

	public MetricsRecordBuilder setContext(String value) {
		this.context = value;
		return this;
	}

	public MetricsRecordBuilder setApplication(String value) {
		this.application = value;
		return this;
	}

	public MetricsRecord getRecord() {
		return new MetricsRecordImpl(timestamp, application, context, metrics());
	}

	private List<Metric> metrics() {
		return Collections.unmodifiableList(metrics);
	}
}
