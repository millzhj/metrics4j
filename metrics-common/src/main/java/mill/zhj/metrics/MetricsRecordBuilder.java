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

package mill.zhj.metrics;

/**
 * The metrics record builder interface
 */

public interface MetricsRecordBuilder {

	/**
	 * Set the context tag
	 * 
	 * @param value
	 *            of the context
	 * @return self
	 */
	MetricsRecordBuilder setContext(String value);

	/**
	 * Add an integer metric
	 * 
	 * @param info
	 *            metadata of the metric
	 * @param value
	 *            of the metric
	 * @return self
	 */
	MetricsRecordBuilder addCounter(MetricsInfo info, int value);

	/**
	 * Add an long metric
	 * 
	 * @param info
	 *            metadata of the metric
	 * @param value
	 *            of the metric
	 * @return self
	 */
	MetricsRecordBuilder addCounter(MetricsInfo info, long value);

	/**
	 * Add a integer gauge metric
	 * 
	 * @param info
	 *            metadata of the metric
	 * @param value
	 *            of the metric
	 * @return self
	 */
	MetricsRecordBuilder addGauge(MetricsInfo info, int value);

	/**
	 * Add a long gauge metric
	 * 
	 * @param info
	 *            metadata of the metric
	 * @param value
	 *            of the metric
	 * @return self
	 */
	MetricsRecordBuilder addGauge(MetricsInfo info, long value);

	/**
	 * Add a float gauge metric
	 * 
	 * @param info
	 *            metadata of the metric
	 * @param value
	 *            of the metric
	 * @return self
	 */
	MetricsRecordBuilder addGauge(MetricsInfo info, float value);

	/**
	 * Add a double gauge metric
	 * 
	 * @param info
	 *            metadata of the metric
	 * @param value
	 *            of the metric
	 * @return self
	 */
	MetricsRecordBuilder addGauge(MetricsInfo info, double value);

	MetricsRecord getRecord();

}
