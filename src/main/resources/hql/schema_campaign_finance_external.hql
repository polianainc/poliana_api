CREATE DATABASE campaign_finance_external;

CREATE EXTERNAL TABLE campaign_finance_external.candidate_contributions_crp(
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

CREATE EXTERNAL TABLE campaign_finance_external.committee_contributions_crp(
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

CREATE EXTERNAL TABLE campaign_finance_external.fec_contributions(
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

CREATE EXTERNAL TABLE campaign_finance_external.expenditures(
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

CREATE EXTERNAL TABLE campaign_finance_external.individual_contributions(
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

CREATE EXTERNAL TABLE campaign_finance_external.pac_to_candidate_contributions(
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

CREATE EXTERNAL TABLE campaign_finance_external.pac_to_pac_contributions(
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





