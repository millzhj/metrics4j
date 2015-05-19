package mill.zhj.metrics;

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

/**
 * The metrics system interface
 */
public abstract class MetricsSystem {

	public abstract MetricsSystem init(String prefix);

	/**
	 * Register a metrics source
	 * 
	 * @param name
	 * @param desc
	 * @param source
	 * @return
	 */

	public abstract MetricsSource register(String name, String desc, MetricsSource source);

	/**
	 * Unregister a metrics source
	 * 
	 * @param name
	 *            of the source. This is the name you use to call register()
	 */
	public abstract void unregisterSource(String name);

	/**
	 * Register a metrics source (deriving name and description from the object)
	 * 
	 * @param source
	 * @return
	 */

	public MetricsSource register(MetricsSource source) {
		return register(null, null, source);
	}

	/**
	 * @param name
	 *            of the metrics source
	 * @return the metrics source (potentially wrapped) object
	 */

	public abstract MetricsSource getSource(String name);

	/**
	 * Register a metrics sink
	 * 
	 * @param name
	 * @param desc
	 * @param sink
	 * @return
	 */

	public abstract MetricsSink register(String name, String desc, MetricsSink sink);

	/**
	 * Requests an immediate publish of all metrics from sources to sinks.
	 * 
	 * This is a "soft" request: the expectation is that a best effort will be done to synchronously snapshot the
	 * metrics from all the sources and put them in all the sinks (including flushing the sinks) before returning to the
	 * caller. If this can't be accomplished in reasonable time it's OK to return to the caller before everything is
	 * done.
	 */
	public abstract void publishMetricsNow();

	/**
	 * Shutdown the metrics system completely (usually during server shutdown.) The MetricsSystemMXBean will be
	 * unregistered.
	 * 
	 * @return true if shutdown completed
	 */
	public abstract boolean shutdown();

}
