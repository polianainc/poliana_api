CREATE EXTERNAL TABLE bills.vote_history_external (
    bioguide_id STRING,
    display_name STRING,
    first_name STRING,
    last_name STRING,
    party STRING,
    state STRING,
    votes ARRAY<STRING>
)
STORED AS SEQUENCEFILE
LOCATION 's3n://polianaprod/legislation/vote_history_verbose/';