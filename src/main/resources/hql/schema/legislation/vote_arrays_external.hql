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