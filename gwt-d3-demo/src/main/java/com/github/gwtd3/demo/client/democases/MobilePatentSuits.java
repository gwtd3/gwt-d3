/**
 * Copyright (c) 2017, TOMOTON GmbH
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
 * * The name TOMOTON GmbH may not be used to endorse or promote products
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
package com.github.gwtd3.demo.client.democases;

import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.Set;

import com.github.gwtd3.api.D3;
import com.github.gwtd3.api.arrays.Array;
import com.github.gwtd3.api.core.Selection;
import com.github.gwtd3.api.core.Value;
import com.github.gwtd3.api.functions.DatumFunction;
import com.github.gwtd3.api.layout.Force;
import com.github.gwtd3.api.layout.Force.ForceEventType;
import com.github.gwtd3.api.layout.Force.Link;
import com.github.gwtd3.api.layout.Force.Node;
import com.github.gwtd3.demo.client.DemoCase;
import com.github.gwtd3.demo.client.Factory;
import com.google.gwt.core.client.GWT;
import com.google.gwt.core.client.JavaScriptObject;
import com.google.gwt.dom.client.Element;
import com.google.gwt.resources.client.ClientBundle;
import com.google.gwt.resources.client.CssResource;
import com.google.gwt.user.client.ui.FlowPanel;

/**
 * This is a GWT implementation of Mike Bostock's 'Mobile Patent Suit' D3.js infographic.
 * @see <a href="https://gist.github.com/mbostock/1153292">Mobile Patent Suits</a>.
 * 
 * This code example demonstrates a possible use of the Java wrapper API
 * to create a force-directed graph layout. 
 */
public class MobilePatentSuits extends FlowPanel implements DemoCase {
	
	public static final Resources R = GWT.create(Resources.class);
	
	public interface Resources extends ClientBundle {
		

		interface Style extends CssResource {
		
			String link();
			
			String licensing();

			String resolved();
			
		}
		
		@Source("MobilePatentSuits.css")
		public Style style();

	}

	public class Suit {
		
		private String source;
		
		private String target;
		
		private String type;

		public Suit(String source, String target, String type) {
			this.source = source;
			this.target = target;
			this.type = type;
		}

		public String getSource() {
			return source;
		}

		public String getTarget() {
			return target;
		}

		public String getType() {
			return type;
		};
		
	}
	
	private final Suit[] suits = new Suit[] {
			  new Suit("Microsoft", "Amazon", "licensing"),
			  new Suit("Microsoft", "HTC", "licensing"),
			  new Suit("Samsung", "Apple", "suit"),
			  new Suit("Motorola", "Apple", "suit"),
			  new Suit("Nokia", "Apple", "resolved"),
			  new Suit("HTC", "Apple", "suit"),
			  new Suit("Kodak", "Apple", "suit"),
			  new Suit("Microsoft", "Barnes & Noble", "suit"),
			  new Suit("Microsoft", "Foxconn", "suit"),
			  new Suit("Oracle", "Google", "suit"),
			  new Suit("Apple", "HTC", "suit"),
			  new Suit("Microsoft", "Inventec", "suit"),
			  new Suit("Samsung", "Kodak", "resolved"),
			  new Suit("LG", "Kodak", "resolved"),
			  new Suit("RIM", "Kodak", "suit"),
			  new Suit("Sony", "LG", "suit"),
			  new Suit("Kodak", "LG", "resolved"),
			  new Suit("Apple", "Nokia", "resolved"),
			  new Suit("Qualcomm", "Nokia", "resolved"),
			  new Suit("Apple", "Motorola", "suit"),
			  new Suit("Microsoft", "Motorola", "suit"),
			  new Suit("Motorola", "Microsoft", "suit"),
			  new Suit("Huawei", "ZTE", "suit"),
			  new Suit("Ericsson", "ZTE", "suit"),
			  new Suit("Kodak", "Samsung", "resolved"),
			  new Suit("Apple", "Samsung", "suit"),
			  new Suit("Kodak", "RIM", "suit"),
			  new Suit("Nokia", "Qualcomm", "suit")		
	};
	
	private  Force<String> force;
	
	private Selection path;
	
	private Selection circle;
	
	private Selection text;
	
    public MobilePatentSuits() {
    	R.style().ensureInjected();
    }
    
    /**
     * Convenience function to overcome API limitation. 
     */
    static final native <T> Node<T> newNode(T userDatum)/*-{
		return {
			datum : userDatum
		};
    }-*/;
    
    /**
     * Convenience function to overcome API limitation. 
     */    
    static final native <T> Link<T> newLinkIndex(int source, int target, Suit userDatum) /*-{
		return {
			source : source,
			target : target,
			datum: userDatum
		};
    }-*/;

    /**
     * Convenience function to overcome API limitation. 
     */
    static final native Suit getDatum(JavaScriptObject object) /*-{
    	return object.datum;
    }-*/;
    
	@Override
	public void start() {
		//? Enumerate all unique companies.
		Set<String> companySet = new HashSet<>();
		for (Suit suit: suits) {
			companySet.add(suit.getSource());
			companySet.add(suit.getTarget());
		}
		
		LinkedHashMap<String, Node<String>> map = new LinkedHashMap<>();
		for (Suit suit: suits) {
			if (!map.containsKey(suit.getSource())) {
				map.put(suit.getSource(), newNode(suit.source));
			}
			if (!map.containsKey(suit.getTarget())) {
				map.put(suit.getTarget(), newNode(suit.target));
			}
		}		
		Array<Force.Node<String>> nodes = Array.fromIterable(map.values());
		
		/**
		 * For convenience, a link’s source and target properties may be initialized
		 * using numeric or string identifiers rather than object references.
		 */
		Array<Force.Link<String>> links = Array.create();
		for (Suit suit: suits) {
			Node<String> source = map.get(suit.source);
			Node<String> target = map.get(suit.target);
			Force.Link<String> link = newLinkIndex(nodes.indexOf(source), nodes.indexOf(target), suit);
			links.push(link);
		}
		
		double width = 960;
		double height = 500;
		
		force = D3.layout().force().cast();
		
		force
		.nodes(nodes)
		.links(links)
		.size(width, height)
		.linkDistance(60f)
		.charge(-300f)
		.on(ForceEventType.TICK, new DatumFunction<Void>() {
			@Override
			public Void apply(Element context, Value d, int index) {
			    path.attr("d", new DatumFunction<String>() {
					@Override
					public String apply(Element context, Value d, int index) {
						Link<?> link = d.as(Link.class);
						double dx = link.target().x() - link.source().x();
						double dy = link.target().y() - link.source().y();
						double dr = Math.sqrt(dx * dx + dy * dy);						
						return "M" + link.source().x() + "," + link.source().y() + "A" + dr + "," + dr + " 0 0,1 " + link.target().x() + "," + link.target().y();
					}});
				circle.attr("transform", new DatumFunction<String>() {
					@Override
					public String apply(Element context, Value d, int index) {
						Node<?> node = d.as(Node.class);
						return "translate(" + node.x() + "," + node.y() + ")";
					}
				});
				text.attr("transform",  new DatumFunction<String>() {
					@Override
					public String apply(Element context, Value d, int index) {
						Node<?> node = d.as(Node.class);
						return "translate(" + node.x() + "," + node.y() + ")";
					}
				});				
				return null;
			}
		});
		
		force.start();

		Selection svg = D3
					.select(this)
					.append("svg")
					.attr("width", width)
					.attr("height", height);

		svg
			.append("defs")
			.selectAll("marker")
			.data(new Object[] { "suit", "licensing", "resolved" })
			.enter()
			.append("marker")
			.attr("id", new DatumFunction<String>() {
				@Override
				public String apply(Element context, Value d, int index) {
					return d.asString();
				}
			 })
			.attr("viewBox", "0 -5 10 10")
			.attr("refX", 15)
			.attr("refY", -1.5)
			.attr("markerWidth", 6)
			.attr("markerHeight", 6)
			.attr("orient", "auto")
			.append("path")
			.attr("d", "M0,-5L10,0L0,5");
		
		path = svg
			.append("g")
			.selectAll("path")
			.data(force.links())
			.enter().append("path")
		    .attr("class", new DatumFunction<String>() {
				@Override
				public String apply(Element context, Value d, int index) {
					Link<?> link = d.as(Link.class);
					Suit suit = getDatum(link);
					String style = R.style().link();
					switch (suit.type) {
						case "licensing":
							style += ' ' + R.style().licensing();
							break;
						case "resolved":
							style += ' ' + R.style().resolved();
							break;
						default:
					}
					return style;
				}
		    })
			.attr("marker-end", new DatumFunction<String>() {
				@Override
				public String apply(Element context, Value d, int index) {
					Link<?> link = d.as(Link.class);
					Suit suit = getDatum(link);
					return "url(#" + suit.type + ")";
				}
			});

		circle = svg
			.append("g")
			.selectAll("circle")
		    .data(force.nodes())
		    .enter()
		    .append("circle")
		    .attr("r", 6)
		    .call(force.drag());
		
		text = svg
			.append("g")
			.selectAll("text")
		    .data(force.nodes())
		    .enter().append("text")
		    .attr("x", 8)
		    .attr("y", ".31em")
		    .text(new DatumFunction<String>() {
				@Override
				public String apply(Element context, Value d, int index) {
					Node<String> node = d.as();
					return node.datum();
				}
		    });
		
	}
	
	@Override
	public void stop() {
		if (force != null) {
			force.stop();
		}
	}
	
	public static Factory factory() {
		return new Factory() {
			@Override
			public DemoCase newInstance() {
				return new MobilePatentSuits();
			}
		};
	}	

}
