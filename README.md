# Norvig Web Data Science Award Examples
======================================

This is a branch of the original [common crawl examples][1], adapted to be used
as a starting point for your entry to the [Norvig Web Data Science Award][2].

[1]: https://github.com/commoncrawl/commoncrawl-examples
[2]: http://norvigaward.github.com 


Getting started
---------------

We recommend using the virtual machine image as development environment as
descriped on the [contest website][3].

[3]: http://norvigaward.github.com/getstarted.html


Overview of the examples
------------------------

### Example MapReduce code

See the code for all examples [on github][3].

[3]: https://github.com/norvigaward/commoncrawl-examples/tree/master/src/java/org/commoncrawl/examples

All examples support the same arguments:

    org.commoncrawl.examples.Example*
                             -in <inputpath>
                             -out <outputpath>
                           [ -overwrite ]
                           [ -numreducers <number_of_reducers> ]
                           [ -conf <conffile> ]
                           [ -maxfiles <maxfiles> ]

Where:

 * `-in` - Point to the path of your input files. You can use globbing if your
   Hadoop distribution supports it.
 * `-out` - Point to the path to store the output files.
 * `-overwrite` - If output path exists, this switch will allow the example to
   overwrite the existing directory.
 * `-numreducers` - Set the maximum amount of reducers to run. Defaults to a
   single reducer.
 * `-conf` - Path to additional configuration.
 * `-maxfiles` - Maximum amount of files to process.

These examples are included:

 * [org.commoncrawl.examples.ExampleArcMicroformat](https://github.com/norvigaward/commoncrawl-examples/blob/master/src/java/org/commoncrawl/examples/ExampleArcMicroformat.java)
   - An example showing how to analyze the Common Crawl ARC web content files.
 * [org.commoncrawl.examples.ExampleMetadataDomainPageCount](https://github.com/norvigaward/commoncrawl-examples/blob/master/src/java/org/commoncrawl/examples/ExampleMetadataDomainPageCount.java)
   - An example showing how to use the Common Crawl 'metadata' files to quickly
     gather high level information about the corpus' content.
 * [org.commoncrawl.examples.ExampleMetadataStats](https://github.com/norvigaward/commoncrawl-examples/blob/master/src/java/org/commoncrawl/examples/ExampleMetadataStats.java)
   - An example showing how to use the Common Crawl 'metadata' files to quickly
     gather high level information about the corpus' content.
 * [org.commoncrawl.examples.ExampleTextWordCount](https://github.com/norvigaward/commoncrawl-examples/blob/master/src/java/org/commoncrawl/examples/ExampleTextWordCount.java)
   - An example showing how to use the Common Crawl 'textData' files to
   efficiently work with Common Crawl corpus text content.


### Build and package the examples

From within the commoncrawl-examples directory, run:

    $ ant


### Running the MapReduce examples

To run the an example on maximally 5 input files, open a shell and run:

    $ hadoop jar dist/lib/commoncrawl-examples-1.0.1.jar [EXAMPLECLASS] -in [INPUT] -out [OUTPUT] -maxfiles 5

For org.commoncrawl.examples.ExampleMetadataStats that would be

    $ hadoop jar dist/lib/commoncrawl-examples-1.0.1.jar org.commoncrawl.examples.ExampleMetadataStats -in [INPUT] -out [OUTPUT] -maxfiles 5

You can use this same command for each included example.

### Example Pig script

 * [example.pig](https://github.com/norvigaward/commoncrawl-examples/blob/master/example.pig) - An example counting the occurrences of HTTP status codes


Using the Common Crawl ARC files in MapReduce and Pig
-----------------------------------------------------

These examples come with an InputFormat for MapReduce and a Loader for Pig:

 * [ArcInputFormat, ArcRecordReader, and ArcRecord](https://github.com/norvigaward/commoncrawl-examples/tree/master/src/java/org/commoncrawl/hadoop/mapred)
 * [ArcLoader](https://github.com/norvigaward/commoncrawl-examples/blob/master/src/java/org/commoncrawl/pig/ArcLoader.java)

The above examples should show you how to load the Common Crawl ARC files using
these classes.
