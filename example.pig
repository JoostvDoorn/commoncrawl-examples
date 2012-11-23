register /home/participant/git/commoncrawl-examples/lib/*.jar; 

register /home/participant/git/commoncrawl-examples/dist/lib/commoncrawl-examples-1.0.1.jar;

a = LOAD '/home/participant/data/*.arc.gz' USING org.commoncrawl.pig.ArcLoader() as (date, length, type, statuscode, ipaddress, url, html);

words = foreach a generate flatten(statuscode) as codes;

grpd = group words by codes; 

cntd = foreach grpd generate group, COUNT(words); 

dump cntd;
