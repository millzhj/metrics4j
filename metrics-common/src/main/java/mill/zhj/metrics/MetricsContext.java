/*
 * MetricsContext.java
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

package mill.zhj.metrics;

/**
 * The main interface to the metrics package.
 */

public interface MetricsContext {

	/**
	 * Default period in seconds at which data is sent to the metrics system.
	 */
	public static final int DEFAULT_PERIOD = 5;

	String getApplication();

	/**
	 * Returns the context name.
	 * 
	 * @return the context name
	 */
	String getContextName();

	/**
	 * Registers a callback to be called at regular time intervals, as determined by the implementation-class specific
	 * configuration.
	 * 
	 * @param updater
	 *            object to be run periodically; it should updated some metrics records and then return
	 */
	void registerSink(MetricsSink sink);

	/**
	 * Removes a callback, if it exists.
	 * 
	 * @param updater
	 *            object to be removed from the callback list
	 */
	void unregisterSink(MetricsSink sink);

	/**
	 * Removes a callback, if it exists.
	 * 
	 * @param updater
	 *            object to be removed from the callback list
	 */
	void unregisterSink(String sinkName);

	void registerSource(MetricsSource source);

	/**
	 * Returns the timer period.
	 */
	int getPeriod();

	void start();

	void stop();

}
