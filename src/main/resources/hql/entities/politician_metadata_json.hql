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
LOCATION 's3n://polianatest/full/metadata/pols_json/';

