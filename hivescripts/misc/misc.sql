--Set amazon s3 credentials
set fs.s3n.awsAccessKeyId=AKIAI27OS66EVU7XE57A;
set fs.s3n.awsSecretAccessKey=8o8zba04hd7o+vXA591stiamJCFut4c4cXENm5cE;

--Another table example with Arrays
CREATE EXTERNAL TABLE contributor_totals (
contributor_ext_id STRING,
contributor_names ARRAY<STRING>,
organization_name ARRAY<STRING>,
contributor_employer ARRAY<STRING>,
recipient_ext_id ARRAY<STRING>,
amount DOUBLE
)
ROW FORMAT DELIMITED FIELDS TERMINATED BY '\t'
LOCATION 's3n://contributiondata/contributor_totals/';


--Select contribution information grouped by recipient ids
SELECT recipient_ext_id, collect_set(contributor_name),
collect_set(organization_name), collect_set(contributor_employer),
collect_set(contributor_ext_id), sum(amount), collect_set(cycle)
FROM contributions WHERE cycle = 2012 
GROUP BY recipient_ext_id;


-- Output query results to .tsv
hive -e 'SELECT * FROM test_table' > test_info.tsv


--Specify a date range
select * from test_table where 
dates >= '2012-02-01' AND dates <= '2012-02-03';
 




--BLAH--


hive -e 'select * from contributor_totals order by amount desc;' > contributorByAmount2008.tsv


select * from contributions where 
unix_timestamp(dates) >= unix_timestamp('2008-02-01') AND 
unix_timestamp (dates) <= unix_timestamp('2008-02-03');

select * from test_table where 
unix_timestamp(dates + ' 00:00:00','yyyy-MM-dd') >= unix_timestamp('2012-01-01 00:00:00','yyyy-MM-dd') AND 
unix_timestamp (dates + ' 00:00:00','yyyy-MM-dd') <= unix_timestamp('2012-03-03  00:00:00','yyyy-MM-dd');

CREATE TABLE bill_contributions (
bill_id STRING,
begin_date STRING, 
end_date STRING,
recipient_ext_id STRING,
transaction_ids ARRAY<STRING>
);

INSERT OVERWRITE TABLE bill_contributions 
SELECT 'bill_id', '2012-01-01', '2012-03-01', recipient_ext_id, collect_set(transaction_id) 
FROM test_table WHERE dates >= '2012-02-01' AND dates <= '2012-08-020' AND recipient_ext_id = 'C00432260'
GROUP BY recipient_ext_id;


CREATE EXTERNAL TABLE bills ( 
bill_id STRING,
short_title STRING,
official_title STRING,
result STRING,
introduced_on STRING,
session STRING,
sponsor_id STRING,
republican_yeas DOUBLE,
republican_nays DOUBLE,
democrat_yeas DOUBLE,
democrat_nays DBOULE,
yeas ARRAY<STRING>,
nays ARRAY<STRING>,
text STRING,
user_id BIGINT,
user_name STRING
)
ROW FORMAT SERDE "org.apache.hadoop.hive.contrib.serde2.JsonSerde" WITH SERDEPROPERTIES (
"msg_id"="$.id", "tstamp"="$.created_at", "text"="$.text", "user_id"="$.user.id", "user_name"="$.user.name"
)
LOCATION '/data/messages';

