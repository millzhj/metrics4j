package mill.zhj.metrics;

public abstract class Metircs implements MetricsInfo {

	private MetricsInfo info;

	public Metircs(MetricsInfo info) {
		this.info = info;
	}

	@Override
	public String name() {
		return info.name();
	}

	@Override
	public String description() {
		return info.description();
	}

	public abstract Number value();

	public abstract MetricType type();

}
