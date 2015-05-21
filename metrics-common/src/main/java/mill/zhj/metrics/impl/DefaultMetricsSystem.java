package mill.zhj.metrics.impl;

import java.util.List;
import java.util.Map;
import java.util.Set;

import mill.zhj.metrics.MetricsContext;
import mill.zhj.metrics.MetricsSystem;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.collect.Maps;

public class DefaultMetricsSystem implements MetricsSystem {

	private static final String CONFIG_NAME = "monitor-conf";

	private static final String DEFALUT_CONF_FILE = "monitor.xml";

	private String application;

	private Map<String, MetricsContext> metricsContexts = Maps.newConcurrentMap();

	private static final DefaultMetricsSystem INSTANCE = new DefaultMetricsSystem();

	private static final Logger logger = LoggerFactory.getLogger(DefaultMetricsSystem.class);

	private DefaultMetricsSystem() {
		// load from file path
		String file = System.getProperty(CONFIG_NAME);
		if (StringUtils.isEmpty(file)) {
			// default load config from classpath
			file = DEFALUT_CONF_FILE;
			file = this.getClass().getClassLoader().getResource(file).getFile();
		}
		try {
			init(file);
		} catch (IllegalStateException stateException) {
			throw new IllegalArgumentException("parse config error:" + stateException.getMessage(), stateException);
		}
	}

	private void init(String file) throws IllegalStateException {
		MonitorConfiguration configuration = MonitorConfiguration.create(file);
		application = configuration.getApplication();
		List<MetricsContext> contexts = configuration.getMetricsContexts();
		for (MetricsContext context : contexts) {
			logger.debug("install context " + application + "." + context.getContextName() + " monitor to system....");
			metricsContexts.put(context.getContextName(), context);
		}
	}

	public static DefaultMetricsSystem getInstance() {
		return INSTANCE;
	}

	@Override
	public void start() {
		logger.info("monitor " + application + " metrics system start....");
		Set<Map.Entry<String, MetricsContext>> entrySet = metricsContexts.entrySet();
		for (Map.Entry<String, MetricsContext> entry : entrySet) {
			logger.info(String.format("monitor %s.%s metrics start...", application, entry.getKey()));
			MetricsContext metricsContext = entry.getValue();
			metricsContext.start();
		}
	}

	@Override
	public void shutdown() {
		logger.info("monitor " + application + " metrics system stop....");
		Set<Map.Entry<String, MetricsContext>> entrySet = metricsContexts.entrySet();
		for (Map.Entry<String, MetricsContext> entry : entrySet) {
			logger.info(String.format("monitor %s.%s metrics stop...", application, entry.getKey()));
			MetricsContext metricsContext = entry.getValue();
			metricsContext.start();
		}
	}

}
