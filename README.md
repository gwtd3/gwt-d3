#gwt-d3: the GWT wrapper around d3.js


This library provides access to the [d3.js API](http://d3js.org/) to the Java and GWT community.
It allows GWT developers to use d3.js library in their project.

You can see a demo with the test cases (for API coverage) and some exemples:
http://gwt-d3.appspot.com/

Goals of the library:
- provide access to relevant API of [d3.js API](http://d3js.org/)
- enrich the API to ease Java programming style of d3 (turns JS array into List, string constants to Enum, ...)
- make a bridge between GWT Widget API and d3 

Continuous integration on <a href="https://gwt-d3.ci.cloudbees.com/job/CI%20of%20gwt-d3/">Cloudbees</a>:
<a href='https://gwt-d3.ci.cloudbees.com/job/CI%20of%20gwt-d3/'><img src='https://gwt-d3.ci.cloudbees.com/buildStatus/icon?job=CI of gwt-d3'></a>


##Getting started

How to use the library :
TODO:
- Basic eclipse GWT project creation tutorial
- MAVEN setup

##Roadmap:
See also below in the next section the API already covered.

- 1.0.0 (end of september 2013)
- 1.1.0 : complete the SVG Shapes, SVG Controls and Geometry API
- 1.2.0 : complete the Layout API
- 1.3.0 : geometry API
- 1.4.0 : geography API
- 1.5.0 : non essentials API
 
~~1.0.0 (planned by the end of august)~~


##API coverage 

For details, you may be interested in the remaining [issues](https://github.com/gwtd3/gwt-d3/issues?milestone=&page=1&state=open):

Here is the details of the covered API, as it is as 13th september 2013:
- CORE
 - Selections (1.0.0)
 - Transitions  (1.0.0)
 - Working with Arrays
 - Math  (1.0.0)
 - Loading External Resources
 - String Formatting  (1.0.0)
 - CSV Formatting (d3.csv)
 - Colors  (1.0.0)
 - Namespaces 
 - Internals
- SCALES (1.0.0)
 - Quantitative (1.0.0)
  - linear (1.0.0)
  - sqrt (1.0.0)
  - pow (1.0.0)
  - log (1.0.0)
  - quantize (1.0.0)
  - threshold (1.0.0)
  - quantile (1.0.0)
  - identity (1.0.0)
 - Ordinal (1.0.0)
- SVG 
 - Shapes
  - Line (1.0.0)
  - line.radial
  - area
  - area.radial
  - arc
  - symbol (1.0.0)
  - chord
  - diagonal
  - diagonal.radial
 - Axes (1.0.0)
 - Controls
- TIME
 - Time Formatting (1.0.0)
 - Time Scales (1.0.0)
 - Time Intervals
- LAYOUTS
 - Bundle
 - Chord
 - Cluster
 - Force
 - Hierarchy
 - Histogram
 - Pack
 - Partition
 - Pie
 - Stack
 - Tree
 - Treemap
- GEOGRAPHY
 - Paths
 - Projections
 - Streams
- GEOMETRY
 - Voronoi
 - Quadtree (1.0.0)
 - Polygon (1.0.0)
 - Hull (1.0.0)
- BEHAVIORS
 - Drag (1.0.0)
 - Zoom (1.0.0)


