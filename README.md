Norvig Award Examples
=====================

This is a branch of the original [common crawl examples](https://github.com/commoncrawl/commoncrawl-examples),
adapted to be used as a starting point for your entry to the [Norvig ... Award](http://www.sara.nl)

Examples
--------

### [Example MapReduce code](https://github.com/norvigaward/commoncrawl-examples/tree/master/src/java/org/commoncrawl/examples)

* [org.commoncrawl.examples.ExampleArcMicroformat](https://github.com/norvigaward/commoncrawl-examples/blob/master/src/java/org/commoncrawl/examples/ExampleArcMicroformat.java) - An example showing how to analyze the Common Crawl ARC web content files.
* [org.commoncrawl.examples.ExampleMetadataDomainPageCount](https://github.com/norvigaward/commoncrawl-examples/blob/master/src/java/org/commoncrawl/examples/ExampleMetadataDomainPageCount.java) - An example showing how to use the Common Crawl 'metadata' files to quickly gather high level information about the corpus' content.
* [org.commoncrawl.examples.ExampleMetadataStats](https://github.com/norvigaward/commoncrawl-examples/blob/master/src/java/org/commoncrawl/examples/ExampleMetadataStats.java) - An example showing how to use the Common Crawl 'metadata' files to quickly gather high level information about the corpus' content.
* [org.commoncrawl.examples.ExampleTextWordCount](https://github.com/norvigaward/commoncrawl-examples/blob/master/src/java/org/commoncrawl/examples/ExampleTextWordCount.java) - An example showing how to use the Common Crawl 'textData' files to efficiently work with Common Crawl corpus text content.

### Example Pig script

* [example.pig](https://github.com/norvigaward/commoncrawl-examples/blob/master/example.pig) - An example counting the occurrences of HTTP status codes

Running the examples
--------------------

### Prerequisites

You need to have the following software installed:

* [JDK 6](http://www.oracle.com/technetwork/java/index.html)
* [Git](http://git-scm.com/)
* [Ant](http://ant.apache.org/)
* [Apache Hadoop 0.20.2](http://hadoop.apache.org/)
* [Apache Pig](http://pig.apache.org/)

### Step 1: check out the code

    $ git clone https://github.com/norvigaward/commoncrawl-examples.git

### Step 2: 

Using the Common Crawl ARC files in MapReduce and Pig
-----------------------------------------------------

These examples come with an InputFormat for MapReduce and a Loader for Pig:

* [ArcInputFormat, ArcRecordReader, and ArcRecord](https://github.com/norvigaward/commoncrawl-examples/tree/master/src/java/org/commoncrawl/hadoop/mapred)
* [ArcLoader](https://github.com/norvigaward/commoncrawl-examples/blob/master/src/java/org/commoncrawl/pig/ArcLoader.java)

The above examples should show you how to load the Common Crawl ARC files using these classes.