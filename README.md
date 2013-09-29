#gwt-d3: the GWT wrapper around d3.js


This library provides access to the [d3.js API](http://d3js.org/) to the Java and GWT community.
It allows GWT developers to use d3.js library in their project.

Have a look to the 
<a href="http://gwt-d3.appspot.com/">demo</a>.

Goals of the library:
- provide access to relevant API of [d3.js API](http://d3js.org/)
- enrich the API to ease Java programming style of d3 (turns JS array into List, string constants to Enum, ...)
- make a bridge between GWT Widget API and d3 
 

##Getting started

Configure the pom.xml file to add the dependency to gwt-d3-api module.
(FIXME: update the version as 1.0.0 is released)

```xml
<dependencies>
 ...
 <dependency>
 	<groupId>com.github.gwtd3</groupId>
 	<artifactId>gwt-d3-api</artifactId>
 	<version>0.0.16</version>
 </dependency>
 ...
```

Add the gwt-d3 release repository to your pom.
(FIXME : remove this as the release is published to maven central)
```xml
<repositories>
 ...
	<repository>
		<id>gwtd3</id>
		<name>GWT-D3</name>
		<url>http://repository-gwt-d3.forge.cloudbees.com/release/</url>
	</repository>
	...
</repositories>
```

Add the gwt D3 module inheritance in your gwt module file (.gwt.xml):
```xml
  <inherits name='com.github.gwtd3.D3' />
```

Check everything works fine :
```java 
final Label versionLabel = new Label("d3.js current version: " + D3.version());
    RootPanel.get().add(versionLabel);
```

Launch your application; the current version of d3.js should be displayed.

##Learn D3

- The best way to learn D3 is to read the <a href="https://github.com/mbostock/d3/wiki/Tutorials">d3.js tutorials<a/> 
- Then you may want to check the examples gallery from <a href="https://github.com/mbostock/d3/wiki/Gallery">Mike Bostock website</a>
- You may also be interested in forking the gwt-d3 repository to see the demo source code
- Read the <a href="http://gwtd3.github.io/gwt-d3/apidocs/">Javadocs</a>
- Look at the <a href="http://gwt-d3.appspot.com/">demo</a>.


##Version mapping

| GWT-D3 version | d3.js version | GWT-D3 release date  |
| -------------: | -------------:| -----:|
| 1.0.0 (preparing)          | 3.3.6         | 2013 09 30  |
| <a href="http://repository-gwt-d3.forge.cloudbees.com/release/com/github/gwtd3/gwt-d3-api/0.0.16/">0.0.16</a>          | 3.3.6         | 2013 09 29 |
| 0.0.15          | 3.1.10         | 2013 07 29 |
| 0.0.14          | 3.1.10         | 2013 07 24 |
| 0.0.13          | 3.1.10         | 2013 06 13 |
| 0.0.12          | 3.1.10         | 2013 06 01 |




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

The version 1.x of the library (x>=1) is an ongoing work to cover all the missing API, as stated below:

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


Version 2.x may come in a far future with a package reorganization, based on the feedbacks of API users.




Continuous integration on <a href="https://gwt-d3.ci.cloudbees.com/job/CI%20of%20gwt-d3/">Cloudbees</a>:
<a href='https://gwt-d3.ci.cloudbees.com/job/CI%20of%20gwt-d3/'><img src='https://gwt-d3.ci.cloudbees.com/buildStatus/icon?job=CI of gwt-d3'></a>
