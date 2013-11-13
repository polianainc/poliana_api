CREATE EXTERNAL TABLE entities.candidate_ids_external (
    recipient_ext_id STRING,
    crp_name STRING,
    party STRING,
    dist_id_run_for STRING,
    fec_cand_id STRING
)
ROW FORMAT SERDE 'com.bizo.hive.serde.csv.CSVSerde'
LOCATION 's3n://polianaprod/entities/candidate_ids/';

CREATE TABLE entities.candidate_ids (
    recipient_ext_id STRING,
    crp_name STRING,
    party STRING,
    dist_id_run_for STRING,
    fec_cand_id STRING
)
STORED AS SEQUENCEFILE;

CREATE EXTERNAL TABLE entities.recip_id_to_bioguide_ids (
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
ROW FORMAT SERDE 'com.bizo.hive.serde.csv.CSVSerde'
LOCATION 's3n://polianaprod/entities/recip_ids_to_bioguide/';

CREATE TABLE entities.recip_id_to_bioguide_ids (
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
);