package mill.zhj.metrics.impl;

import java.util.Iterator;
import java.util.Map;

import org.apache.commons.configuration.Configuration;
import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.configuration.PropertiesConfiguration;
import org.apache.commons.lang.StringUtils;

import com.google.common.collect.Maps;

import mill.zhj.metrics.MetricsSink;
import mill.zhj.metrics.MetricsSource;
import mill.zhj.metrics.MetricsSystem;

public class DefaultMetricsSystem implements MetricsSystem {

	private Map<String, MetricsSource> sources = Maps.newConcurrentMap();// key is context value si source

	private Map<String, MetricsSink> sinks = Maps.newConcurrentMap();// key is context value is sink

	private static final String PRPERTIES_NAME = "metrics-conf";

	private static final String DEFALUT_CONF_FILE = "conf.properties";

	private static final String APP_KEY = "app";

	private String application = "DEFAULT";

	private DefaultMetricsSystem() {
		// load from file path
		String file = System.getProperty(PRPERTIES_NAME);
		if (StringUtils.isEmpty(file)) {
			// default load config from classpath
			file = DEFALUT_CONF_FILE;
			file = this.getClass().getClassLoader().getResource(file).getFile();
		}
		try {
			Configuration config = new PropertiesConfiguration(file);
			init(config);
		} catch (ConfigurationException e) {
			throw new IllegalArgumentException("config file error:" + e.getMessage(), e);
		} catch (IllegalStateException stateException) {
			throw new IllegalArgumentException("parse config error:" + stateException.getMessage(), stateException);
		}
	}

	@SuppressWarnings("rawtypes")
	private void init(Configuration config) throws IllegalStateException {
		application = config.getString(APP_KEY);
		Iterator keys = config.getKeys();
		while (keys.hasNext()) {
			String key = (String) keys.next();
			if (key.equals(APP_KEY))
				continue;
			String value = (String) config.getString(key);
			String[] splitKey = key.split(".");
			if (splitKey.length < 3) {
				throw new IllegalStateException("config item key " + key + " error ,the default key length is 3 split by (.) pls check it");
			}
			MetricsSink sink = null;
			try {
				sink = (MetricsSink) Class.forName(value).newInstance();
			} catch (Exception e) {
				throw new IllegalStateException("init sink class " + value + " error :" + e.getMessage(), e);
			}
			sink.setApplication(splitKey[0]);// set application
			sink.setContext(splitKey[1]);
			register(sink);
		}
	}

	@Override
	public MetricsSource register(String context, MetricsSource source) {
		sources.put(context, source);
		return source;
	}

	@Override
	public void unregisterSource(String context) {
		sources.remove(context);

	}

	@Override
	public MetricsSource getSource(String name) {
		return sources.get(name);
	}

	@Override
	public MetricsSink register(MetricsSink sink) {
		sinks.put(sink.getContext(), sink);
		return sink;
	}

	@Override
	public void start() {
		
	}

	@Override
	public boolean shutdown() {
		// TODO Auto-generated method stub
		return false;
	}

}
