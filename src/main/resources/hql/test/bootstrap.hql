CREATE TABLE bills ( 
    bill_id STRING,
    sponsor_id STRING,
    result STRING,
    congress STRING,
    introduced_on STRING,
    bioguide_id STRING,
    vote STRING
)
row format serde 'com.bizo.hive.serde.csv.CSVSerde'
LOCATION '/Users/dagilmore/Dev/polianalytics/poliana_processing/src/main/resources/data/billsvotes/commadel/';

CREATE TABLE candidate_contr ( 
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
row format serde 'com.bizo.hive.serde.csv.CSVSerde'
 with serdeproperties (
   "separatorChar" = ",",
   "quoteChar"     = "|"
  )
LOCATION '/Users/dagilmore/Dev/polianalytics/poliana_processing/src/main/resources/data/contributions/crp/candidates/';

CREATE TABLE committee_contr ( 
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
LOCATION '/Users/dagilmore/Dev/polianalytics/poliana_processing/src/main/resources/data/contributions/crp/committees/';

CREATE TABLE individual_contr ( 
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
row format serde 'com.bizo.hive.serde.csv.CSVSerde'
 with serdeproperties (
   "separatorChar" = ",",
   "quoteChar"     = "|"
  )
LOCATION '/Users/dagilmore/Dev/polianalytics/poliana_processing/src/main/resources/data/contributions/crp/individuals/';

CREATE TABLE pac_to_candidate_contr ( 
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
LOCATION '/Users/dagilmore/Dev/polianalytics/poliana_processing/src/main/resources/data/contributions/crp/pacs/';

CREATE TABLE pac_to_pac_contr ( 
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
row format serde 'com.bizo.hive.serde.csv.CSVSerde'
 with serdeproperties (
   "separatorChar" = ",",
   "quoteChar"     = "|"
  )
LOCATION '/Users/dagilmore/Dev/polianalytics/poliana_processing/src/main/resources/data/contributions/crp/pac_other/';

CREATE TABLE fec_contributions (
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
LOCATION '/Users/dagilmore/Dev/polianalytics/poliana_processing/src/main/resources/data/contributions/sunlight/';

CREATE TABLE expenditures ( 
    cycle STRING,
    id STRING,
    trans_id STRING,
    crp_filer_id STRING,
    recip_code STRING,
    pac_short STRING,
    crp_recip_name STRING,
    exp_code STRING,
    amount DOUBLE,
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
row format serde 'com.bizo.hive.serde.csv.CSVSerde'
 with serdeproperties (
   "separatorChar" = ",",
   "quoteChar"     = "|"
  )
LOCATION '/Users/dagilmore/Dev/polianalytics/poliana_processing/src/main/resources/data/expenditures/';

CREATE TABLE politicians_metadata ( 
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
row format serde 'com.bizo.hive.serde.csv.CSVSerde'
 with serdeproperties (
   "separatorChar" = "\t"
  )
LOCATION '/Users/dagilmore/Dev/polianalytics/poliana_processing/src/main/resources/data/personmeta/pols_csv/';

CREATE TABLE industry_codes ( 
    cat_code STRING,
    cat_name STRING,
    cat_order STRING,
    industry STRING,
    sector STRING,
    sector_long STRING
)
row format serde 'com.bizo.hive.serde.csv.CSVSerde'
LOCATION '/Users/dagilmore/Dev/polianalytics/poliana_processing/src/main/resources/data/metadata/industry_codes/';

CREATE TABLE cong_cmte_codes ( 
    code STRING,
    cmte_name STRING
)
row format serde 'com.bizo.hive.serde.csv.CSVSerde'
LOCATION '/Users/dagilmore/Dev/polianalytics/poliana_processing/src/main/resources/data/metadata/cong_cmte_codes/';

CREATE TABLE candidate_ids ( 
    recipient_ext_id STRING,
    crp_name STRING,
    party STRING,
    dist_id_run_for STRING,
    fec_cand_id STRING
)
row format serde 'com.bizo.hive.serde.csv.CSVSerde'
LOCATION '/Users/dagilmore/Dev/polianalytics/poliana_processing/src/main/resources/data/metadata/candidate_ids/';