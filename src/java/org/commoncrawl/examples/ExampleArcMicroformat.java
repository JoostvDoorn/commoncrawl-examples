package org.commoncrawl.examples;

// Java classes
import java.io.IOException;
import java.net.URI;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.conf.Configured;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.fs.PathFilter;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapred.OutputCollector;
import org.apache.hadoop.mapred.Reporter;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;
import org.apache.hadoop.mapreduce.lib.output.TextOutputFormat;
import org.apache.hadoop.mapreduce.lib.reduce.LongSumReducer;
import org.apache.hadoop.util.Tool;
import org.apache.hadoop.util.ToolRunner;
import org.apache.log4j.Logger;
import org.commoncrawl.hadoop.mapred.ArcInputFormat;
import org.commoncrawl.hadoop.mapred.ArcRecord;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
// log4j classes
// Hadoop classes
// Common Crawl classes
// jsoup classes

/**
 * An example showing how to analyze the Common Crawl ARC web content files.
 * 
 * @author Chris Stephens <chris@commoncrawl.org>
 */
public class ExampleArcMicroformat
    extends    Configured
    implements Tool {

  private static final Logger LOG = Logger.getLogger(ExampleArcMicroformat.class);
  private Configuration conf;

  /**
   * Maps incoming web documents to a list of Microformat 'itemtype' tags.
   * Filters out any non-HTML pages.
   *
   * @author Chris Stephens <chris@commoncrawl.org>
   *
   * Inspired by:
   *
   * @author Manu Sporny 
   * @author Steve Salevan
   */
  public static class ExampleArcMicroformatMapper
      extends    Mapper<Text, ArcRecord, Text, LongWritable> {
 
    // create a counter group for Mapper-specific statistics
    private final String _counterGroup = "Custom Mapper Counters";

    public void map(Text key, ArcRecord value, OutputCollector<Text, LongWritable> output, Reporter reporter)
        throws IOException {

      try {

        if (!value.getContentType().contains("html")) {
          reporter.incrCounter(this._counterGroup, "Skipped - Not HTML", 1);
          return;
        }

        // just curious how many of each content type we've seen
        reporter.incrCounter(this._counterGroup, "Content Type - "+value.getContentType(), 1);

        // ensure sample instances have enough memory to parse HTML
        if (value.getContentLength() > (5 * 1024 * 1024)) {
          reporter.incrCounter(this._counterGroup, "Skipped - HTML Too Long", 1);
          return;
        }

        // Count all 'itemtype' attributes referencing 'schema.org'
        Document doc = value.getParsedHTML();

        if (doc == null) {
          reporter.incrCounter(this._counterGroup, "Skipped - Unable to Parse HTML", 1);
          return;
        }

        Elements mf = doc.select("[itemtype~=schema.org]");

        if (mf.size() > 0) {
          for (Element e : mf) {
            if (e.hasAttr("itemtype")) {
              output.collect(new Text(e.attr("itemtype").toLowerCase().trim()), new LongWritable(1));
            }
          }
        }
      }
      catch (Throwable e) {

        // occassionally Jsoup parser runs out of memory ...
        if (e.getClass().equals(OutOfMemoryError.class))
          System.gc();

        LOG.error("Caught Exception", e);
        reporter.incrCounter(this._counterGroup, "Skipped - Exception Thrown", 1);
      }
    }
  }

  /**
   * Hadoop FileSystem PathFilter for ARC files, allowing users to limit the
   * number of files processed.
   *
   * @author Chris Stephens <chris@commoncrawl.org>
   */
  public static class SampleFilter
      implements PathFilter {

    private static int count =         0;
    private static int max   = 999999999;

    public boolean accept(Path path) {

      if (!path.getName().endsWith(".arc.gz"))
        return false;

      SampleFilter.count++;

      if (SampleFilter.count > SampleFilter.max)
        return false;

      return true;
    }
  }

  /**
   * Implmentation of Tool.run() method, which builds and runs the Hadoop job.
   *
   * @param  args command line parameters, less common Hadoop job parameters stripped
   *              out and interpreted by the Tool class.  
   * @return      0 if the Hadoop job completes successfully, 1 if not. 
   */
  @Override
  public int run(String[] args)
      throws Exception {

    conf = getConf();
    String outputPath = null;
    String configFile = null;

    // Read the command line arguments.
    if (args.length <  1)
      throw new IllegalArgumentException("Example JAR must be passed an output path.");

    outputPath = args[0];

    if (args.length >= 2)
      configFile = args[1];

    // For this example, only look at a single ARC files.
    String inputPath   = "/data/public/common-crawl/parse-output/segment/1346823845675/1346864466526_10.arc.gz";
 
    // Switch to this if you'd like to look at all ARC files.  May take many minutes just to read the file listing.
  //String inputPath   = "s3n://aws-publicdatasets/common-crawl/parse-output/segment/*/*.arc.gz";

    // Read in any additional config parameters.
    if (configFile != null) {
      LOG.info("adding config parameters from '"+ configFile + "'");
      conf.addResource(configFile);
    }

    Job job = new Job(conf);
    
    job.setJarByClass(ExampleArcMicroformat.class);

    // Scan the provided input path for ARC files.
    LOG.info("setting input path to '"+ inputPath + "'");
    FileInputFormat.addInputPath(job, new Path(inputPath));
    FileInputFormat.setInputPathFilter(job, SampleFilter.class);

    // Delete the output path directory if it already exists.
    LOG.info("clearing the output path at '" + outputPath + "'");

    FileSystem fs = FileSystem.get(new URI(outputPath), conf);

    if (fs.exists(new Path(outputPath)))
      fs.delete(new Path(outputPath), true);

    // Set the path where final output 'part' files will be saved.
    LOG.info("setting output path to '" + outputPath + "'");
    FileOutputFormat.setOutputPath(job, new Path(outputPath));
    FileOutputFormat.setCompressOutput(job, false);

    // Set which InputFormat class to use.
    job.setInputFormatClass(ArcInputFormat.class);

    // Set which OutputFormat class to use.
    job.setOutputFormatClass(TextOutputFormat.class);

    // Set the output data types.
    job.setOutputKeyClass(Text.class);
    job.setOutputValueClass(LongWritable.class);

    // Set which Mapper and Reducer classes to use.
    job.setMapperClass(ExampleArcMicroformat.ExampleArcMicroformatMapper.class);
    job.setReducerClass(LongSumReducer.class);

    if (!job.waitForCompletion(true))
      return 1;
    else
      return 0;
  }

  /**
   * Main entry point that uses the {@link ToolRunner} class to run the example
   * Hadoop job.
   */
  public static void main(String[] args)
      throws Exception {
    int res = ToolRunner.run(new Configuration(), new ExampleArcMicroformat(), args);
    System.exit(res);
  }
}
