CREATE TABLE bills.bills_overview_flat_thomas_ids (
    bill_id STRING,
    official_title STRING,
    popular_title STRING,
    short_title STRING,
    sponsor_name STRING,
    sponsor_state STRING,
    sponsor_thomas STRING,
    cosponsor_id STRING,
    top_subject STRING,
    subject STRING,
    summary STRING,
    introduced_at STRING
)
PARTITIONED BY (congress STRING, bill_type STRING)
 STORED AS SEQUENCEFILE;

 CREATE EXTERNAL TABLE bills.bills_overview_flat_thomas_ids_external (
     bill_id STRING,
     official_title STRING,
     popular_title STRING,
     short_title STRING,
     sponsor_name STRING,
     sponsor_state STRING,
     sponsor_thomas STRING,
     cosponsor_id STRING,
     top_subject STRING,
     subject STRING,
     summary STRING,
     introduced_at STRING
 )
 PARTITIONED BY (congress STRING, bill_type STRING)
 LOCATION 's3n://polianaprod/legislation/bills_overview_flat_thomas_ids/';



CREATE TABLE bills.bills_overview_flat_cosponsor_thomas (
    bill_id STRING,
    official_title STRING,
    popular_title STRING,
    short_title STRING,
    sponsor_name STRING,
    sponsor_state STRING,
    sponsor_id STRING,
    cosponsor_id STRING,
    top_subject STRING,
    subject STRING,
    summary STRING,
    introduced_at STRING
)
PARTITIONED BY (congress STRING, bill_type STRING)
 STORED AS SEQUENCEFILE;

 CREATE TABLE bills.bills_overview_flat_cosponsor_thomas_external (
     bill_id STRING,
     official_title STRING,
     popular_title STRING,
     short_title STRING,
     sponsor_name STRING,
     sponsor_state STRING,
     sponsor_id STRING,
     cosponsor_id STRING,
     top_subject STRING,
     subject STRING,
     summary STRING,
     introduced_at STRING
 )
 PARTITIONED BY (congress STRING, bill_type STRING)
 LOCATION  's3n://polianaprod/legislation/bills_overview_flat_cosponsor_thomas/';

 nohup hive -e 'INSERT OVERWRITE TABLE bills_external.bills_overview_flat_cosponsor_thomas SELECT * FROM bills.bills_overview_flat_cosponsor_thomas;'

 CREATE TABLE bills.bills_overview_flat (
     bill_id STRING,
     official_title STRING,
     popular_title STRING,
     short_title STRING,
     sponsor_name STRING,
     sponsor_state STRING,
     sponsor_id STRING,
     cosponsor_id STRING,
     top_subject STRING,
     subject STRING,
     summary STRING,
     introduced_at STRING
 )
 PARTITIONED BY (congress STRING, bill_type STRING)
  STORED AS SEQUENCEFILE;

  CREATE TABLE bills.bills_overview (
      bill_id STRING,
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
      introduced_at STRING
  )
  PARTITIONED BY (congress STRING, bill_type STRING)
    STORED AS SEQUENCEFILE;

  CREATE EXTERNAL TABLE bills.bills_general_overview_external (
      bill_id STRING,
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
      introduced_at STRING,
      house_passage_result STRING,
      house_passage_result_at STRING,
      senate_cloture_result STRING,
      senate_cloture_result_at STRING,
      senate_passage_result STRING,
      senate_passage_result_at STRING,
      awaiting_signature BOOLEAN,
      enacted BOOLEAN,
      vetoed BOOLEAN,
      enacted_at STRING
  )
  PARTITIONED BY (congress STRING, bill_type STRING)
    LOCATION 's3n://polianaprod/legislation/bills_general_overview/';

CREATE EXTERNAL TABLE bills.bill_meta (
    bill_id STRING,
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
    introduced_at STRING,
    house_passage_result STRING,
    house_passage_result_at STRING,
    senate_cloture_result STRING,
    senate_cloture_result_at STRING,
    senate_passage_result STRING,
    senate_passage_result_at STRING,
    awaiting_signature BOOLEAN,
    enacted BOOLEAN,
    vetoed BOOLEAN,
    enacted_at STRING,
    status STRING,
    status_at STRING
)
PARTITIONED BY (congress STRING, bill_type STRING)
LOCATION 's3n://polianaprod/legislation/bills_general_overview/';

CREATE VIEW bills.view_bill_meta (
    bill_id,
    vote_id,
    official_title,
    popular_title,
    short_title,
    sponsor_name,
    sponsor_state,
    sponsor_id,
    cosponsor_ids,
    top_subject,
    subjects,
    summary,
    introduced_at,
    house_passage_result,
    house_passage_result_at,
    senate_cloture_result,
    senate_cloture_result_at,
    senate_passage_result,
    senate_passage_result_at,
    awaiting_signature,
    enacted,
    vetoed,
    enacted_at,
    status,
    status_at,
    congress,
    bill_type
) as SELECT
    b.bill_id,
    concat(bill_id_resolve(b.bill_id),".",year(b.introduced_at)),
    b.official_title,
    b.popular_title,
    b.short_title,
    b.sponsor_name,
    b.sponsor_state,
    b.sponsor_id,
    b.cosponsor_ids,
    b.top_subject,
    b.subjects,
    b.summary,
    liberal_timestamp(b.introduced_at),
    b.house_passage_result,
    b.house_passage_result_at,
    b.senate_cloture_result,
    b.senate_cloture_result_at,
    b.senate_passage_result,
    b.senate_passage_result_at,
    b.awaiting_signature,
    b.enacted,
    b.vetoed,
    liberal_timestamp(b.enacted_at),
    j.status,
    liberal_timestamp(j.status_at),
    b.congress,
    b.bill_type
FROM bills.bills_general_overview_external b JOIN bills.bills_json_external j
    ON b.bill_id = j.bill_id;

CREATE EXTERNAL TABLE bills.bill_meta_string_external (
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
    house_passage_result_at STRING,
    senate_cloture_result STRING,
    senate_cloture_result_at STRING,
    senate_passage_result STRING,
    senate_passage_result_at STRING,
    awaiting_signature STRING,
    enacted STRING,
    vetoed STRING,
    enacted_at INT,
    status STRING,
    status_at INT
)
PARTITIONED BY (congress INT, bill_type STRING)
STORED AS SEQUENCEFILE
LOCATION 's3n://polianaprod/legislation/bill_meta/';

CREATE VIEW bills.view_bill_meta_string_external (
    bill_id,
    vote_id,
    official_title,
    popular_title,
    short_title,
    sponsor_name,
    sponsor_state,
    sponsor_id,
    cosponsor_ids,
    top_subject,
    subjects,
    summary,
    introduced_at,
    house_passage_result,
    house_passage_result_at,
    senate_cloture_result,
    senate_cloture_result_at,
    senate_passage_result,
    senate_passage_result_at,
    awaiting_signature,
    enacted,
    vetoed,
    enacted_at,
    status,
    status_at,
    congress,
    bill_type
)
as SELECT
    bill_id,
    vote_id,
    official_title,
    popular_title,
    short_title,
    sponsor_name,
    sponsor_state,
    sponsor_id,
    cosponsor_ids,
    top_subject,
    subjects,
    summary,
    introduced_at,
    house_passage_result,
    liberal_timestamp(house_passage_result_at),
    senate_cloture_result,
    liberal_timestamp(senate_cloture_result_at),
    senate_passage_result,
    liberal_timestamp(senate_passage_result_at),
    awaiting_signature,
    enacted,
    vetoed,
    enacted_at,
    status,
    status_at,
    congress,
    bill_type
FROM bills.bill_meta_string_external;

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

nohup hive -e 'INSERT OVERWRITE TABLE bills.bill_meta_external PARTITION (congress, bill_type) SELECT * FROM bills.view_bill_meta;'



