set fs.s3n.awsAccessKeyId=AKIAI27OS66EVU7XE57A;
set fs.s3n.awsSecretAccessKey=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;

add jar csv-serde-1.1.2.jar;

CREATE EXTERNAL TABLE industry_codes ( 
    cat_code STRING,
    cat_name STRING,
    cat_order STRING,
    industry STRING,
    sector STRING,
    sector_long STRING
)
row format serde 'com.bizo.hive.serde.csv.CSVSerde'
LOCATION 's3n://polianaprod/metadata/industry_codes/';