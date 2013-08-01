set fs.s3n.awsAccessKeyId=AKIAI27OS66EVU7XE57A;
set fs.s3n.awsSecretAccessKey=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;

add jar /usr/local/csv-serde-1.1.2.jar;
add jar /usr/local/hive-serde-1.0.jar;

CREATE EXTERNAL TABLE IF NOT EXISTS contributions (
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
contributor_type STRING,
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
row format serde 'com.bizo.hive.serde.csv.CSVSerde'
LOCATION 's3n://contributiondata/source/';

CREATE EXTERNAL TABLE IF NOT EXISTS contributions_test (
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
contributor_type STRING,
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
row format serde 'com.bizo.hive.serde.csv.CSVSerde'
LOCATION 's3n://contributiondata/test/';

CREATE EXTERNAL TABLE IF NOT EXISTS votes (
bill_id STRING,
sponsor_id STRING,
result STRING,
congress STRING,
introduced_on STRING,
bioguide_id STRING,
vote STRING
)
row format serde 'com.bizo.hive.serde.csv.CSVSerde'
LOCATION 's3n://billvotedata/commadel/';

CREATE EXTERNAL TABLE IF NOT EXISTS politicians (
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
LOCATION 's3n://politicianmetadata/commadel/';