Norvig ... Award Examples
=========================

This is a branch of the original [common crawl examples](https://github.com/commoncrawl/commoncrawl-examples),
adapted to be used as a starting point for your entry to the [Norvig ... Award](http://www.sara.nl)

Building the jar
----------------

**If you are a participant you can skip to step 3, since are probably running our VM (if you are, but you don't, then check the mail that confirms your approval)**

### Prerequisites

You need to have the following software installed:

* [JDK 6](http://www.oracle.com/technetwork/java/index.html)
* [Git](http://git-scm.com/)
* [Ant](http://ant.apache.org/)
* [Apache Hadoop 0.20.2](http://hadoop.apache.org/)
* [Apache Pig](http://pig.apache.org/)


### Step 1: check out the code

    $ git clone https://github.com/norvigaward/commoncrawl-examples.git


### Step 2: set path to Hadoop and Pig

Open `build.properties` and set `hadoop.path` and `pig.path` to the full path of respectively your Hadoop and Pig distributions.


### Step 3: build and package the examples

From within the commoncrawl-examples directory, run:

    $ ant


Examples
-----------------

### Example MapReduce code

See the code for all examples [here](https://github.com/norvigaward/commoncrawl-examples/tree/master/src/java/org/commoncrawl/examples)

All examples support the same arguments:
    org.commoncrawl.examples.Example*
                             -in <inputpath>
                             -out <outputpath>
                           [ -overwrite ]
                           [ -numreducers <number_of_reducers> ]
                           [ -conf <conffile> ]
                           [ -maxfiles <maxfiles> ]

Where:
`-in` - Point to the path of your input files. You can use globbing if your Hadoop distribution supports it.
`-out` - Point to the path to store the output files.
`-overwrite` - If output path exists, this switch will allow the example to overwrite the existing directory.
`-numreducers` - Set the maximum amount of reducers to run. Defaults to a single reducer.
`-conf` - Path to additional configuration.
`-maxfiles` - Maximum amount of files to process.

These examples are included:
* [org.commoncrawl.examples.ExampleArcMicroformat](https://github.com/norvigaward/commoncrawl-examples/blob/master/src/java/org/commoncrawl/examples/ExampleArcMicroformat.java) - An example showing how to analyze the Common Crawl ARC web content files.
* [org.commoncrawl.examples.ExampleMetadataDomainPageCount](https://github.com/norvigaward/commoncrawl-examples/blob/master/src/java/org/commoncrawl/examples/ExampleMetadataDomainPageCount.java) - An example showing how to use the Common Crawl 'metadata' files to quickly gather high level information about the corpus' content.
* [org.commoncrawl.examples.ExampleMetadataStats](https://github.com/norvigaward/commoncrawl-examples/blob/master/src/java/org/commoncrawl/examples/ExampleMetadataStats.java) - An example showing how to use the Common Crawl 'metadata' files to quickly gather high level information about the corpus' content.
* [org.commoncrawl.examples.ExampleTextWordCount](https://github.com/norvigaward/commoncrawl-examples/blob/master/src/java/org/commoncrawl/examples/ExampleTextWordCount.java) - An example showing how to use the Common Crawl 'textData' files to efficiently work with Common Crawl corpus text content.


### Example Pig script

* [example.pig](https://github.com/norvigaward/commoncrawl-examples/blob/master/example.pig) - An example counting the occurrences of HTTP status codes


Running the ExampleTextWordCount
--------------------------------

Open a shell, and run:

    $ hadoop jar dist/lib/commoncrawl-examples-1.0.1.jar org.commoncrawl.examples.ExampleTextWordCount 


Using the Common Crawl ARC files in MapReduce and Pig
-----------------------------------------------------

These examples come with an InputFormat for MapReduce and a Loader for Pig:

* [ArcInputFormat, ArcRecordReader, and ArcRecord](https://github.com/norvigaward/commoncrawl-examples/tree/master/src/java/org/commoncrawl/hadoop/mapred)
* [ArcLoader](https://github.com/norvigaward/commoncrawl-examples/blob/master/src/java/org/commoncrawl/pig/ArcLoader.java)

The above examples should show you how to load the Common Crawl ARC files using these classes.