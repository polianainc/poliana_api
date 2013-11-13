CREATE TABLE bills.votes_json (
    category STRING,
    chamber STRING,
    congress INT,
    date STRING,
    nomination STRUCT<
        number: STRING,
        title: STRING
    >,
    amendment STRUCT<
        number: STRING,
        purpose: STRING,
        type: STRING
    >,
    bill STRUCT<
        number: INT,
        congress: INT,
        title: STRING,
        type: STRING
    >,
    number INT,
    question STRING,
    requires STRING,
    result STRING,
    result_text STRING,
    session STRING,
    source_url STRING,
    subject STRING,
    type STRING,
    updated_at STRING,
    vote_id STRING,
    votes STRUCT<
        Nay: ARRAY<STRUCT<
            display_name: STRING,
            first_name: STRING,
            id: STRING,
            last_name: STRING,
            party: STRING,
            state: STRING
        >>,
        Not_Voting: ARRAY<STRUCT<
            display_name: STRING,
            first_name: STRING,
            id: STRING,
            last_name: STRING,
            party: STRING,
            state: STRING
        >>,
        Present: ARRAY<STRUCT<
            display_name: STRING,
            first_name: STRING,
            id: STRING,
            last_name: STRING,
            party: STRING,
            state: STRING
        >>,
        Yea: ARRAY<STRUCT<
            display_name: STRING,
            first_name: STRING,
            id: STRING,
            last_name: STRING,
            party: STRING,
            state: STRING
        >>
    >
)
ROW FORMAT SERDE 'com.proofpoint.hive.serde.JsonSerde'
 WITH SERDEPROPERTIES ('errors.ignore' = 'true')
;

LOAD DATA LOCAL INPATH '/mnt/new_data/votes.tar.bz2'
OVERWRITE INTO TABLE bills.votes_json;




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

CREATE EXTERNAL TABLE bills_external.votes_json (
    category STRING,
    chamber STRING,
    congress INT,
    date STRING,
    nomination STRUCT<
        number: STRING,
        title: STRING
    >,
    amendment STRUCT<
        number: STRING,
        purpose: STRING,
        type: STRING
    >,
    bill STRUCT<
        number: INT,
        congress: INT,
        title: STRING,
        type: STRING
    >,
    number INT,
    question STRING,
    requires STRING,
    result STRING,
    result_text STRING,
    session STRING,
    source_url STRING,
    subject STRING,
    type STRING,
    updated_at STRING,
    vote_id STRING,
    votes STRUCT<
        Nay: ARRAY<STRUCT<
            display_name: STRING,
            first_name: STRING,
            id: STRING,
            last_name: STRING,
            party: STRING,
            state: STRING
        >>,
        Not_Voting: ARRAY<STRUCT<
            display_name: STRING,
            first_name: STRING,
            id: STRING,
            last_name: STRING,
            party: STRING,
            state: STRING
        >>,
        Present: ARRAY<STRUCT<
            display_name: STRING,
            first_name: STRING,
            id: STRING,
            last_name: STRING,
            party: STRING,
            state: STRING
        >>,
        Yea: ARRAY<STRUCT<
            display_name: STRING,
            first_name: STRING,
            id: STRING,
            last_name: STRING,
            party: STRING,
            state: STRING
        >>
    >
)
ROW FORMAT SERDE 'com.proofpoint.hive.serde.JsonSerde'
 WITH SERDEPROPERTIES ('errors.ignore' = 'true')
LOCATION 's3n://polianaprod/legislation/votes_json/';

CREATE TABLE bills.votes_json (
    category STRING,
    chamber STRING,
    congress INT,
    ds STRING,
    nomination STRUCT<
        number: STRING,
        title: STRING
    >,
    amendment STRUCT<
        number: STRING,
        purpose: STRING,
        type: STRING
    >,
    bill STRUCT<
        number: INT,
        congress: INT,
        title: STRING,
        type: STRING
    >,
    number INT,
    question STRING,
    requires STRING,
    result STRING,
    result_text STRING,
    session STRING,
    source_url STRING,
    subject STRING,
    type STRING,
    updated_at STRING,
    vote_id STRING,
    votes STRUCT<
        Nay: ARRAY<STRUCT<
            display_name: STRING,
            first_name: STRING,
            id: STRING,
            last_name: STRING,
            party: STRING,
            state: STRING
        >>,
        Not_Voting: ARRAY<STRUCT<
            display_name: STRING,
            first_name: STRING,
            id: STRING,
            last_name: STRING,
            party: STRING,
            state: STRING
        >>,
        Present: ARRAY<STRUCT<
            display_name: STRING,
            first_name: STRING,
            id: STRING,
            last_name: STRING,
            party: STRING,
            state: STRING
        >>,
        Yea: ARRAY<STRUCT<
            display_name: STRING,
            first_name: STRING,
            id: STRING,
            last_name: STRING,
            party: STRING,
            state: STRING
        >>
    >
)
STORED AS SEQUENCEFILE;




     LATERAL VIEW explode(nays) nays AS nay
     LATERAL VIEW explode(not_voting) not_voting AS no_vote
     LATERAL VIEW explode(present) present AS attending;


