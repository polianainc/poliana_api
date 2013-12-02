CREATE DATABASE entities_external;


CREATE TABLE IF NOT EXISTS entities.candidate_ids(
    recipient_ext_id STRING,
    crp_name STRING,
    party STRING,
    dist_id_run_for STRING,
    fec_cand_id STRING
);

CREATE TABLE IF NOT EXISTS entities.congressional_committee_ids( 
    code STRING,
    cmte_name STRING
);

CREATE TABLE IF NOT EXISTS entities.current_committees_nested(
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
);

CREATE TABLE IF NOT EXISTS entities.legislators(
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
);

CREATE TABLE IF NOT EXISTS entities.legislators_flat_terms (
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
);

CREATE VIEW IF NOT EXISTS entities.view_legislators (
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
) as SELECT DISTINCT
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
FROM entities.legislators_flat_terms;

CREATE TABLE IF NOT EXISTS entities.senate_terms LIKE entities.legislators_flat_terms;

CREATE TABLE IF NOT EXISTS entities.house_terms LIKE entities.legislators_flat_terms;

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

INSERT OVERWRITE TABLE entities.candidate_ids SELECT * FROM entities_external.candidate_ids;
INSERT OVERWRITE TABLE entities.congressional_committee_ids SELECT * FROM entities_external.congressional_committee_ids;
INSERT OVERWRITE TABLE entities.legislators SELECT * FROM entities_external.legislators;
INSERT OVERWRITE TABLE entities.legislators_flat_terms SELECT * FROM entities_external.legislators_flat_terms;
INSERT OVERWRITE TABLE entities.senate_terms SELECT * FROM entities_external.senate_terms;
INSERT OVERWRITE TABLE entities.house_terms SELECT * FROM entities_external.house_terms;
INSERT OVERWRITE TABLE entities.recip_id_to_bioguide_ids SELECT * FROM entities_external.recip_id_to_bioguide_ids;
