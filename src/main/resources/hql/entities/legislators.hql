CREATE EXTERNAL TABLE entities.legislators_json_external (
    bio STRUCT<
        gender: STRING,
        birthday: STRING,
        religion: STRING
    >,
    terms ARRAY<STRUCT<
        start: STRING,
        state: STRING,
        term_end: STRING,
        district: STRING,
        party: STRING,
        type: STRING,
        state_rank: STRING,
        url: STRING,
        fax: STRING,
        office: STRING,
        class: STRING,
        phone: STRING,
        address: STRING,
        contact_form: STRING,
        rss_url: STRING
    >>,
    id STRUCT<
        thomas: STRING,
        opensecrets: STRING,
        fec: ARRAY<STRING>,
        votesmart: STRING,
        ballotpedia: STRING,
        lis: STRING,
        wikipedia: STRING,
        bioguide: STRING,
        govtrack: STRING,
        maplight:STRING,
        icpsr: STRING,
        cspan: STRING,
        house_history: STRING,
        washington_post: STRING
    >,
    name STRUCT<
        last: STRING,
        official_full: STRING,
        first: STRING
    >
)
ROW FORMAT SERDE 'com.proofpoint.hive.serde.JsonSerde'
 WITH SERDEPROPERTIES ('errors.ignore' = 'true')
LOCATION 's3n://polianatest/full/legislators/';

CREATE EXTERNAL TABLE entities.legislators_flat_terms_external (
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

CREATE EXTERNAL TABLE entities.senate_terms_external LIKE entities.legislators_flat_terms_external
 LOCATION 's3n://polianaprod/entities/senate_terms/';

CREATE EXTERNAL TABLE entities.house_terms_external LIKE entities.legislators_flat_terms_external
 LOCATION 's3n://polianaprod/entities/house_terms/';

nohup hive -e 'INSERT OVERWRITE TABLE entities.senate_terms_external SELECT * FROM entities.view_senate_terms;INSERT OVERWRITE TABLE entities.house_terms_external SELECT * FROM entities.view_house_terms;'
nohup hive -e 'INSERT OVERWRITE TABLE entities.house_terms_external SELECT * FROM entities.view_house_terms;'

CREATE TABLE entities.legislators (
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
    religion STRING
)
STORED AS SEQUENCEFILE;

CREATE EXTERNAL TABLE entities.legislators_external (
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

CREATE VIEW entities.view_senate_terms (
    first_name,
    last_name,
    official_full,
    party,
    thomas_id,
    bioguide_id,
    opensecrets_id,
    fec_id,
    votesmart_id,
    ballotpedia,
    lis_id,
    wikipedia_id,
    govtrack_id,
    maplight_id,
    icpsr_id,
    cspan_id,
    house_history_id,
    washington_post_id,
    gender,
    birthday,
    religion,
    term_start,
    term_end,
    term_state,
    term_type,
    district,
    term_state_rank
)
as SELECT * FROM entities.legislators_flat_terms_external WHERE term_type = 'sen';

CREATE VIEW entities.view_house_terms (
    first_name,
    last_name,
    official_full,
    party,
    thomas_id,
    bioguide_id,
    opensecrets_id,
    fec_id,
    votesmart_id,
    ballotpedia,
    lis_id,
    wikipedia_id,
    govtrack_id,
    maplight_id,
    icpsr_id,
    cspan_id,
    house_history_id,
    washington_post_id,
    gender,
    birthday,
    religion,
    term_start,
    term_end,
    term_state,
    term_type,
    district,
    term_state_rank
)
as SELECT * FROM entities.legislators_flat_terms_external WHERE term_type = 'rep';

CREATE VIEW entities.view_legislators (
    first_name,
    last_name,
    official_full,
    party,
    thomas_id,
    bioguide_id,
    opensecrets_id,
    fec_id,
    votesmart_id,
    ballotpedia,
    lis_id,
    wikipedia_id,
    govtrack_id,
    maplight_id,
    icpsr_id,
    cspan_id,
    house_history_id,
    washington_post_id,
    gender,
    birthday,
    religion
)
as SELECT DISTINCT
    name.first,
    name.last,
    name.official_full,
    terms[0].party,
    id.thomas,
    id.bioguide,
    id.opensecrets,
    id.fec,
    id.votesmart,
    id.ballotpedia,
    id.lis,
    id.wikipedia,
    id.govtrack,
    id.maplight,
    id.icpsr,
    id.cspan,
    id.house_history,
    id.washington_post,
    bio.gender,
    bio.birthday,
    bio.religion
FROM entities.legislators_json_external;


INSERT OVERWRITE TABLE entities.legislators_flat_terms_external SELECT * FROM entities.view_legislators_flat_terms;
INSERT OVERWRITE TABLE entities.legislators SELECT * FROM entities.view_legislators;