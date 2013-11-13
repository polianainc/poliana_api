CREATE DATABASE entities_external;


CREATE EXTERNAL TABLE IF NOT EXISTS entities_external.candidate_ids(
    recipient_ext_id STRING,
    crp_name STRING,
    party STRING,
    dist_id_run_for STRING,
    fec_cand_id STRING
)
ROW FORMAT SERDE 'com.bizo.hive.serde.csv.CSVSerde'
LOCATION 's3n://polianaprod/entities/candidate_contribution_ids/'; 

CREATE EXTERNAL TABLE IF NOT EXISTS entities_external.congressional_committee_ids( 
    code STRING,
    cmte_name STRING
)
row format serde 'com.bizo.hive.serde.csv.CSVSerde'
LOCATION 's3n://polianaprod/entities/congressional_committee_ids/'; 

--SCHEMA BROKEN!!! TODO
CREATE EXTERNAL TABLE IF NOT EXISTS entities_external.current_committee_membership_json(
    committee  MAP<
        STRING,
        STRING
    >
)
ROW FORMAT SERDE 'com.proofpoint.hive.serde.JsonSerde'
 WITH SERDEPROPERTIES ('errors.ignore' = 'true')
LOCATION 's3n://polianaprod/entities/current_committee_members/';

CREATE EXTERNAL TABLE IF NOT EXISTS entities_external.current_committees_nested(
    rss_url STRING,
    name STRING,
    url STRING,
    house_committee_id STRING,
    phone STRING,
    subcommittees ARRAY<STRUCT<
        phone: STRING,
        thomas_id: STRING,
        name: STRING,
        address: STRING
    >>,
    address STRING,
    type STRING,
    thomas_id STRING
)
ROW FORMAT SERDE 'com.proofpoint.hive.serde.JsonSerde'
 WITH SERDEPROPERTIES ('errors.ignore' = 'true')
LOCATION 's3n://polianaprod/entities/current_committees/';

CREATE EXTERNAL TABLE IF NOT EXISTS entities_external.legislators(
    first_name STRING,
    last_name STRING,
    official_full STRING,
    party STRING,
    thomas_id STRING,
    bioguide_id STRING,
    opensecrets_id STRING,
    fec_id STRING,
    votesmart_id STRING,
    ballotpedia STRING,
    lis_id STRING,
    wikipedia_id STRING,
    govtrack_id STRING,
    maplight_id STRING,
    icpsr_id STRING,
    cspan_id STRING,
    house_history_id STRING,
    washington_post_id STRING,
    gender STRING,
    birthday INT,
    religion STRING
)
LOCATION 's3n://polianaprod/entities/legislators_flat/';

CREATE EXTERNAL TABLE IF NOT EXISTS entities_external.legislators_flat_terms (
    first_name STRING,
    last_name STRING,
    official_full STRING,
    party STRING,
    thomas_id STRING,
    bioguide_id STRING,
    opensecrets_id STRING,
    fec_id STRING,
    votesmart_id STRING,
    ballotpedia STRING,
    lis_id STRING,
    wikipedia_id STRING,
    govtrack_id STRING,
    maplight_id STRING,
    icpsr_id STRING,
    cspan_id STRING,
    house_history_id STRING,
    washington_post_id STRING,
    gender STRING,
    birthday STRING,
    religion STRING,
    term_start INT,
    term_end INT,
    term_state STRING,
    term_type STRING,
    district STRING,
    term_state_rank STRING
)
LOCATION 's3n://polianaprod/entities/legislators_flat_terms/';

CREATE EXTERNAL TABLE IF NOT EXISTS entities_external.senate_terms LIKE entities_external.legislators_flat_terms
 LOCATION 's3n://polianaprod/entities/senate_terms/';

CREATE EXTERNAL TABLE IF NOT EXISTS entities_external.house_terms LIKE entities_external.legislators_flat_terms
 LOCATION 's3n://polianaprod/entities/house_terms/';