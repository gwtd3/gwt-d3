package com.github.gwtd3.demo.client.democases.layout;

import com.github.gwtd3.api.D3;
import com.github.gwtd3.api.arrays.Array;
import com.github.gwtd3.api.core.Selection;
import com.github.gwtd3.api.core.Value;
import com.github.gwtd3.api.functions.DatumFunction;
import com.github.gwtd3.api.functions.PropertyValueFunction;
import com.github.gwtd3.api.layout.Cluster;
import com.github.gwtd3.api.layout.Link;
import com.github.gwtd3.api.layout.Node;
import com.github.gwtd3.api.svg.Diagonal;
import com.github.gwtd3.demo.client.DemoCase;
import com.github.gwtd3.demo.client.Factory;
import com.google.gwt.core.client.GWT;
import com.google.gwt.core.client.JsonUtils;
import com.google.gwt.dom.client.Element;
import com.google.gwt.http.client.Request;
import com.google.gwt.http.client.RequestBuilder;
import com.google.gwt.http.client.RequestCallback;
import com.google.gwt.http.client.RequestException;
import com.google.gwt.http.client.Response;
import com.google.gwt.resources.client.ClientBundle;
import com.google.gwt.resources.client.CssResource;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.FlowPanel;

public class ClusterDendogram extends FlowPanel implements DemoCase {

	private static final String JSON_URL = // GWT.getModuleBaseURL() +
			"demo-data/flare.json";
	private final MyResources css;

	public interface Bundle extends ClientBundle {
		public static final Bundle INSTANCE = GWT.create(Bundle.class);

		@Source("ClusterDendogram.css")
		public MyResources css();
	}

	interface MyResources extends CssResource {
		String link();

		String node();
	}

	public ClusterDendogram() {
		css = Bundle.INSTANCE.css();
		css.ensureInjected();
	}

	@Override
	public void start() {
		int width = 960;
		final int height = 2200;

		final Cluster cluster = D3.layout().cluster()
				.size(height, width - 160);

		final Diagonal diagonal = D3.svg().diagonal()
				.projection(
						new DatumFunction<Array<Double>>() {
							@Override
							public Array<Double> apply(final Element context, final Value value, final int index) {
								return Array.fromDoubles(value.asCoords().y(), value.asCoords().x());
							}
						}
						);

		final Selection svg = D3.select(this).append("svg")
				.attr("width", width)
				.attr("height", height)
				.append("g")
				.attr("transform", "translate(40,0)");

		// Send request to server and catch any errors.
		RequestBuilder builder = new RequestBuilder(RequestBuilder.GET, JSON_URL);

		try {
			Request request = builder.sendRequest(null, new RequestCallback() {
				@Override
				public void onError(final Request request, final Throwable exception) {
					Window.alert("Couldn't retrieve JSON");
				}

				@Override
				public void onResponseReceived(final Request request, final Response response) {
					if (200 == response.getStatusCode()) {
						Node root = JsonUtils.safeEval(response.getText());
						Array<Node> nodes = cluster.nodes(root);
						Array<Link> links = cluster.links(nodes);

						Selection link = svg.selectAll("." + css.link())
								.data(links)
								.enter().append("path")
								.attr("class", css.link())
								.attr("d", diagonal);

						Selection node = svg.selectAll("." + css.node())
								.data(nodes)
								.enter().append("g")
								.attr("class", css.node())
								.attr("transform", new DatumFunction<String>() {
									@Override
									public String apply(final Element context, final Value value, final int index) {
										return "translate(" + value.asCoords().y() + "," + value.asCoords().x() + ")";
									}
								});

						node.append("circle")
						.attr("r", 4.5);

						node.append("text")
						.attr("dx",
								new DatumFunction<Integer>() {
							@Override
							public Integer apply(final Element context, final Value d, final int index) {
								return d.<Node> as().children() != null ? -8 : 8;
							}
						})
						.attr("dy", 3)
						.style("text-anchor",
								new DatumFunction<String>() {
							@Override
							public String apply(final Element context, final Value d, final int index) {
								return d.<Node> as().children() != null ? "end" : "start";
							}
						}
								)
								.text(PropertyValueFunction.<String> forProperty("name"));

						D3.select(ClusterDendogram.this).select("svg").style("height", height + "px");

					} else {
						Window.alert("Couldn't retrieve JSON (" + response.getStatusText()
								+ ")");
					}
				}
			});
		} catch (RequestException e) {
			Window.alert("Couldn't retrieve JSON");
		}

	}

	@Override
	public void stop() {

	}

	public static Factory factory() {
		return new Factory() {
			@Override
			public DemoCase newInstance() {
				return new ClusterDendogram();
			}
		};
	}

}
