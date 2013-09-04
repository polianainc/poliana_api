set fs.s3n.awsAccessKeyId=AKIAI27OS66EVU7XE57A;
set fs.s3n.awsSecretAccessKey=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;

add jar csv-serde-1.1.2.jar;

CREATE EXTERNAL TABLE candidate_ids ( 
    recipient_ext_id STRING,
    crp_name STRING,
    party STRING,
    dist_id_run_for STRING,
    fec_cand_id STRING
)
row format serde 'com.bizo.hive.serde.csv.CSVSerde'
LOCATION 's3n://polianaprod/metadata/candidate_ids/'; 