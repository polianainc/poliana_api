set fs.s3n.awsAccessKeyId=AKIAI27OS66EVU7XE57A;
set fs.s3n.awsSecretAccessKey=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;

CREATE EXTERNAL TABLE politician_metadata_json ( 
    website STRING,
    first_name STRING,
    last_name STRING,
    state_name STRING,
    chamber STRING,
    in_office STRING,
    twitter_id STRING,
    bioguide_id STRING,
    crp_id STRING,
    party STRING
)
ROW FORMAT SERDE 'com.proofpoint.hive.serde.JsonSerde'
WITH SERDEPROPERTIES ('errors.ignore' = 'true')
LOCATION 's3n://polianatest/sample/metadata/pols_json/';

