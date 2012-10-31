package org.commoncrawl.examples;

import org.apache.hadoop.fs.Path;
import org.apache.hadoop.fs.PathFilter;

/**
 * Hadoop FileSystem PathFilter for TextData files, allowing users to limit the
 * number of files processed.
 *
 * @author Chris Stephens <chris@commoncrawl.org>
 */
public class SampleFilter implements PathFilter {

  private static int count =  0;
  private static long max   = -1;
  private static String prefx = "";
  
  public static void setPrefix(String prefix) {
    prefx = prefix;
  }
  
  protected static void setMax(long newmax) {
    max = newmax;
  }

  @Override
  public boolean accept(Path path) {

    if (!path.getName().startsWith(prefx))
      return false;

    count++;
    
    if (max < 0 || count > max)
      return false;

    return true;
  }
}