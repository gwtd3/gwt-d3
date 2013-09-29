#gwt-d3: the GWT wrapper around d3.js


This library provides access to the [d3.js API](http://d3js.org/) to the Java and GWT community.
It allows GWT developers to use d3.js library in their project.

You can see a demo with the test cases (for API coverage) and some exemples:
http://gwt-d3.appspot.com/

Goals of the library:
- provide access to relevant API of [d3.js API](http://d3js.org/)
- enrich the API to ease Java programming style of d3 (turns JS array into List, string constants to Enum, ...)
- make a bridge between GWT Widget API and d3 
 
##Getting started

How to use the library :
TODO:
- Basic eclipse GWT project creation tutorial
- MAVEN setup to use gwt-d3
- Basic use of d3
- links to some tutorials

##Resources

- <a href="http://gwtd3.github.io/gwt-d3/apidocs/">Javadoc</a>


##Scope and coverage

Currently (30th of sept 2013), version 1.0.0 of gwt-d3 covers the version 3.3.6 of d3.js.

#####CORE
- Selections 
- Transitions
- Working with Arrays (partial)
- Math  
- String Formatting
- Colors  (1.0.0)

#####SCALES
- Quantitative
 - Linear
 - sqrt
 - pow
 - log
 - quantize
 - threshold
 - quantile
 - identity
- Ordinal

#####SVG 
- Shapes
 - Line
 - symbol
- Axes

#####TIME
- Time Formatting
- Time Scales


#####GEOMETRY
- Quadtree
- Polygon
- Hull

#####BEHAVIORS
- Drag
- Zoom


##Roadmap

For details, you may be interested in the remaining [issues](https://github.com/gwtd3/gwt-d3/issues?milestone=&page=1&state=open):

#####CORE
- Loading External Resources (unplanned)
- Working with Arrays (unplanned)
- CSV Formatting (unplanned)
- Namespaces (unplanned)
- Internals (unplanned)

#####SVG 
- Shapes
 - line.radial (1.1.0)
 - area (1.1.0)
 - area.radial (1.1.0)
 - arc (1.1.0)
 - chord (1.1.0)
 - diagonal (1.1.0)
 - diagonal.radial (1.1.0)
- Controls (1.1.0)

#####Time 
- Time Intervals

#####LAYOUTS
- Bundle (1.2.0)
- Chord (1.2.0)
- Cluster (1.2.0)
- Force (1.2.0)
- Hierarchy (1.2.0)
- Histogram (1.2.0)
- Pack (1.2.0)
- Partition (1.2.0)
- Pie (1.2.0)
- Stack (1.2.0)
- Tree (1.2.0)
- Treemap (1.2.0)

#####GEOGRAPHY
- Paths (1.3.0)
- Projections (1.3.0)
- Streams (1.3.0)
 
#####GEOMETRY
- Voronoi (1.1.0)






Continuous integration on <a href="https://gwt-d3.ci.cloudbees.com/job/CI%20of%20gwt-d3/">Cloudbees</a>:
<a href='https://gwt-d3.ci.cloudbees.com/job/CI%20of%20gwt-d3/'><img src='https://gwt-d3.ci.cloudbees.com/buildStatus/icon?job=CI of gwt-d3'></a>
