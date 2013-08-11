set fs.s3n.awsAccessKeyId=AKIAI27OS66EVU7XE57A;
set fs.s3n.awsSecretAccessKey=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;

add jar csv-serde-1.1.2.jar;

CREATE TABLE bioguides_contributions ( 

bioguide_id STRING,
contributor_ext_id STRING,
amount DOUBLE,
dates STRING
)

ROW FORMAT DELIMITED FIELDS TERMINATED BY '\t'
LOCATION '/home/hadoop/test_tables/';