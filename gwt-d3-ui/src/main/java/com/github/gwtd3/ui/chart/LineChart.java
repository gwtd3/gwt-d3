/**
 * Copyright (c) 2013, Anthony Schiochet and Eric Citaire
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 *
 * * Redistributions of source code must retain the above copyright notice, this
 *   list of conditions and the following disclaimer.
 *
 * * Redistributions in binary form must reproduce the above copyright notice,
 *   this list of conditions and the following disclaimer in the documentation
 *   and/or other materials provided with the distribution.
 *
 * * The names Anthony Schiochet and Eric Citaire may not be used to endorse or promote products
 *   derived from this software without specific prior written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS"
 * AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE
 * IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE
 * DISCLAIMED. IN NO EVENT SHALL MICHAEL BOSTOCK BE LIABLE FOR ANY DIRECT,
 * INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING,
 * BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE,
 * DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY
 * OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING
 * NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE,
 * EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */
package com.github.gwtd3.ui.chart;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.github.gwtd3.api.core.Selection;
import com.github.gwtd3.api.core.Value;
import com.github.gwtd3.api.functions.DatumFunction;
import com.github.gwtd3.api.scales.LinearScale;
import com.github.gwtd3.ui.data.DefaultSelectionUpdater;
import com.github.gwtd3.ui.data.SelectionDataJoiner;
import com.github.gwtd3.ui.event.SerieAddedEvent;
import com.github.gwtd3.ui.event.SerieAddedEvent.SerieAddedHandler;
import com.github.gwtd3.ui.event.SerieChangeEvent;
import com.github.gwtd3.ui.event.SerieChangeEvent.SerieChangeHandler;
import com.github.gwtd3.ui.event.SerieRemovedEvent;
import com.github.gwtd3.ui.event.SerieRemovedEvent.SerieRemovedHandler;
import com.github.gwtd3.ui.model.AxisCoordsBuilder;
import com.github.gwtd3.ui.model.LineChartModel;
import com.github.gwtd3.ui.model.PointBuilder;
import com.github.gwtd3.ui.model.RangeDomainFilter;
import com.github.gwtd3.ui.model.Serie;
import com.github.gwtd3.ui.model.Serie.NamedRange;
import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.Element;
import com.google.gwt.resources.client.CssResource.ImportedWithPrefix;
import com.google.web.bindery.event.shared.HandlerRegistration;

/**
 * A line chart displaying several series on the same amount.
 * <p>
 * You can customize the styling by providing your own {@link Styles} instance during construction of the chart.
 * <p>
 * You can configure the chart behaviour using {@link #options()}.
 * <p>
 * User is able by default to navigate accross the X dimension domain. Call {@link Options#enableXNavigation(boolean)} with false to disable it.
 * <p>
 * FIXME: styling lines (colors, etc...) FIXME: styling serie label (position, font, etc...)
 * 
 * FIXME: customize scaling functions (linear, log, etc...)
 * 
 * FIXME: configuring ticks
 * 
 * FIXME: slide in X => events to grab new data
 * 
 * FIXME: caching hidden data if slide is possible by presending events
 * 
 * @author <a href="mailto:schiochetanthoni@gmail.com">Anthony Schiochet</a>
 * 
 * @param <T>
 */
public class LineChart<T> extends BaseChart<T> implements SerieAddedHandler<T>, SerieRemovedHandler<T>,
		SerieChangeHandler<T> {

	// ========== Resources and Styles classes =====================

	private static Resources createDefaultResources() {
		return GWT.create(Resources.class);
	}

	public static interface Resources extends BaseChart.Resources {
		@Override
		@Source("LineChart.css")
		LineChart.Styles chartStyles();
	}

	// public static interface XAxisResources extends ChartAxis.Resources {
	// @Source("ChartAxis.css")
	// ChartAxis.Styles xStyles();
	// }
	//
	// public static interface YAxisResources extends ChartAxis.Resources {
	// @Source("ChartAxis.css")
	// ChartAxis.Styles yStyles();
	// }

	@ImportedWithPrefix("d3-line-chart")
	public static interface Styles extends BaseChart.Styles {

		/**
		 * will be applied to any line serie.
		 * 
		 * @return
		 */
		public String line();

		/**
		 * a classna;e applied to series lines.
		 * 
		 * @return
		 */
		public String serie();

		/**
		 * class applied to range of values defined in any {@link NamedRange}.
		 * 
		 * @return the named class
		 */
		public String namedRange();

	}

	/**
	 * The model defining this chart
	 */
	private LineChartModel<T, LinearScale> model;

	private LineChart.Styles styles;

	private PointBuilder<T> pointBuilder;

	private PointBuilder<T> domainBuilder;

	public LineChart(final PointBuilder<T> domainBuilder) {
		this(domainBuilder, createDefaultResources());
	}

	public LineChart(final PointBuilder<T> domainBuilder, final Resources resources) {
		super(resources);
		this.domainBuilder = domainBuilder;
		// getElement().setAttribute("viewBox", "0 0 500 400");
		styles = resources.chartStyles();
		styles.ensureInjected();

		this.model = new LineChartModel<T, LinearScale>(xModel, yModel, domainBuilder);
	}

	@Override
	protected void initModel() {
		super.initModel();

		this.model.addSerieAddedHandler(this);
		this.model.addSerieRemovedHandler(this);

		// listens for range changed

		pointBuilder = new AxisCoordsBuilder<T>(xModel, yModel, domainBuilder);
	}

	// ============== initialization ========================

	// ==================== redraw methods ================

	@Override
	protected void redrawSeries() {
		super.redrawSeries();

		// create a drawer for the series
		DefaultSelectionUpdater<Serie<T>> serieDrawer = new DefaultSelectionUpdater<Serie<T>>("." + styles.serie()) {
			// create a path
			@Override
			public String getElementName() {
				return "path";
			}

			@Override
			public String getKey(final Serie<T> datum, final int index) {
				return datum.id();
			}

			// add the .line class to the newly created path
			@Override
			public void afterEnter(final Selection selection) {
				super.afterEnter(selection);
				selection.classed(styles.line(), true);
				selection.classed(styles.serie(), true);
				// apply clip path
				getSerieClipPath().apply(selection);
			}

			// update the d attribute
			@Override
			public void onJoinEnd(final Selection selection) {
				super.onJoinEnd(selection);
				// setting the attribute d of the path
				selection.attr("d", new DatumFunction<String>() {
					@SuppressWarnings("unchecked")
					@Override
					public String apply(final Element context, final Value d, final int index) {
						Serie<T> serie = d.<Serie<T>> as();
						List<T> values = serie.getValues();
						LineGenerator<T> generator =
								new LineGenerator<T>(
										pointBuilder,
										new RangeDomainFilter<T>(domainBuilder,
												xModel));
						return generator.generate(values);
					}
				});

			}

		};
		// create, update (and remove), a path element for each serie
		SelectionDataJoiner.update(
				g.select(), // inside the root G
				model.series(),// the data is the series
				serieDrawer
				);

		// update the selection with a path for each named range in a serie
		DefaultSelectionUpdater<Serie<T>.NamedRange> namedRangeDrawer =
				new DefaultSelectionUpdater<Serie<T>.NamedRange>("." + styles.namedRange()) {
					// create a path
					@Override
					public String getElementName() {
						return "path";
					}

					@Override
					public String getKey(final Serie<T>.NamedRange datum, final int index) {
						return datum.serie().id() + "." + datum.id();
					}

					// add the .namedRange class to the newly created
					@Override
					public void afterEnter(final Selection selection) {
						super.afterEnter(selection);
						selection.classed(styles.line(), true);
						selection.classed(styles.namedRange(), true);
						getSerieClipPath().apply(selection);
					}

					// update the d attribute
					@Override
					public void onJoinEnd(final Selection selection) {
						super.onJoinEnd(selection);
						// setting the attribute d of the path
						selection.attr("d", new DatumFunction<String>() {
							@Override
							public String apply(final Element context, final Value d, final int index) {
								Serie<T>.NamedRange namedRange = d.<Serie<T>.NamedRange> as();
								// filter the points with the range
								LineGenerator<T> lineGenerator = new LineGenerator<T>(pointBuilder, namedRange);
								return lineGenerator.generate(namedRange.getValues());
							}
						});
					}

				};
		// create, update (and remove), a path element for each named range of each serie
		List<Serie<T>> series = model.series();
		for (Serie<T> serie : series) {
			List<Serie<T>.NamedRange> ranges = serie.getOverlappingRanges(xModel.visibleDomain());
			GWT.log("named ranges count:" + ranges.size());
			SelectionDataJoiner.update(g.select(), ranges, namedRangeDrawer);
		}

	}

	// ============= getters =============

	protected LineChart.Styles styles() {
		return styles;
	}

	public LineChartModel<T, LinearScale> model() {
		return model;
	}

	// =========== listens to model events ==============

	Map<Serie<T>, HandlerRegistration> serieChangeRegistrations = new HashMap<Serie<T>, HandlerRegistration>();

	@Override
	public void onSerieRemoved(final SerieRemovedEvent<T> event) {
		HandlerRegistration registration = serieChangeRegistrations.remove(event.getSerie());
		if (registration != null) {
			registration.removeHandler();
		}
		redrawSeries();
	}

	@Override
	public void onSerieAdded(final SerieAddedEvent<T> event) {
		// attach listener on the serie
		serieChangeRegistrations.put(event.getSerie(), event.getSerie().addSerieChangeHandler(this));
		redrawSeries();
	}

	@Override
	public void onSerieChange(final SerieChangeEvent<T> event) {
		// TODO: find a way to redraw only the serie that changed
		redrawSeries();
	}

}
