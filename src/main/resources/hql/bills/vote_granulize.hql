CREATE VIEW bills.view_house_votes_flat_nays (
    vote_id,
    date,
    nay_display_name,
    nay_first_name,
    nay_bioguide_id,
    nay_last_name,
    nay_party,
    nay_state,
    year,
    month
)
as SELECT * FROM bills.votes_flat_nays_thomas WHERE SUBSTR(vote_id, 0, 1) = 'h';

CREATE EXTERNAL TABLE bills.house_votes_flat_nays_external  (
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
LOCATION 's3n://polianaprod/legislation/house_votes_nays_flat/';

CREATE VIEW bills.view_senate_votes_flat_nays (
    vote_id,
    date,
    nay_display_name,
    nay_first_name,
    nay_bioguide_id,
    nay_last_name,
    nay_party,
    nay_state,
    year,
    month
)
as SELECT * FROM bills.votes_flat_nays_thomas WHERE SUBSTR(vote_id, 0, 1) = 's';

CREATE EXTERNAL TABLE bills.senate_votes_flat_nays_external (
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

CREATE VIEW bills.view_house_votes_flat_present (
    vote_id,
    date,
    present_display_name,
    present_first_name,
    present_bioguide_id,
    present_last_name,
    present_party,
    present_state,
    year,
    month
)
as SELECT * FROM bills.votes_flat_present_thomas WHERE SUBSTR(vote_id, 0, 1) = 'h';

CREATE EXTERNAL TABLE bills.house_votes_flat_present_external (
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

CREATE VIEW bills.view_senate_votes_flat_present (
    vote_id,
    date,
    present_display_name,
    present_first_name,
    present_bioguide_id,
    present_last_name,
    present_party,
    present_state,
    year,
    month
)
as SELECT * FROM bills.votes_flat_present_thomas WHERE SUBSTR(vote_id, 0, 1) = 's';

CREATE EXTERNAL TABLE bills.senate_votes_flat_present_external (
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

CREATE VIEW bills.view_house_votes_flat_not_voting (
    vote_id,
    date,
    yea_display_name,
    yea_first_name,
    yea_bioguide_id,
    yea_last_name,
    yea_party,
    yea_state,
    year,
    month
)
as SELECT * FROM bills.votes_flat_not_voting_thomas WHERE SUBSTR(vote_id, 0, 1) = 'h';

CREATE EXTERNAL TABLE bills.house_votes_flat_not_voting_external (
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

CREATE VIEW bills.view_senate_votes_flat_not_voting (
    vote_id,
    date,
    yea_display_name,
    yea_first_name,
    yea_bioguide_id,
    yea_last_name,
    yea_party,
    yea_state,
    year,
    month
)
as SELECT * FROM bills.votes_flat_not_voting_thomas WHERE SUBSTR(vote_id, 0, 1) = 's';

CREATE EXTERNAL TABLE bills.senate_votes_flat_not_voting_external (
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
LOCATION 's3n://polianaprod/legislation/senate_votes_not_voting_flat/';

nohup hive -e 'INSERT OVERWRITE TABLE bills.senate_votes_flat_present_external PARTITION (year, month) SELECT * FROM bills.view_senate_votes_flat_present;
INSERT OVERWRITE TABLE bills.house_votes_flat_present_external PARTITION (year, month) SELECT * FROM bills.view_house_votes_flat_present;
INSERT OVERWRITE TABLE bills.house_votes_flat_not_voting_external PARTITION (year, month) SELECT * FROM bills.view_house_votes_flat_not_voting;
INSERT OVERWRITE TABLE bills.senate_votes_flat_not_voting_external PARTITION (year, month) SELECT * FROM bills.view_senate_votes_flat_not_voting;
INSERT OVERWRITE TABLE bills.senate_votes_flat_nays_external PARTITION (year, month) SELECT * FROM bills.view_senate_votes_flat_nays;
INSERT OVERWRITE TABLE bills.house_votes_flat_nays_external PARTITION (year, month) SELECT * FROM bills.view_house_votes_flat_nays;'
