CREATE EXTERNAL TABLE campaign_finance.pac_to_candidate_contributions_external (
    cycle STRING,
    fec_rec_no STRING,
    pac_id STRING,
    cid STRING,
    amount FLOAT,
    dates STRING,
    real_code STRING,
    type STRING,
    di STRING,
    fec_camd_id STRING
)
row format serde 'com.bizo.hive.serde.csv.CSVSerde'
 with serdeproperties (
   "separatorChar" = ",",
   "quoteChar"     = "|"
 )
LOCATION 's3n://polianatest/full/contributions/crp/pacs/';