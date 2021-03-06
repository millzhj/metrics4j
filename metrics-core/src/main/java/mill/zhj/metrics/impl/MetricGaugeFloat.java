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

import mill.zhj.metrics.Metric;
import mill.zhj.metrics.MetricType;
import mill.zhj.metrics.MetricsInfo;

class MetricGaugeFloat extends Metric {

	final float value;

	public MetricGaugeFloat(MetricsInfo info, float value) {
		super(info);
		this.value = value;
	}

	@Override
	public Float value() {
		return value;
	}

	@Override
	public MetricType type() {
		return MetricType.GAUGE;
	}

}
