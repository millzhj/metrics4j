package mill.zhj.metrics;

public abstract class Metric implements MetricsInfo {

	private MetricsInfo info;

	public Metric(MetricsInfo info) {
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
