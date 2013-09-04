set fs.s3n.awsAccessKeyId=AKIAI27OS66EVU7XE57A;
set fs.s3n.awsSecretAccessKey=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;

add jar csv-serde-1.1.2.jar;

CREATE EXTERNAL TABLE cong_cmte_codes ( 
    code STRING,
    cmte_name STRING
)
row format serde 'com.bizo.hive.serde.csv.CSVSerde'
LOCATION 's3n://polianaprod/metadata/cong_cmte_codes/'; 