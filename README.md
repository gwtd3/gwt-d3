gwt-d3 GWT wrapper around d3.js.
======

This library provides access to the [d3.js API](http://d3js.org/) to the Java and GWT community.
It allows GWT developers to use d3.js library in their project.

Goals of the library:
- provide access to relevant API of [d3.js API](http://d3js.org/)
- enrich the API to ease Java programming style of d3 (turns JS array into List, string constants to Enum, ...)
- make a bridge between GWT Widget API and d3 

It is still in a developpement phase. We are paying efforts on covering almost 100% of relevant d3 API.
You should not use it for production purpose since it's still experimental and may change unexpectedly.

You can see a demo with the test cases (for API coverage) and some exemples:
http://gwt-d3.appspot.com/

This is an ugly app but still demonstrate the potential of (gwt-)d3 


Roadmap:

- 1.0.0 (end of september 2013): first release  (details of the coverage to come soon)
- 1.1.0 : complete the SVG Shapes, SVG Controls and Geometry API
- 1.2.0 : complete the Layout API
- 1.3.0 : geometry API
- 1.4.0 : geography API
- 1.5.0 : non essentials API
- 
~~1.0.0 (planned by the end of august)~~


Continuous integration on <a href="https://gwt-d3.ci.cloudbees.com/job/CI%20of%20gwt-d3/">Cloudbees</a>:
<a href='https://gwt-d3.ci.cloudbees.com/job/CI%20of%20gwt-d3/'><img src='https://gwt-d3.ci.cloudbees.com/buildStatus/icon?job=CI of gwt-d3'></a>

