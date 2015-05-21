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

package mill.zhj.metrics.sink;

import java.io.File;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.util.Map;

import mill.zhj.metrics.Metric;
import mill.zhj.metrics.MetricsRecord;
import mill.zhj.metrics.MetricsSink;

/**
 * A metrics sink that writes to a file
 */

public class FileSink implements MetricsSink {

	private static final String FILENAME_KEY = "filename";
	private PrintWriter writer;
	private String name;

	public FileSink(String name, Map<String, String> conf) {
		this.name = name;
		String filename = conf.get(FILENAME_KEY);
		try {
			writer = filename == null ? new PrintWriter(System.out) : new PrintWriter(new FileWriter(new File(filename), true));
		} catch (Exception e) {
			throw new IllegalStateException("Error creating " + filename, e);
		}
	}

	@Override
	public void putMetrics(MetricsRecord record) {
		writer.print(record.timestamp());
		writer.print(" ");
		writer.print(record.application());
		writer.print(".");
		writer.print(record.context());
		String separator = ": ";
		for (Metric metric : record.metrics()) {
			writer.print(separator);
			separator = ", ";
			writer.print(metric.name());
			writer.print("=");
			writer.print(metric.value());
		}
		writer.println();
	}

	@Override
	public void flush() {
		writer.flush();
	}

	@Override
	public void stop() {
		writer.close();
	}

	@Override
	public String getName() {
		return name;
	}

}
