#gwt-d3: the GWT wrapper around d3.js


This library provides access to the [d3.js API](http://d3js.org/) to the Java and GWT community.
It allows GWT developers to use d3.js library in their project.

Goals of the library:
- provide access to relevant API of [d3.js API](http://d3js.org/)
- enrich the API to ease Java programming style of d3 (turns JS array into List, string constants to Enum, ...)
- make a bridge between GWT Widget API and d3 

Have a look at the 
<a href="http://gwt-d3.appspot.com/">demo</a>.

 
<img src="http://gwtd3.github.io/gwt-d3/images/demo_chorddiagram.png" width="355px">
&nbsp;
<img src="http://gwtd3.github.io/gwt-d3/images/demo_lorenzsystem.png" width="355px"> 

##Getting started

**1. Configure a project**

**Using Maven**

Configure the pom.xml file to add the dependency to gwt-d3-api module.
```xml
<dependencies>
 ...
 <dependency>
 	<groupId>com.github.gwtd3</groupId>
 	<artifactId>gwt-d3-api</artifactId>
 	<version>1.0.0</version>
 	<scope>provided</scope>
 </dependency>
 ...
```

Note: these JARs don't contain any server-side code, so you don't need to package them into your webapp.

**Without Maven**

Download [gwt-d3-js-3.3.6.jar](http://central.maven.org/maven2/com/github/gwtd3/gwt-d3-js/3.3.6/gwt-d3-js-3.3.6.jar),
[gwt-d3-api-1.0.0.jar](http://central.maven.org/maven2/com/github/gwtd3/gwt-d3-api/1.0.0/gwt-d3-api-1.0.0.jar).
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

- The best way to learn D3 is to read the <a href="https://github.com/mbostock/d3/wiki/Tutorials">d3.js tutorials<a/> 
- Then you may want to check the examples gallery from <a href="https://github.com/mbostock/d3/wiki/Gallery">Mike Bostock website</a>
- You may also be interested in forking the gwt-d3 repository to see the demo source code
- Read the <a href="http://gwtd3.github.io/gwt-d3/apidocs/">Javadocs</a>
- Look at the <a href="http://gwt-d3.appspot.com/">demo</a>.





##Scope and coverage

Currently (30th of sept 2013), version 1.0.0 of gwt-d3 depends on the version 3.3.6 of d3.js codebase.
However, not all the API is covered. Please also have a look to the <a href="https://github.com/gwtd3/gwt-d3/issues?milestone=&page=1&state=open">issues</a>.

#####CORE
| API | status | version | notes |
| -------------: | -------------:| -----:| --- |
| Selections | Complete | 1.0.0 | |
| Transitions  | Complete | 1.0.0  | |
| Working with Arrays  | Partial | 1.0.0  | |
| Math  | Complete | 1.0.0  | |
| String Formatting | Complete | 1.0.0  | |
| Colors | Complete | 1.0.0  | |
| Loading External Resources | - | - | unplanned
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
| line.radial | - | - | planned for 1.1.0
| area | - | - | planned for 1.1.0
| area.radial | - | - | planned for 1.1.0
| arc | - | - | planned for 1.1.0
| chord | - | - | planned for 1.1.0
| diagonal | - | - | planned for 1.1.0
| diagonal.radial | - | - | planned for 1.1.0
| Symbol| Complete | 1.0.0
| **Axes** | Complete | 1.0.0
| **Controls** | Partial | - | planned for 1.1.0

#####LAYOUTS
| API | status | version | notes |
| -------------: | -------------:| -----:| --- |
| Bundle | - | - | planned for 1.2.0
| Chord | - | - | planned for 1.2.0
| Cluster | - | - | planned for 1.2.0
| Force | - | - | planned for 1.2.0
| Hierarchy | - | - | planned for 1.2.0
| Histogram | - | - | planned for 1.2.0
| Pack | - | - | planned for 1.2.0
| Partition | - | - | planned for 1.2.0
| Pie | - | - | planned for 1.2.0
| Stack | - | - | planned for 1.2.0
| Tree | - | - | planned for 1.2.0
| Treemap | - | - | planned for 1.2.0


#####TIME
| API | status | version | notes |
| -------------: | -------------:| -----:| --- |
| Time Formatting | Complete | 1.0.0 |
| Time Scales | Complete | 1.0.0 |
| Time Intervals | Complete | 1.0.0 |

#####GEOGRAPHY
| API | status | version | notes |
| -------------: | -------------:| -----:| --- |
| Paths | - | - | planned for 1.3.0 |  
| Projections | - | -  planned for 1.3.0 | 
| Streams | - | - | planned for 1.3.0 | | 

#####GEOMETRY
| API | status | version | notes |
| -------------: | -------------:| -----:| --- |
| Quadtree| Complete | 1.0.0
| Polygon| Complete | 1.0.0
| Hull | Complete | 1.0.0
| Voronoi | - | - | planned for 1.1.0 |

#####BEHAVIORS
| API | status | version | notes |
| -------------: | -------------:| -----:| --- |
| Drag | Complete | 1.0.0
| Zoom | Complete | 1.0.0



##Version mapping

| GWT-D3 version | d3.js version | GWT-D3 release date  |
| -------------: | -------------:| -----:|
| 1.0.0 (preparing)          | 3.3.6         | 2013 09 30  |
| <a href="http://repository-gwt-d3.forge.cloudbees.com/release/com/github/gwtd3/gwt-d3-api/0.0.16/">0.0.16</a>          | 3.3.6         | 2013 09 29 |
| 0.0.15          | 3.1.10         | 2013 07 29 |
| 0.0.14          | 3.1.10         | 2013 07 24 |
| 0.0.13          | 3.1.10         | 2013 06 13 |
| 0.0.12          | 3.1.10         | 2013 06 01 |




Continuous integration on <a href="https://gwt-d3.ci.cloudbees.com/job/CI%20of%20gwt-d3/">Cloudbees</a>:
<a href='https://gwt-d3.ci.cloudbees.com/job/CI%20of%20gwt-d3/'><img src='https://gwt-d3.ci.cloudbees.com/buildStatus/icon?job=CI of gwt-d3'></a>
