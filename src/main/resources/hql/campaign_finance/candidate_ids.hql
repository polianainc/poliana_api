CREATE EXTERNAL TABLE campaign_finance.candidate_ids (
    recipient_ext_id STRING,
    crp_name STRING,
    party STRING,
    dist_id_run_for STRING,
    fec_cand_id STRING
)
row format serde 'com.bizo.hive.serde.csv.CSVSerde'
LOCATION 's3n://polianatest/full/metadata/candidate_ids/'; 