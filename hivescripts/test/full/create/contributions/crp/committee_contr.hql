set fs.s3n.awsAccessKeyId=AKIAI27OS66EVU7XE57A;
set fs.s3n.awsSecretAccessKey=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;

add jar csv-serde-1.1.2.jar;

CREATE EXTERNAL TABLE committee_contr ( 
    cycle STRING,
    cmtel_id STRING,
    pac_short STRING,
    affiliate STRING,
    ui_torg STRING,
    recip_id STRING,
    recip_code STRING,
    fec_cand_id STRING,
    party STRING,
    prim_code STRING,
    source STRING,
    sensitive STRING,
    foreign TINYINT,
    active SMALLINT
)
row format serde 'com.bizo.hive.serde.csv.CSVSerde'
 with serdeproperties (
   "separatorChar" = ",",
   "quoteChar"     = "|"
  )
LOCATION 's3n://polianatest/full/contributions/crp/committees/';