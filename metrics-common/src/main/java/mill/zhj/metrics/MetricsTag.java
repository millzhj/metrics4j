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

import org.apache.commons.lang.builder.ToStringBuilder;

/**
 * Immutable tag for metrics (for grouping on host/queue/username etc.)
 */

public class MetricsTag implements MetricsInfo {

	private final MetricsInfo info;
	private final String value;

	/**
	 * Construct the tag with name, description and value
	 * 
	 * @param info
	 *            of the tag
	 * @param value
	 *            of the tag
	 */
	public MetricsTag(MetricsInfo info, String value) {
		this.info = info;
		this.value = value;
	}

	@Override
	public String name() {
		return info.name();
	}

	@Override
	public String description() {
		return info.description();
	}

	/**
	 * @return the info object of the tag
	 */
	public MetricsInfo info() {
		return info;
	}

	/**
	 * Get the value of the tag
	 * 
	 * @return the value
	 */
	public String value() {
		return value;
	}

	@Override
	public boolean equals(Object obj) {
		if (obj instanceof MetricsTag) {
			final MetricsTag other = (MetricsTag) obj;
			return info.equals(other.info()) && value.equals(other.value());
		}
		return false;
	}

	@Override
	public int hashCode() {
		return info.hashCode() * value.hashCode();
	}

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this);
	}
}
