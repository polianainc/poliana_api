set fs.s3n.awsAccessKeyId=AKIAI27OS66EVU7XE57A;
set fs.s3n.awsSecretAccessKey=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;

add jar /usr/local/csv-serde-1.1.2.jar;
add jar /usr/local/hive-serde-1.0.jar;

DROP TABLE IF EXISTS contributions;
DROP TABLE IF EXISTS contributions_test;
DROP TABLE IF EXISTS votes;
DROP TABLE IF EXISTS politicians;