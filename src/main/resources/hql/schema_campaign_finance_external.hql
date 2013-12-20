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



-- http://www.fec.gov/finance/disclosure/metadata/DataDictionaryCommitteeMaster.shtml
CREATE EXTERNAL TABLE IF NOT EXISTS campaign_finance_external.pac_committee_master (
    cmte_id STRING, -- Committee Identification
    cmte_nm STRING, -- Committee Name
    tres_nm STRING, -- Treasurer's Name
    cmte_st1 STRING, -- Street One
    cmte_st2 STRING, -- Street Two
    cmte_city STRING, -- City or Town
    cmte_st STRING, -- State
    cmte_zip STRING, -- Zip code
    cmte_dsgn STRING, -- Committee Designation
    cmte_tp STRING, -- Committee Type
    cmte_pty_affiliation STRING, -- Committee Party
    cmte_filing_freq STRING, -- Filing Frequency
    org_tp STRING, -- Interest Group Category
    connected_org_nm STRING, -- Connected Organization's Name
    cand_id STRING -- Candidate ID
)
ROW FORMAT SERDE 'com.bizo.hive.serde.csv.CSVSerde'
 WITH SERDEPROPERTIES (
   "separatorChar" = "|"
  )
LOCATION 's3n://polianaprod/campaign_finance/pacs/committee_master/data/';


-- http://www.fec.gov/finance/disclosure/metadata/DataDictionaryCandidateMaster.shtml
CREATE EXTERNAL TABLE IF NOT EXISTS campaign_finance_external.pac_candidate_master (
    cand_id STRING, -- Candidate Identification
    cand_name STRING, -- Candidate Name
    cand_pty_affiliation STRING, -- Party Affiliation
    cand_election_yr STRING, -- Year of Election
    cand_office_st STRING, -- Candidate State
    cand_office STRING, -- Candidate Office: H = House P = President S = Senate
    cand_office_district INT, -- Candidate District
    cand_ici STRING, -- Incumbent Challenger Status: C = Challenger I = Incumbent O = Open Seat
    cand_status STRING, -- Candidate Status: C = Statutory candidate F = Statutory candidate for future election N = Not yet a statutory candidate P = Statutory candidate in prior cycle
    cand_pcc STRING, -- Principal Campaign Committee
    cand_st1 STRING, -- Mailing Address - Street
    cand_st2 STRING, -- Mailing Address - Street2
    cand_city STRING, -- Mailing Address - City
    cand_st STRING, -- Mailing Address - State
    cand_zip STRING -- Mailing Address - Zip Code
)
ROW FORMAT SERDE 'com.bizo.hive.serde.csv.CSVSerde'
 WITH SERDEPROPERTIES (
   "separatorChar" = "|"
  )
LOCATION 's3n://polianaprod/campaign_finance/pacs/candidate_master/data/';


CREATE EXTERNAL TABLE IF NOT EXISTS campaign_finance_external.pac_to_candidate_contributions (
    cmte_id STRING COMMENT 'Filer Identification Number',
    amndt_ind STRING COMMENT 'Amendment Indicator Indicates if the report being filed is new (N), an amendment (A) to a previous report, or a termination (T) report.',
    rpt_tp STRING COMMENT 'Report Type Indicates the type of report filed. List of report type codes',
    transaction_pgi STRING COMMENT 'Primary-General Indicator This code indicates the election for which the contribution was made. EYYYY (election Primary, General, Other plus election year)',
    image_num STRING COMMENT 'Microfilm Location (YYOORRRFFFF) Indicates the physical location of the filing.',
    transaction_tp STRING COMMENT 'Transaction Type: 24A, 24C, 24E, 24F, 24H, 24K, 24N, 24P, 24R and 24Z are included in the PAS2 file.',
    entity_tp STRING COMMENT 'Entity Type: CAN = Candidate, CCM = Candidate Committee, COM = Committee, IND = Individual (a person), ORG = Organization (not a committee and not a person),PAC = Political Action Committee, PTY = Party Organization',
    name STRING COMMENT 'Contributor/Lender/Transfer Name',
    city STRING COMMENT 'City/Town',
    state STRING COMMENT 'State',
    zip_code STRING COMMENT 'Zip Code',
    employer STRING COMMENT 'Employer',
    occupation STRING COMMENT 'Occupation',
    transaction_dt STRING COMMENT 'Transaction Date(MMDDYYYY)',
    transaction_amt INT COMMENT 'Transaction Amount',
    other_id STRING COMMENT 'Other Identification Number For contributions from individuals this column is null. For contributions from candidates or other committees this column will contain that contributors FEC ID.',
    cand_id STRING COMMENT 'Candidate Identification: Candidate receiving money from the filing committee',
    tran_id STRING COMMENT 'Transaction ID ONLY VALID FOR ELECTRONIC FILINGS. A unique identifier permanently associated with each itemization or transaction appearing in an FEC electronic file.',
    file_num INT COMMENT 'File Number / Report ID Unique report id',
    memo_code STRING COMMENT 'Memo Code X indicates that the amount is NOT to be included in the itemization total.',
    memo_text STRING COMMENT 'Memo Text A description of the activity. Memo Text is available on itemized amounts on Schedules A and B. These transactions are included in the itemization total.',
    sub_id INT COMMENT 'FEC Record Number Unique row ID'
)
COMMENT 'http://www.fec.gov/finance/disclosure/metadata/DataDictionaryContributionstoCandidates.shtml'
ROW FORMAT SERDE 'com.bizo.hive.serde.csv.CSVSerde'
 WITH SERDEPROPERTIES (
   "separatorChar" = "|"
  )
LOCATION 's3n://polianaprod/campaign_finance/pacs/pas/data/';


-- http://www.fec.gov/finance/disclosure/metadata/DataDictionaryCommitteetoCommittee.shtml
CREATE EXTERNAL TABLE IF NOT EXISTS campaign_finance_external.pac_to_pac_contributions (
    cmte_id STRING, -- Filer Identification Number
    amndt_ind STRING, -- Amendment Indicator Indicates if the report being filed is new (N), an amendment (A) to a previous report, or a termination (T) report.
    rpt_tp STRING,  -- Report Type Indicates the type of report filed. List of report type codes
    transaction_pgi STRING, -- Primary-General Indicator This code indicates the election for which the contribution was made. EYYYY (election Primary, General, Other plus election year)
    image_num STRING, -- Microfilm Location (YYOORRRFFFF) Indicates the physical location of the filing.
    transaction_tp STRING, -- Transaction Type: 24A, 24C, 24E, 24F, 24H, 24K, 24N, 24P, 24R and 24Z are included in the PAS2 file.
    entity_tp STRING, -- Entity Type: CAN = Candidate, CCM = Candidate Committee, COM = Committee, IND = Individual (a person), ORG = Organization (not a committee and not a person),PAC = Political Action Committee, PTY = Party Organization
    name STRING, -- Contributor/Lender/Transfer Name
    city STRING, -- City/Town
    state STRING, --State
    zip_code STRING, -- Zip Code
    employer STRING, -- Employer
    occupation STRING, -- Occupation
    transaction_dt STRING, -- Transaction Date(MMDDYYYY)
    transaction_amt INT, -- Transaction Amount
    other_id STRING, -- Other Identification Number For contributions from individuals this column is null. For contributions from candidates or other committees this column will contain that contributors FEC ID.
    tran_id STRING, -- Transaction ID ONLY VALID FOR ELECTRONIC FILINGS. A unique identifier permanently associated with each itemization or transaction appearing in an FEC electronic file.
    file_num INT, -- File Number / Report ID Unique report id
    memo_code STRING, -- Memo Code 'X' indicates that the amount is NOT to be included in the itemization total.
    memo_text STRING, -- Memo Text A description of the activity. Memo Text is available on itemized amounts on Schedules A and B. These transactions are included in the itemization total.
    sub_id INT  -- FEC Record Number Unique row ID
)
ROW FORMAT SERDE 'com.bizo.hive.serde.csv.CSVSerde'
 WITH SERDEPROPERTIES (
   "separatorChar" = "|"
  )
LOCATION 's3n://polianaprod/campaign_finance/pacs/other/data/';


-- http://www.fec.gov/finance/disclosure/metadata/DataDictionaryContributionsbyIndividuals.shtml
CREATE EXTERNAL TABLE IF NOT EXISTS campaign_finance_external.pac_individual_contributions (
    cmte_id STRING, -- Filer Identification Number
    amndt_ind STRING, -- Amendment Indicator Indicates if the report being filed is new (N), an amendment (A) to a previous report, or a termination (T) report.
    rpt_tp STRING,  -- Report Type Indicates the type of report filed. List of report type codes
    transaction_pgi STRING, -- Primary-General Indicator This code indicates the election for which the contribution was made. EYYYY (election Primary, General, Other plus election year)
    image_num STRING, -- Microfilm Location (YYOORRRFFFF) Indicates the physical location of the filing.
    transaction_tp STRING, -- Transaction Type: 24A, 24C, 24E, 24F, 24H, 24K, 24N, 24P, 24R and 24Z are included in the PAS2 file.
    entity_tp STRING, -- Entity Type: CAN = Candidate, CCM = Candidate Committee, COM = Committee, IND = Individual (a person), ORG = Organization (not a committee and not a person),PAC = Political Action Committee, PTY = Party Organization
    name STRING, -- Contributor/Lender/Transfer Name
    city STRING, -- City/Town
    state STRING, --State
    zip_code STRING, -- Zip Code
    employer STRING, -- Employer
    occupation STRING, -- Occupation
    transaction_dt STRING, -- Transaction Date(MMDDYYYY)
    transaction_amt INT, -- Transaction Amount
    other_id STRING, -- Other Identification Number For contributions from individuals this column is null. For contributions from candidates or other committees this column will contain that contributors FEC ID.
    tran_id STRING, -- Transaction ID ONLY VALID FOR ELECTRONIC FILINGS. A unique identifier permanently associated with each itemization or transaction appearing in an FEC electronic file.
    file_num INT, -- File Number / Report ID Unique report id
    memo_code STRING, -- Memo Code 'X' indicates that the amount is NOT to be included in the itemization total.
    memo_text STRING, -- Memo Text A description of the activity. Memo Text is available on itemized amounts on Schedules A and B. These transactions are included in the itemization total.
    sub_id INT  -- FEC Record Number Unique row ID
)
ROW FORMAT SERDE 'com.bizo.hive.serde.csv.CSVSerde'
 WITH SERDEPROPERTIES (
   "separatorChar" = "|"
  )
LOCATION 's3n://polianaprod/campaign_finance/pacs/individuals/data/';




