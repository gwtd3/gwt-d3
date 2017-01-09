#gwt-d3: the GWT wrapper around d3.js

[![Travis CI - Build status](https://travis-ci.org/gwtd3/gwt-d3.svg?branch=master)](https://travis-ci.org/gwtd3/gwt-d3)
[![Javadocs](http://javadoc.io/badge/com.github.gwtd3/gwt-d3.svg?color=blue&label=javadocs)](http://javadoc.io/doc/com.github.gwtd3/gwt-d3)


This library provides access to the [d3.js API](http://d3js.org/) to the Java and GWT community.
It allows GWT developers to use d3.js library in their project.

Goals of the library:
- provide access to relevant API of [d3.js API](http://d3js.org/)
- enrich the API to ease Java programming style of d3 (turns JS array into List, string constants to Enum, ...)
- make a bridge between GWT Widget API and d3 

Have a look at the 
<a href="http://gwtd3.github.io/demo/">demo</a>.

 
<img src="http://gwtd3.github.io/gwt-d3/images/demo_chorddiagram.png" width="355px">
&nbsp;
<img src="http://gwtd3.github.io/gwt-d3/images/demo_lorenzsystem.png" width="355px"> 
<br>
<img src="http://gwtd3.github.io/gwt-d3/images/demo_scatterplot_brushing.png" width="355px">
&nbsp;
<img src="http://gwtd3.github.io/gwt-d3/images/demo_brush_transition.png" width="355px"> 

##Getting started

**1. Configure a project**

**Using Maven**

add the gwt-d3-api module to your pom.xml:

```xml
<dependencies>
  ...
  <dependency>
    <groupId>com.github.gwtd3</groupId>
    <artifactId>gwt-d3-api</artifactId>
    <version>1.3.0</version>
    <scope>provided</scope>
  </dependency>
  ...
```

Note: these JARs don't contain any server-side code, so you don't need to package them into your webapp.

**Without Maven**

Download [gwt-d3-js-3.5.11.jar](http://central.maven.org/maven2/com/github/gwtd3/gwt-d3-js/3.5.11/gwt-d3-js-3.5.11.jar),
[gwt-d3-api-1.3.0.jar](http://central.maven.org/maven2/com/github/gwtd3/gwt-d3-api/1.3.0/gwt-d3-api-1.3.0.jar).
Add the JARs to the project classpath.

Note: these JARs don't contain any server-side code, so you don't need to package them into your webapp.

**2. Add the gwt D3 module inheritance in your gwt module file (.gwt.xml):**

```xml
  <inherits name='com.github.gwtd3.D3' />
```

**3. Check everything works fine :**

```java 
  final Label versionLabel = new Label("d3.js current version: " + D3.version());
  RootPanel.get().add(versionLabel);
```

Launch your application; the current version of d3.js should be displayed.

##Learn D3

- The best way to learn D3 is to read the <a href="https://github.com/mbostock/d3/wiki/Tutorials">d3.js tutorials</a> 
- Then you may want to check the examples gallery from <a href="https://github.com/mbostock/d3/wiki/Gallery">Mike Bostock website</a>
- You may also be interested in forking the gwt-d3 repository to see the demo source code
- Read the <a href="http://www.javadoc.io/doc/com.github.gwtd3/gwt-d3-api">Javadocs</a>
- Look at the <a href="http://gwtd3.github.io/demo/">demo</a>.

##API docs
* [1.3.0](http://www.javadoc.io/doc/com.github.gwtd3/gwt-d3-api/1.3.0)
* [1.2.0](http://www.javadoc.io/doc/com.github.gwtd3/gwt-d3-api/1.2.0)
* [1.1.1](http://www.javadoc.io/doc/com.github.gwtd3/gwt-d3-api/1.1.1)
* [1.1.0](http://www.javadoc.io/doc/com.github.gwtd3/gwt-d3-api/1.1.0)
* [1.0.1](http://www.javadoc.io/doc/com.github.gwtd3/gwt-d3-api/1.0.1)
* [1.0.0](http://www.javadoc.io/doc/com.github.gwtd3/gwt-d3-api/1.0.0)

##Release notes
- 1.3.0 (9th of jan 2017) : made GWT 2.8-compliant 
- 1.2.0 (28th of jan 2016) : added Arrays.median/Arrays.mean API, fix some regression to ensure future GWT 2.8.0 compatibility, added Cluster and Tree layouts  
- 1.1.1 (6th of feb 2015) : integrated a bug fix (color brighter() and darker() methods take a decimal rather than an integer argument
- 1.1.0 (3rd of feb 2015) : made GWT 2.7-compliant, completed SVG Shapes,SVG Controls, and Geometry API + some bug fixes
- 1.0.1 (7th of dec 2013) : a bug fix release to make gwt-d3 compatible with GWT super dev mode.
- 1.0.0 (30th of sept 2013) : initial release (see below for the API coverage)



##Scope and coverage

Currently (9th of jan 2017), version 1.3.0 of gwt-d3 depends on the version 3.5.11 of d3.js codebase.
However, not all the API is covered. Please also have a look to the <a href="https://github.com/gwtd3/gwt-d3/issues?milestone=&page=1&state=open">issues</a>.

#####CORE
| API | status | version | notes |
| -------------: | -------------:| -----:| --- |
| Selections | Complete | 1.0.0 | |
| Transitions  | Complete | 1.0.0  | |
| Localization | - | - | unplanned |
| Working with Arrays  | Partial | 1.0.0  | |
| Math  | Complete | 1.0.0  | |
| String Formatting | Complete | 1.0.0  | |
| Colors | Complete | 1.0.0  | |
| Loading External Resources | Partial | - | unplanned
| CSV Formatting | - | - | unplanned 
| Namespaces | - | - | unplanned 
| Internals | - | - | unplanned

#####SCALES
| API | status | version | notes |
| -------------: | -------------:| -----:| --- |
| Linear| Complete | 1.0.0
| sqrt| Complete | 1.0.0
| pow| Complete | 1.0.0
| log| Complete | 1.0.0
| quantize| Complete | 1.0.0
| threshold| Complete | 1.0.0
| quantile| Complete | 1.0.0
| identity| Complete | 1.0.0
| Ordinal| Complete | 1.0.0

#####SVG 
| API | status | version | notes |
| -------------: | -------------:| -----:| --- |
| **Shapes** |
| Line| Complete | 1.0.0
| line.radial | Complete |  1.1.0
| area | Complete |  1.1.0
| area.radial | - | - | not planned
| arc | Complete |  1.1.0
| chord | Complete |  1.1.0
| diagonal | Complete |  1.1.0
| diagonal.radial | Complete | 1.1.0
| Symbol| Complete | 1.0.0
| **Axes** | Complete | 1.0.0
| **Controls** | Complete | 1.1.0

#####LAYOUTS
| API | status | version | notes |
| -------------: | -------------:| -----:| --- |
| Bundle | - | - | planned for 1.4.0
| Chord | - | - | planned for 1.4.0
| Cluster | Complete | 1.2.0 | 
| Force | - | - | planned for 1.4.0
| Hierarchy | Complete | 1.2.0 | 
| Histogram | - | - | planned for 1.4.0
| Pack | - | - | planned for 1.4.0
| Partition | - | - | planned for 1.4.0
| Pie | - | - | planned for 1.4.0
| Stack | - | - | planned for 1.4.0
| Tree | Complete | 1.2.0 | 
| Treemap | - | - | planned for 1.4.0


#####TIME
| API | status | version | notes |
| -------------: | -------------:| -----:| --- |
| Time Formatting | Complete | 1.0.0 |
| Time Scales | Complete | 1.0.0 |
| Time Intervals | Complete | 1.0.0 |

#####GEOGRAPHY
| API | status | version | notes |
| -------------: | -------------:| -----:| --- |
| Paths | - | - | planned for 1.5.0 |  
| Projections | - | - | planned for 1.5.0 | 
| Streams | - | - | planned for 1.5.0  | 

#####GEOMETRY
| API | status | version | notes |
| -------------: | -------------:| -----:| --- |
| Quadtree| Complete | 1.0.0
| Polygon| Complete | 1.0.0
| Hull | Complete | 1.0.0
| Voronoi | Partial | 1.1.0 

#####BEHAVIORS
| API | status | version | notes |
| -------------: | -------------:| -----:| --- |
| Drag | Complete | 1.0.0
| Zoom | Complete | 1.0.0



##Version mapping

| GWT-D3 version | d3.js version | GWT-D3 release date  |
| -------------: | -------------:| -----:|
| [1.3.0](http://central.maven.org/maven2/com/github/gwtd3/gwt-d3-api/1.3.0/)          | 3.5.11        | 2017 01 09  |
| [1.2.0](http://central.maven.org/maven2/com/github/gwtd3/gwt-d3-api/1.2.0/)          | 3.5.11        | 2016 01 30  |
| [1.1.1](http://central.maven.org/maven2/com/github/gwtd3/gwt-d3-api/1.1.1/)          | 3.5.3        | 2015 02 06  |
| [1.1.0](http://central.maven.org/maven2/com/github/gwtd3/gwt-d3-api/1.1.0/)          | 3.5.3        | 2015 02 03  |
| [1.0.1](http://central.maven.org/maven2/com/github/gwtd3/gwt-d3-api/1.0.1/)          | 3.3.10        | 2013 12 07  |
| [1.0.0](http://central.maven.org/maven2/com/github/gwtd3/gwt-d3-api/1.0.0/)          | 3.3.6         | 2013 09 30  |
| <a href="http://repository-gwt-d3.forge.cloudbees.com/release/com/github/gwtd3/gwt-d3-api/0.0.16/">0.0.16</a>          | 3.3.6         | 2013 09 29 |
| 0.0.15          | 3.1.10         | 2013 07 29 |
| 0.0.14          | 3.1.10         | 2013 07 24 |
| 0.0.13          | 3.1.10         | 2013 06 13 |
| 0.0.12          | 3.1.10         | 2013 06 01 |


##Debugging d3.js 

You may be interested in debugging d3.js scripts when using super dev mode. 
If so, just add to your gwt application module file the following configuration:

```xml
  <set-property name="d3.pretty" value="true"/>
```

##License

See [LICENSE](https://github.com/gwtd3/gwt-d3/blob/master/LICENSE) file

