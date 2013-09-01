set fs.s3n.awsAccessKeyId=AKIAI27OS66EVU7XE57A;
set fs.s3n.awsSecretAccessKey=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;

add jar csv-serde-1.1.2.jar;

CREATE EXTERNAL TABLE bills_csv ( 
    bill_id STRING,
    sponsor_id STRING,
    result STRING,
    congress STRING,
    introduced_on STRING,
    bioguide_id STRING,
    vote STRING
)
row format serde 'com.bizo.hive.serde.csv.CSVSerde'
LOCATION 's3n://polianatest/sample/billsvotes/commadel/';