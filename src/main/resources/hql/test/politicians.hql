CREATE TABLE IF NOT EXISTS politicians (
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
ROW FORMAT DELIMITED FIELDS TERMINATED BY '\t'
LOCATION '/Users/dagilmore/Dev/polianalytics/poliana_processing/src/main/resources/data/politicians/';