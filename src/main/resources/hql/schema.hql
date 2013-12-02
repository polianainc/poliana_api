CREATE DATABASE campaign_finance;

CREATE EXTERNAL TABLE campaign_finance.candidate_contributions_crp_external (
    cycle STRING,
    fec_candi_id STRING,
    cid STRING,
    name_party STRING,
    party STRING,
    dist_id_run_for STRING,
    dist_id_curr STRING,
    curr_cand STRING,
    cycle_cand STRING,
    crp_type STRING,
    recip_code STRING,
    no_pacs STRING
)
ROW FORMAT SERDE 'com.bizo.hive.serde.csv.CSVSerde'
 WITH SERDEPROPERTIES (
   "separatorChar" = ",",
   "quoteChar"     = "|"
  )
LOCATION 's3n://polianaprod/campaign_finance/candidate_contributions_crp/';

CREATE EXTERNAL TABLE campaign_finance.committee_contributions_crp_external (
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
ROW FORMAT SERDE 'com.bizo.hive.serde.csv.CSVSerde'
 WITH SERDEPROPERTIES (
   "separatorChar" = ",",
   "quoteChar"     = "|"
  )
LOCATION 's3n://polianaprod/campaign_finance/committee_contributions_crp/';

CREATE EXTERNAL TABLE campaign_finance.fec_contributions_external (
    id STRING,
    import_reference_id STRING,
    cycle STRING,
    transaction_namespace STRING,
    transaction_id STRING,
    transaction_type STRING,
    filing_id STRING,
    is_amendment STRING,
    amount DOUBLE,
    dates STRING,
    contributor_name STRING,
    contributor_ext_id STRING,
    contributorType STRING,
    contributor_occupation STRING,
    contributor_employer STRING,
    contributor_gender STRING,
    contributor_address STRING,
    contributor_city STRING,
    contributor_state STRING,
    contributor_zipcode STRING,
    contributor_category STRING,
    organization_name STRING,
    organization_ext_id STRING,
    parent_organization_name STRING,
    parent_organization_ext_id STRING,
    recipient_name STRING,
    recipient_ext_id STRING,
    recipient_party STRING,
    recipient_type STRING,
    recipient_state STRING,
    recipient_state_held STRING,
    recipient_category STRING,
    committee_name STRING,
    committee_ext_id STRING,
    committee_party STRING,
    candidacy_status STRING,
    district STRING,
    district_held STRING,
    seat STRING,
    seat_held STRING,
    seat_status STRING,
    seat_result STRING
)
ROW FORMAT SERDE 'com.bizo.hive.serde.csv.CSVSerde'
LOCATION 's3n://polianaprod/campaign_finance/contributions_fec/';

CREATE EXTERNAL TABLE campaign_finance.expenditures_external (
    cycle STRING,
    id STRING,
    trans_id STRING,
    crp_filer_id STRING,
    recip_code STRING,
    pac_short STRING,
    crp_recip_name STRING,
    exp_code STRING,
    amount FLOAT,
    dates STRING,
    city STRING,
    state STRING,
    zip STRING,
    cmtel_id_ef STRING,
    cand_id STRING,
    type STRING,
    descrip STRING,
    pg STRING,
    elec_other STRING,
    ent_type STRING,
    source STRING
)
ROW FORMAT SERDE 'com.bizo.hive.serde.csv.CSVSerde'
 WITH SERDEPROPERTIES (
   "separatorChar" = ",",
   "quoteChar"     = "|"
  )
LOCATION 's3n://polianaprod/campaign_finance/expenditures/';

CREATE EXTERNAL TABLE campaign_finance.individual_contributions_external (
    cycle STRING,
    fec_trans_id STRING,
    contrib_id STRING,
    contrib STRING,
    recip_id STRING,
    org_name STRING,
    ult_org STRING,
    real_code STRING,
    dates STRING,
    amount INT,
    street STRING,
    city STRING,
    state STRING,
    zip STRING,
    recip_code STRING,
    type STRING,
    cmtel_id STRING,
    other_id STRING,
    gender STRING,
    microfilm STRING,
    occupation STRING,
    employer STRING,
    source STRING
)
ROW FORMAT SERDE 'com.bizo.hive.serde.csv.CSVSerde'
 WITH SERDEPROPERTIES (
   "separatorChar" = ",",
   "quoteChar"     = "|"
  )
LOCATION 's3n://polianaprod/campaign_finance/individual_contributions_crp/';

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
ROW FORMAT SERDE 'com.bizo.hive.serde.csv.CSVSerde'
 WITH SERDEPROPERTIES (
   "separatorChar" = ",",
   "quoteChar"     = "|"
 )
LOCATION 's3n://polianaprod/campaign_finance/pac_to_cand_contributions_crp/';

CREATE EXTERNAL TABLE campaign_finance.pac_to_pac_contributions_external (
    cycle STRING,
    fec_rec_no STRING,
    file_rid STRING,
    donor_cmte STRING,
    contrib_lend_trans STRING,
    city STRING,
    state STRING,
    zip STRING,
    fec_occ_emp STRING,
    prim_code STRING,
    dates STRING,
    amount FLOAT,
    recip_id STRING,
    party STRING,
    other_id STRING,
    recip_code STRING,
    recip_prim_code STRING,
    amend STRING,
    report STRING,
    pg STRING,
    microfilm STRING,
    type STRING,
    real_code STRING,
    source STRING
)
ROW FORMAT SERDE 'com.bizo.hive.serde.csv.CSVSerde'
 WITH SERDEPROPERTIES (
   "separatorChar" = ",",
   "quoteChar"     = "|"
  )
LOCATION 's3n://polianaprod/campaign_finance/pac_to_pac_contributions_crp/';




CREATE DATABASE entities;


CREATE EXTERNAL TABLE entities.candidate_ids_external (
    recipient_ext_id STRING,
    crp_name STRING,
    party STRING,
    dist_id_run_for STRING,
    fec_cand_id STRING
)
ROW FORMAT SERDE 'com.bizo.hive.serde.csv.CSVSerde'
LOCATION 's3n://polianaprod/entities/candidate_contribution_ids/'; 

CREATE EXTERNAL TABLE entities.congressional_committee_ids_external ( 
    code STRING,
    cmte_name STRING
)
row format serde 'com.bizo.hive.serde.csv.CSVSerde'
LOCATION 's3n://polianaprod/entities/congressional_committee_ids/'; 

--SCHEMA BROKEN!!! TODO
CREATE EXTERNAL TABLE entities.current_committee_membership_json_external (
    committee  MAP<
        STRING,
        STRING
    >
)
ROW FORMAT SERDE 'com.proofpoint.hive.serde.JsonSerde'
 WITH SERDEPROPERTIES ('errors.ignore' = 'true')
LOCATION 's3n://polianaprod/entities/current_committee_members/';

CREATE EXTERNAL TABLE entities.current_committees_nested_external (
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



CREATE DATABASE bills;
USE bills;

CREATE EXTERNAL TABLE bills.amendments_json_external (
    actions ARRAY<STRUCT<
        acted_at: STRING,
        references: ARRAY<STRING>,
        test: STRING,
        type: STRING,
        how: STRING,
        result: STRING,
        vote_type: STRING,
        location: STRING
    >>,
    amendment_id STRING,
    amendment_type STRING,
    amends_amendment STRING,
    amends_bill STRUCT<
        bill_id: STRING,
        bill_type: STRING,
        congress: INT,
        number: INT
    >,
    amends_treaty STRING,
    chamber STRING,
    congress STRING,
    description STRING,
    house_number INT,
    introduced_at STRING,
    number INT,
    purpose STRING,
    sponsor STRUCT<
        district: STRING,
        name: STRING,
        state: STRING,
        thomas_id: STRING,
        title: STRING,
        type: STRING
    >,
    status STRING,
    status_at STRING,
    updated_at STRING
)
ROW FORMAT SERDE 'com.proofpoint.hive.serde.JsonSerde'
 WITH SERDEPROPERTIES ('errors.ignore' = 'true')
 LOCATION 's3n://polianaprod/legislation/amendments_json/';


 CREATE EXTERNAL TABLE bills.bill_meta_external (
    bill_id STRING,
    vote_id STRING,
    official_title STRING,
    popular_title STRING,
    short_title STRING,
    sponsor_name STRING,
    sponsor_state STRING,
    sponsor_id STRING,
    cosponsor_ids ARRAY<STRING>,
    top_subject STRING,
    subjects ARRAY<STRING>,
    summary STRING,
    introduced_at INT,
    house_passage_result STRING,
    house_passage_result_at INT,
    senate_cloture_result STRING,
    senate_cloture_result_at INT,
    senate_passage_result STRING,
    senate_passage_result_at INT,
    awaiting_signature BOOLEAN,
    enacted BOOLEAN,
    vetoed BOOLEAN,
    enacted_at INT,
    status STRING,
    status_at INT
)
PARTITIONED BY (congress INT, bill_type STRING)
STORED AS SEQUENCEFILE
LOCATION 's3n://polianaprod/legislation/bills_meta/';

ALTER TABLE bill_meta_embedded_external RECOVER PARTITIONS;

CREATE EXTERNAL TABLE bills.bill_meta_external (
    bill_id STRING,
    vote_id STRING,
    official_title STRING,
    popular_title STRING,
    short_title STRING,
    sponsor_name STRING,
    sponsor_state STRING,
    sponsor_id STRING,
    cosponsor_ids ARRAY<STRING>,
    top_subject STRING,
    subjects ARRAY<STRING>,
    summary STRING,
    introduced_at INT,
    house_passage_result STRING,
    house_passage_result_at INT,
    senate_cloture_result STRING,
    senate_cloture_result_at INT,
    senate_passage_result STRING,
    senate_passage_result_at INT,
    awaiting_signature BOOLEAN,
    enacted BOOLEAN,
    vetoed BOOLEAN,
    enacted_at INT,
    status STRING,
    status_at INT
)
PARTITIONED BY (congress INT, bill_type STRING)
STORED AS SEQUENCEFILE
LOCATION 's3n://polianaprod/legislation/bills_meta/';

CREATE EXTERNAL TABLE bills.bill_meta_embedded_external (
    bill_id STRING,
    vote_id STRING,
    official_title STRING,
    popular_title STRING,
    short_title STRING,
    sponsor_name STRING,
    sponsor_state STRING,
    sponsor_id STRING,
    cosponsor_ids STRING,
    top_subject STRING,
    subjects STRING,
    summary STRING,
    introduced_at INT,
    house_passage_result STRING,
    house_passage_result_at INT,
    senate_cloture_result STRING,
    senate_cloture_result_at INT,
    senate_passage_result STRING,
    senate_passage_result_at INT,
    awaiting_signature BOOLEAN,
    enacted BOOLEAN,
    vetoed BOOLEAN,
    enacted_at INT,
    status STRING,
    status_at INT
)
PARTITIONED BY (congress INT, bill_type STRING)
STORED AS SEQUENCEFILE
LOCATION 's3n://polianaprod/legislation/bills_meta/';

ALTER TABLE bill_meta_external RECOVER PARTITIONS;

CREATE EXTERNAL TABLE bills.votes_by_bill_embedded_external (
    vote_id STRING,
    chamber STRING,
    yea_total INT,
    nay_total INT,
    not_voting_total INT,
    present_total INT,
    yea_votes STRING,
    nay_votes STRING,
    not_voting STRING,
    present STRING
)
PARTITIONED BY (year INT, month INT)
STORED AS SEQUENCEFILE
LOCATION 's3n://polianaprod/legislation/votes_by_bill_embedded/';

ALTER TABLE votes_by_bill_embedded_external RECOVER PARTITIONS;

CREATE EXTERNAL TABLE bills.votes_flat_external (
    vote_id STRING,
    vote STRING,
    date STRING,
    display_name STRING,
    first_name STRING,
    id STRING,
    last_name STRING,
    party STRING,
    state STRING
)
PARTITIONED BY (year INT, month INT)
STORED AS SEQUENCEFILE
LOCATION 's3n://polianaprod/legislation/votes_flat/';

ALTER TABLE votes_flat_external RECOVER PARTITIONS;

