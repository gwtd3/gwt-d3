package com.github.gwtd3.ui.model;


public class RangeDomainFilter<T> implements DomainFilter<T> {

	private final PointBuilder<T> builder;
	private final AxisModel<?> acceptRange;

	public RangeDomainFilter(PointBuilder<T> builder, AxisModel<?> acceptRange) {
		super();
		this.builder = builder;
		this.acceptRange = acceptRange;
	}

	@Override
	public boolean accept(T value) {
		return acceptRange.visibleDomain().contains(builder.x(value));
	}
}
