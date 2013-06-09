package com.github.gwtd3.ui.model;

/**
 * Converts an arbytrary T object into a pixel distance on an axis, using a
 * delegate {@link PointBuilder} that can convert a object T into domain double
 * values.
 * 
 * @author SCHIOCA
 * 
 * @param <T>
 */
public class AxisCoordsBuilder<T> implements PointBuilder<T> {

	private final AxisModel<?> xModel;
	private final AxisModel<?> yModel;
	private final PointBuilder<T> domainBuilder;

	public AxisCoordsBuilder(final AxisModel<?> xModel, final AxisModel<?> yModel, final PointBuilder<T> domainBuilder) {
		super();
		this.xModel = xModel;
		this.yModel = yModel;
		this.domainBuilder = domainBuilder;
	}

	@Override
	public double x(final T value) {
		return xModel.toPixel(domainBuilder.x(value));
	}

	@Override
	public double y(final T value) {
		return yModel.toPixel(domainBuilder.y(value));
	}
}
