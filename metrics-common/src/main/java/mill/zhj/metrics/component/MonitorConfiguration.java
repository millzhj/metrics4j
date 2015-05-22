package mill.zhj.metrics.component;

import java.lang.reflect.Constructor;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import mill.zhj.metrics.MetricsSink;
import mill.zhj.metrics.MetricsSource;

import org.apache.commons.lang.StringUtils;
import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

public class MonitorConfiguration {

	private String application;

	private List<MetricsContext> contexts = Lists.newArrayList();

	private MonitorConfiguration() {
	}
	
	/**
	 *  must refactor the parse process TODO
	 * @param file
	 * @return
	 */
	@SuppressWarnings({ "unused", "unchecked" })
	public static MonitorConfiguration create(String file) {
		MonitorConfiguration configuration = new MonitorConfiguration();
		SAXReader reader = new SAXReader();
		try {
			Document doc = reader.read(file);
			Element root = doc.getRootElement();
			configuration.application = root.attribute("name").getText();
			List<Element> contextElements = root.elements("Context");
			MetricsContext context;
			for (Element contextElement : contextElements) {
				Element msElement = contextElement.element("MetricSource");
				if (msElement == null)
					throw new IllegalArgumentException("MetricSource must not be null.");
				String contextName = contextElement.attributeValue("name");
				String clazz = msElement.attributeValue("class");
				context = new MetricsContext(configuration.getApplication(), contextName);
				String periodStr = contextElement.attributeValue("period");
				if (!StringUtils.isEmpty(periodStr)) {
					int period = Integer.parseInt(periodStr);
					context.setPeriod(period);
				}
				List<Element> propElements = msElement.elements("property");
				Map<String, String> conf = configuration.getProperties(msElement);
				MetricsSource source = configuration.create(clazz, configuration.getApplication(), contextName, conf);
				context.setMetricsSource(source);

				List<Element> sinkElements = contextElement.elements("MetricSink");

				for (Element sinkElement : sinkElements) {
					String sinkName = sinkElement.attributeValue("name");
					String className = sinkElement.attributeValue("class");
					Map<String, String> conf2 = configuration.getProperties(sinkElement);
					MetricsSink sink = configuration.create(className, sinkName, conf2);
					context.registerMetricsSink(sink);
				}

				configuration.addMetricsContext(context);
			}
		} catch (Exception e) {
			throw new IllegalStateException(e.getMessage(), e);
		}

		return configuration;
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	private MetricsSource create(String className, String application, String context, Map<String, String> conf) throws Exception {
		Class sourceClazz = Class.forName(className);
		Constructor constructor = sourceClazz.getConstructor(String.class, String.class, Map.class);
		MetricsSource source = (MetricsSource) constructor.newInstance(application, context, conf);
		return source;
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	private MetricsSink create(String className, String name, Map<String, String> conf) throws Exception {
		Class sinkClazz = Class.forName(className);
		Constructor constructor = sinkClazz.getConstructor(String.class, Map.class);
		MetricsSink sink = (MetricsSink) constructor.newInstance(name, conf);
		return sink;
	}

	@SuppressWarnings("unchecked")
	private Map<String, String> getProperties(Element element) {
		List<Element> propElements = element.elements("property");
		Map<String, String> conf = Maps.newHashMap();
		if (propElements != null) {
			for (Element propElement : propElements) {
				String name = propElement.attributeValue("name");
				String value = propElement.attributeValue("value");
				conf.put(name, value);
			}
		}
		return conf;
	}

	public String getApplication() {
		return application;
	}

	public List<MetricsContext> getMetricsContexts() {

		return Collections.unmodifiableList(contexts);
	}

	private void addMetricsContext(MetricsContext context) {
		contexts.add(context);
	}
}
