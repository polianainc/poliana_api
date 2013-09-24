set fs.s3n.awsAccessKeyId=AKIAI27OS66EVU7XE57A;
set fs.s3n.awsSecretAccessKey=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;

CREATE EXTERNAL TABLE externals.bill_votes_json (
    breakdown STRUCT<
        Party: STRUCT<
            R: STRUCT<
                Yea: DOUBLE,
                Nay: DOUBLE,
                No_Vote: DOUBLE,
                Present: DOUBLE
            >,
            D: STRUCT<
                Yea: DOUBLE,
                Nay: DOUBLE,
                No_Vote: DOUBLE,
                Present: DOUBLE
            >
        >,
        Total: STRUCT<
            Yea: DOUBLE,
            Nay: DOUBLE,
            No_Vote: DOUBLE,
            Present: DOUBLE
        >
    >,
    introduced_on STRING,
    congress STRING,
    required STRING,
    yeas_ids ARRAY<STRING>,
    short_title STRING,
    official_title STRING,
    result STRING,
    sponsor_id STRING,
    nays_ids ARRAY<STRING>,
    bill_id STRING
) PARTITIONED BY (congress STRING, vote STRING)
ROW FORMAT SERDE 'com.proofpoint.hive.serde.JsonSerde'
 WITH SERDEPROPERTIES ('errors.ignore' = 'true')
LOCATION 's3n://polianatest/full/data/';


INSERT OVERWRITE TABLE bills_actions PARTITION (congress, bill_type) SELECT * FROM bills_tmp.view_bill_actions;

CREATE TABLE bills_actions (
    bill_id STRING,
    acted_at STRING,
    committee STRING,
    how STRING,
    roll STRING,
    status STRING,
    text STRING,
    type STRING,
    vote_type STRING,
    location STRING
)
PARTITIONED BY (congress STRING, bill_type STRING);

CREATE VIEW view_bill_actions (
    bill_id,
    acted_at,
    committee,
    how,
    roll,
    status,
    text,
    type,
    vote_type,
    location,
    congress,
    bill_type
)
as SELECT
    bill_id,
    action.acted_at,
    action.committee,
    action.how,
    action.roll,
    action.status,
    action.text,
    action.type,
    action.vote_type,
    action.location,
    congress,
    bill_type
FROM bills_tmp.bills_json
LATERAL VIEW explode(actions) actions AS action;