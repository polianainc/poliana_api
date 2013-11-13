CREATE EXTERNAL TABLE bills.senate_votes_flat_external (
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

CREATE EXTERNAL TABLE bills.senate_votes_flat_yeas_external (
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
LOCATION 's3n://polianaprod/legislation/senate_votes_yeas_flat/';

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

use bills;
SELECT * FROM senate_votes_flat_yeas_external LIMIT 10;
SELECT * FROM senate_votes_flat_nays_external LIMIT 10;
SELECT * FROM senate_votes_flat_present_external LIMIT 10;
SELECT * FROM senate_votes_flat_not_voting_external LIMIT 10;