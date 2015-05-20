/*
 * MetricsRecordImpl.java
 *
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

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import mill.zhj.metrics.Metric;
import mill.zhj.metrics.MetricsRecord;

/**
 * An implementation of MetricsRecord. Keeps a back-pointer to the context from which it was created, and delegates back
 * to it on <code>update</code> and <code>remove()</code>.
 */

public class MetricsRecordImpl implements MetricsRecord {

	private long timestamp;
	private String context;
	private List<Metric> metrics;

	public MetricsRecordImpl(long timestamp, String context, List<Metric> metrics) {
		this.timestamp = timestamp;
		this.context = context;
		this.metrics = metrics;
	}

	@Override
	public long timestamp() {
		return timestamp;
	}

	@Override
	public String context() {
		return context;
	}

	@Override
	public Iterable<Metric> metrics() {
		return metrics;
	}

}
