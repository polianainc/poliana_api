CREATE EXTERNAL TABLE bills.vote_arrays_external (
    vote_id STRING,
    date STRING,
    Yea ARRAY<STRUCT<
        display_name: STRING,
        first_name: STRING,
        id: STRING,
        last_name: STRING,
        party: STRING,
        state: STRING
    >>,
    Nay ARRAY<STRUCT<
        display_name: STRING,
        first_name: STRING,
        id: STRING,
        last_name: STRING,
        party: STRING,
        state: STRING
    >>,
    Not_Voting ARRAY<STRUCT<
        display_name: STRING,
        first_name: STRING,
        id: STRING,
        last_name: STRING,
        party: STRING,
        state: STRING
    >>,
    Present ARRAY<STRUCT<
        display_name: STRING,
        first_name: STRING,
        id: STRING,
        last_name: STRING,
        party: STRING,
        state: STRING
    >>
)
PARTITIONED BY (year INT, month INT)
STORED AS SEQUENCEFILE
LOCATION 's3n://polianaprod/legislation/vote_arrays/';

CREATE EXTERNAL TABLE bills.votes_flat_yeas_external (
    vote_id STRING,
    date STRING,
    yea_display_name STRING,
    yea_first_name STRING,
    yea_id STRING,
    yea_last_name STRING,
    yea_party STRING,
    yea_state STRING
)
PARTITIONED BY (year INT, month INT)
STORED AS SEQUENCEFILE
LOCATION 's3n://polianaprod/legislation/votes_yeas_flat/';

CREATE EXTERNAL TABLE bills.votes_flat_nays_external (
    vote_id STRING,
    date STRING,
    nay_display_name STRING,
    nay_first_name STRING,
    nay_id STRING,
    nay_last_name STRING,
    nay_party STRING,
    nay_state STRING
)
PARTITIONED BY (year INT, month INT)
STORED AS SEQUENCEFILE
LOCATION 's3n://polianaprod/legislation/votes_nays_flat/';

CREATE EXTERNAL TABLE bills.votes_flat_not_voting_external (
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

CREATE EXTERNAL TABLE bills.votes_flat_present_external (
    vote_id STRING,
    date STRING,
    present_display_name STRING,
    present_first_name STRING,
    present_id STRING,
    present_last_name STRING,
    present_party STRING,
    present_state STRING
)
PARTITIONED BY (year INT, month INT)
STORED AS SEQUENCEFILE
LOCATION 's3n://polianaprod/legislation/votes_present_flat/';

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

