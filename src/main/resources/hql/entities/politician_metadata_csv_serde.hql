set fs.s3n.awsAccessKeyId=AKIAI27OS66EVU7XE57A;
set fs.s3n.awsSecretAccessKey=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;

add jar csv-serde-1.1.2.jar;

CREATE EXTERNAL TABLE politicians_metadata ( 
    bioguide_id STRING,
    recipient_ext_id STRING,
    chamber STRING,
    state_name STRING,
    first_name STRING,
    last_name STRING,
    party STRING,
    in_office STRING,
    website STRING,
    contact_form STRING
)
ROW FORMAT DELIMITED FIELDS TERMINATED BY ','
LOCATION 's3n://polianatest/full/metadata/pols_csv/';

