CREATE EXTERNAL TABLE bills_external.senate_votes_flat (
   vote_id STRING,
   date STRING,
   yea_display_name STRING,
   yea_first_name STRING,
   yea_bioguide_id STRING,
   yea_last_name STRING,
   yea_party STRING,
   yea_state STRING
)
PARTITIONED BY (year INT, month INT)
STORED AS SEQUENCEFILE
LOCATION 's3://polianaprod/legislation/senate_votes_flat/';

CREATE EXTERNAL TABLE bills_external.senate_votes_flat_yeas (
   vote_id STRING,
   date STRING,
   yea_display_name STRING,
   yea_first_name STRING,
   yea_bioguide_id STRING,
   yea_last_name STRING,
   yea_party STRING,
   yea_state STRING
)
PARTITIONED BY (year INT, month INT)
STORED AS SEQUENCEFILE
LOCATION 's3://polianaprod/legislation/senate_votes_yeas_flat/';

CREATE EXTERNAL TABLE bills_external.house_votes_flat_yeas_ (
   vote_id STRING,
   date STRING,
   yea_display_name STRING,
   yea_first_name STRING,
   yea_bioguide_id STRING,
   yea_last_name STRING,
   yea_party STRING,
   yea_state STRING
)
PARTITIONED BY (year INT, month INT)
STORED AS SEQUENCEFILE
LOCATION 's3n://polianaprod/legislation/house_votes_yeas_flat/';

CREATE EXTERNAL TABLE bills_external.senate_votes_flat_nays (
   vote_id STRING,
   date STRING,
   nay_display_name STRING,
   nay_first_name STRING,
   nay_bioguide_id STRING,
   nay_last_name STRING,
   nay_party STRING,
   nay_state STRING
)
PARTITIONED BY (year INT, month INT)
STORED AS SEQUENCEFILE
LOCATION 's3n://polianaprod/legislation/senate_votes_nays_flat/';

CREATE EXTERNAL TABLE bills_external.house_votes_flat_present (
    vote_id STRING,
    date STRING,
    present_display_name STRING,
    present_first_name STRING,
    present_bioguide_id STRING,
    present_last_name STRING,
    present_party STRING,
    present_state STRING
)
PARTITIONED BY (year INT, month INT)
STORED AS SEQUENCEFILE
LOCATION 's3n://polianaprod/legislation/house_votes_present_flat/';

CREATE EXTERNAL TABLE bills_external.senate_votes_flat_present (
    vote_id STRING,
    date STRING,
    present_display_name STRING,
    present_first_name STRING,
    present_bioguide_id STRING,
    present_last_name STRING,
    present_party STRING,
    present_state STRING
)
PARTITIONED BY (year INT, month INT)
STORED AS SEQUENCEFILE
LOCATION 's3n://polianaprod/legislation/senate_votes_present_flat/';

CREATE EXTERNAL TABLE bills_external.house_votes_flat_not_voting (
    vote_id STRING,
    date STRING,
    not_voting_display_name STRING,
    not_voting_first_name STRING,
    not_voting_bioguide_id STRING,
    not_voting_last_name STRING,
    not_voting_party STRING,
    not_voting_state STRING
)
PARTITIONED BY (year INT, month INT)
STORED AS SEQUENCEFILE
LOCATION 's3n://polianaprod/legislation/house_votes_not_voting_flat/';

CREATE EXTERNAL TABLE bills_external.senate_votes_flat_not_voting (
   vote_id STRING,
   date STRING,
   not_voting_display_name STRING,
   not_voting_first_name STRING,
   not_voting_bioguide_id STRING,
   not_voting_last_name STRING,
   not_voting_party STRING,
   not_voting_state STRING
)
PARTITIONED BY (year INT, month INT)
STORED AS SEQUENCEFILE
LOCATION 's3n://polianaprod/legislation/senate_votes_not_voting_flat/';

CREATE EXTERNAL TABLE bills_external.votes_flat_not_voting (
    vote_id STRING,
    date STRING,
    not_voting_display_name STRING,
    not_voting_first_name STRING,
    not_voting_id STRING,
    not_voting_last_name STRING,
    not_voting_party STRING,
    not_voting_state STRING
)
PARTITIONED BY (year INT, month INT)
STORED AS SEQUENCEFILE
LOCATION 's3n://polianaprod/legislation/votes_not_voting_flat/';

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

CREATE TABLE entities.legislators_flat_terms (
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

CREATE EXTERNAL TABLE entities.senate_terms_external LIKE entities.legislators_flat_terms_external
 LOCATION 's3n://polianaprod/entities/senate_terms/';

CREATE EXTERNAL TABLE entities.house_terms_external LIKE entities.legislators_flat_terms_external
 LOCATION 's3n://polianaprod/entities/house_terms/';

CREATE EXTERNAL TABLE bills_external.senate_votes_by_bill (
    vote_id STRING,
    yea_total INT,
    nay_total INT,
    not_voting_total INT,
    not_present_total INT,
    yea_votes STRING,
    nay_votes STRING,
    not_voting STRING,
    not_present STRING,
    date STRING
)
PARTITIONED BY (year INT, month INT)
STORED AS SEQUENCEFILE
LOCATION 's3n://polianaprod/legislation/senate_votes_by_bill/';

CREATE EXTERNAL TABLE bills_external.house_votes_flat (
    vote_id STRING,
    vote STRING,
    date STRING,
    bioguide_id STRING,
    display_name STRING,
    first_name STRING,
    last_name STRING,
    party STRING,
    state STRING,
    district STRING,
    religion STRING
)
PARTITIONED BY (year INT, month INT)
STORED AS SEQUENCEFILE
LOCATION 's3n://polianaprod/legislation/house_votes_flat/';

CREATE EXTERNAL TABLE bills.senate_votes_flat_external 
LIKE bills.house_votes_flat_external
LOCATION 's3n://polianaprod/legislation/senate_votes_flat/';

CREATE EXTERNAL TABLE bills.senate_votes_flat_external2 
LIKE bills.house_votes_flat_external 
LOCATION 's3n://polianaprod/legislation/senate_votes_flat2/';

CREATE EXTERNAL TABLE bills_external.house_votes_by_bill (
    vote_id STRING,
    yea_total INT,
    nay_total INT,
    not_voting_total INT,
    not_present_total INT,
    yea_votes STRING,
    nay_votes STRING,
    not_voting STRING,
    not_present STRING,
    date STRING
)
PARTITIONED BY (year INT, month INT)
STORED AS SEQUENCEFILE
LOCATION 's3n://polianaprod/legislation/house_votes_by_bill/';

CREATE EXTERNAL TABLE bills_external.bill_sponsorship_flat (
    bill_id STRING,
    sponsor_id STRING,
    cosponsor_id STRING,
    introduced_at INT,
    bill_type STRING

)
PARTITIONED BY (congress INT, year INT, month INT)
STORED AS SEQUENCEFILE
LOCATION 's3n://polianaprod/legislation/bill_sponsorship_flat/';

CREATE EXTERNAL TABLE bills_external.votes_by_bill_embedded (
    vote_id STRING,
    yea_total INT,
    nay_total INT,
    not_voting_total INT,
    not_present_total INT,
    yea_votes STRING,
    nay_votes STRING,
    not_voting STRING,
    not_present STRING,
    date STRING
)
PARTITIONED BY (chamber STRING, year INT, month INT)
STORED AS SEQUENCEFILE
LOCATION 's3n://polianaprod/legislation/votes_by_bill_embedded/';

use bills_external;
ALTER TABLE house_votes_by_bill RECOVER PARTITIONS;
ALTER TABLE senate_votes_flat RECOVER PARTITIONS;
ALTER TABLE senate_votes_flat RECOVER PARTITIONS;
ALTER TABLE house_votes_flat RECOVER PARTITIONS;
ALTER TABLE senate_votes_by_bill RECOVER PARTITIONS;
ALTER TABLE senate_votes_flat RECOVER PARTITIONS;
ALTER TABLE senate_votes_flat_yeas RECOVER PARTITIONS;
ALTER TABLE house_votes_flat_yeas RECOVER PARTITIONS;
ALTER TABLE senate_votes_flat_nays RECOVER PARTITIONS;
ALTER TABLE house_votes_flat_present RECOVER PARTITIONS;
ALTER TABLE senate_votes_flat_present RECOVER PARTITIONS;
ALTER TABLE house_votes_flat_not_voting RECOVER PARTITIONS;
ALTER TABLE senate_votes_flat_not_voting RECOVER PARTITIONS;
ALTER TABLE votes_flat_not_voting RECOVER PARTITIONS;
ALTER TABLE votes_by_bill_embedded RECOVER PARTITIONS;



