register /Users/evertlammerts/workspace/commoncrawl-examples/lib/*.jar;                                                                                      
register /Users/evertlammerts/opt/ccexamples.jar;
a = LOAD '/data/public/common-crawl/parse-output/segment/1346823845675/1346864466526_10.arc.gz' USING org.commoncrawl.pig.ArcLoader() as (date, length, type, statuscode, ipaddress, url, html, payload);
words = foreach a generate flatten(statuscode) as codes;                                                                                                                  
grpd = group words by codes;                                                                                                                                              
cntd = foreach grpd generate group, COUNT(words);                                                                                                                         
dump cntd;
