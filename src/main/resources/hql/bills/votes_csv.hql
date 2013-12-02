CREATE VIEW bills.view_vote_arrays (
    vote_id,
    date,
    yeas,
    nays,
    not_voting,
    present,
    year,
    month
)
as SELECT
    vote_id,
    date,
    votes.Yea,
    votes.Nay,
    votes.Not_Voting,
    votes.Present,
    year(date),
    month(date)
FROM bills.votes_json_external;

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

CREATE VIEW bills.view_votes_flatten_yeas_thomas_ids (
    vote_id,
    date,
    yea_display_name,
    yea_first_name,
    yea_id,
    yea_last_name,
    yea_party,
    yea_state,
    year,
    month
)
as SELECT
    vote_id,
    date,
    yeas.display_name,
    yeas.first_name,
    yeas.id,
    yeas.last_name,
    yeas.party,
    yeas.state,
    year,
    month
FROM  bills.vote_arrays_external
     LATERAL VIEW explode(Yea) Yea AS yeas
     WHERE Yea IS NOT NULL;

CREATE TABLE bills.votes_flat_yeas_thomas (
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
STORED AS SEQUENCEFILE;

CREATE VIEW bills.view_votes_flatten_nays_thomas_ids (
    vote_id,
    date,
    nay_display_name,
    nay_first_name,
    nay_id,
    nay_last_name,
    nay_party,
    nay_state,
    year,
    month
)
as SELECT
    vote_id,
    date,
    nays.display_name,
    nays.first_name,
    nays.id,
    nays.last_name,
    nays.party,
    nays.state,
    year,
    month
FROM  bills.vote_arrays_external
    LATERAL VIEW explode(Nay) Nay AS nays
    WHERE Nay IS NOT NULL;

CREATE TABLE bills.votes_flat_nays_thomas (
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
STORED AS SEQUENCEFILE;

CREATE VIEW bills.view_votes_flatten_novote_thomas_ids (
    vote_id,
    date,
    not_voting_display_name,
    not_voting_first_name,
    not_voting_id,
    not_voting_last_name,
    not_voting_party,
    not_voting_state,
    year,
    month
)
as SELECT
    vote_id,
    date,
    no_vote.display_name,
    no_vote.first_name,
    no_vote.id,
    no_vote.last_name,
    no_vote.party,
    no_vote.state,
    year,
    month
FROM  bills.vote_arrays_external
    LATERAL VIEW explode(Not_Voting) Not_Voting AS no_vote
    WHERE not_voting IS NOT NULL;

CREATE TABLE bills.votes_flat_not_voting_thomas (
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
STORED AS SEQUENCEFILE;

CREATE VIEW bills.view_votes_flatten_present_thomas_ids (
    vote_id,
    date,
    present_display_name,
    present_first_name,
    present_id,
    present_last_name,
    present_party,
    present_state,
    year,
    month
)
as SELECT
    vote_id,
    date,
    attending.display_name,
    attending.first_name,
    attending.id,
    attending.last_name,
    attending.party,
    attending.state,
    year,
    month
FROM  bills.vote_arrays_external
    LATERAL VIEW explode(Present) Present AS attending
    WHERE present IS NOT NULL;

CREATE TABLE bills.votes_flat_present_thomas (
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
STORED AS SEQUENCEFILE;

INSERT OVERWRITE TABLE bills.votes_flat_yeas_thomas PARTITION (year, month) SELECT * FROM bills.view_votes_flatten_yeas_thomas_ids;
INSERT OVERWRITE TABLE bills.votes_flat_nays_thomas PARTITION (year, month) SELECT * FROM bills.view_votes_flatten_nays_thomas_ids;
INSERT OVERWRITE TABLE bills.votes_flat_not_voting_thomas PARTITION (year, month) SELECT * FROM bills.view_votes_flatten_novote_thomas_ids;
INSERT OVERWRITE TABLE bills.votes_flat_present_thomas PARTITION (year, month) SELECT * FROM bills.view_votes_flatten_present_thomas_ids;

CREATE TABLE bills.votes_flat_yeas_thomas (
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
STORED AS SEQUENCEFILE;

CREATE VIEW bills.view_house_votes_flat_yeas (
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
as SELECT * FROM bills.votes_flat_yeas_thomas WHERE SUBSTR(vote_id, 0, 1) = 'h';

CREATE EXTERNAL TABLE bills.senate_votes_flat_yeas_external LIKE bills.house_votes_flat_yeas_external
LOCATION 's3n://polianaprod/legislation/senate_votes_yeas_flat/';

nohup hive -e 'INSERT OVERWRITE TABLE bills.house_votes_flat_yeas_external PARTITION (year, month) SELECT * FROM bills.view_house_votes_flat_yeas;'

CREATE VIEW bills.view_senate_votes_flat_yeas (
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
as SELECT * FROM bills.votes_flat_yeas_thomas WHERE SUBSTR(vote_id, 0, 1) = 's';

CREATE EXTERNAL TABLE bills.senate_votes_flat_yeas_external LIKE bills.house_votes_flat_yeas_external
LOCATION 's3n://polianaprod/legislation/senate_votes_yeas_flat/';

nohup hive -e 'INSERT OVERWRITE TABLE bills.house_votes_flat_yeas_external PARTITION (year, month) SELECT * FROM bills.view_house_votes_flat_yeas;'

nohup hive -e 'INSERT OVERWRITE TABLE bills.house_votes_flat_yeas_external PARTITION (year, month) SELECT * FROM bills.view_house_votes_flat_yeas;'

CREATE EXTERNAL TABLE bills.senate_votes_flat_yeas_external LIKE bills.house_votes_flat_yeas_external
LOCATION 's3n://polianaprod/legislation/senate_votes_yeas_flat/';

nohup hive -e 'INSERT OVERWRITE TABLE bills.senate_votes_flat_yeas_external PARTITION (year, month) SELECT * FROM bills.view_senate_votes_flat_yeas;'

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

CREATE VIEW bills.view_votes_flat_nays (
    vote_id,
    date,
    nay_display_name,
    nay_first_name,
    nay_id,
    nay_last_name,
    nay_party,
    nay_state,
    year,
    month
)
as SELECT
    v.vote_id,
    v.date,
    v.nay_display_name,
    v.nay_first_name,
    l.bioguide_id,
    v.nay_last_name,
    v.nay_party,
    v.nay_state,
    v.year,
    v.month
FROM bills.votes_flat_nays_thomas v JOIN entities.legislators l
    ON v.nay_id = l.lis_id;

CREATE TABLE bills.votes_flat_nays (
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
STORED AS SEQUENCEFILE;

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

CREATE VIEW bills.view_votes_flat_not_voting (
    vote_id,
    date,
    not_voting_display_name,
    not_voting_first_name,
    not_voting_last_name,
    not_voting_party,
    not_voting_state,
    year,
    month
)
as SELECT
    v.vote_id,
    v.date,
    v.not_voting_display_name,
    v.not_voting_first_name,
    l.bioguide_id,
    v.not_voting_last_name,
    v.not_voting_party,
    v.not_voting_state,
    v.year,
    v.month
FROM bills.votes_flat_not_voting_thomas v JOIN entities.legislators l
    ON v.not_voting_id = l.lis_id;

CREATE TABLE bills.votes_flat_not_voting (
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
STORED AS SEQUENCEFILE;

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

CREATE VIEW bills.view_votes_flat_present (
    vote_id,
    date,
    present_display_name,
    present_first_name,
    present_id,
    present_last_name,
    present_party,
    present_state,
    year,
    month
)
as SELECT
    v.vote_id,
    v.date,
    v.present_display_name,
    v.present_first_name,
    l.bioguide_id,
    v.present_last_name,
    v.present_party,
    v.present_state,
    v.year,
    v.month
FROM bills.votes_flat_present_thomas v JOIN entities.legislators l
    ON v.present_id = l.lis_id;

CREATE TABLE bills.votes_flat_present (
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
STORED AS SEQUENCEFILE;

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


DROP VIEW bills.view_votes_flat_yeas;
DROP VIEW bills.view_votes_flat_nays;
DROP VIEW bills.view_votes_flat_not_voting;
DROP VIEW bills.view_votes_flat_present;

INSERT OVERWRITE TABLE bills.votes_flat_yeas_external PARTITION (year, month) SELECT * FROM bills.votes_flat_yeas;
INSERT OVERWRITE TABLE bills.votes_flat_nays_external PARTITION (year, month) SELECT * FROM bills.view_votes_flat_nays;
INSERT OVERWRITE TABLE bills.votes_flat_not_voting_external PARTITION (year, month) SELECT * FROM bills.view_votes_flat_not_voting;
INSERT OVERWRITE TABLE bills.votes_flat_present_external PARTITION (year, month) SELECT * FROM bills.view_votes_flat_present;

INSERT INTO TABLE bills.votes_flat
PARTITION (year, month)
SELECT
    vote_id,
    "yea" as vote,
    date,
    yea_display_name,
    yea_first_name,
    yea_id,
    yea_last_name,
    yea_party,
    yea_state,
    year,
    month
FROM bills.votes_flat_yeas_external;

INSERT INTO TABLE bills.votes_flat
PARTITION (year, month)
SELECT
    vote_id,
    "nay" as vote,
    date,
    nay_display_name,
    nay_first_name,
    nay_id,
    nay_last_name,
    nay_party,
    nay_state,
    year,
    month
FROM bills.votes_flat_nays_external;

INSERT INTO TABLE bills.votes_flat
PARTITION (year, month)
SELECT
    vote_id,
    "not_voting" as vote,
    date,
    not_voting_display_name,
    not_voting_first_name,
    not_voting_id,
    not_voting_last_name,
    not_voting_party,
    not_voting_state,
    year,
    month
FROM bills.votes_flat_not_voting_external;

INSERT INTO TABLE bills.votes_flat
PARTITION (year, month)
SELECT
    vote_id,
    "present" as vote,
    date,
    present_display_name,
    present_first_name,
    present_id,
    present_last_name,
    present_party,
    present_state,
    year,
    month
FROM bills.votes_flat_present_external;

CREATE VIEW bills.view_vote_history_structs (
    bioguide_id,
    display_name,
    first_name,
    last_name,
    party,
    state,
    vote
)
as SELECT
    id,
    display_name,
    first_name,
    last_name,
    party,
    state,
    concat(vote_id,',',vote,',',date,',',year,',',month) as vote
FROM bills.votes_flat;

CREATE VIEW bills.view_vote_history (
    bioguide_id,
    display_name,
    first_name,
    last_name,
    party,
    state,
    votes
)
as SELECT
    bioguide_id,
    display_name,
    first_name,
    last_name,
    party,
    state,
    collect_set(vote)
FROM bills.view_vote_history_structs
GROUP BY
    bioguide_id,
    display_name,
    first_name,
    last_name,
    party,
    state;

CREATE EXTERNAL TABLE bills.votes_flat (
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

CREATE VIEW bills.view_votes_by_bill (
    vote_id,
    vote,
    total,
    votes,
    year,
    month
)
as SELECT
    vote_id,
    vote,
    COUNT(DISTINCT(id)),
    COLLECT_SET(CONCAT(id,",",display_name,",",first_name,",",last_name,",",party,",",state, " | ")),
    year,
    month
FROM bills.votes_flat GROUP BY
    vote_id,
    vote,
    year,
    month;

CREATE EXTERNAL TABLE bills.votes_by_bill (
    vote_id STRING,
    vote STRING,
    total INT,
    votes ARRAY<STRING>
)
PARTITIONED BY (year INT, month INT)
STORED AS SEQUENCEFILE
LOCATION 's3n://polianaprod/legislation/votes_by_bill/';

CREATE EXTERNAL TABLE bills.votes_by_bill_flat_arrays (
    vote_id STRING,
    vote STRING,
    total INT,
    votes STRING
)
PARTITIONED BY (year INT, month INT)
STORED AS SEQUENCEFILE
LOCATION 's3n://polianaprod/legislation/votes_by_bill/';



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

CREATE VIEW view_votes_structs (
    vote_id,
    vote,
    year,
    month
)
as SELECT
    vote_id,
    STRUCT(
        vote,
        date,
        display_name,
        first_name,
        id,
        last_name,
        party,
        state
    ),
    year,
    month
FROM bills.votes_flat;

CREATE VIEW bills.view_votes_flat_thomas (
    vote_id,
    date,
    yea_display_name,
    yea_first_name,
    yea_id,
    yea_last_name,
    yea_party,
    yea_state,
    nay_display_name,
    nay_first_name,
    nay_id,
    nay_last_name,
    nay_party,
    nay_state,
    not_voting_display_name,
    not_voting_first_name,
    not_voting_id,
    not_voting_last_name,
    not_voting_party,
    not_voting_state,
    present_display_name,
    present_first_name,
    present_id,
    present_last_name,
    present_party,
    present_state,
    year,
    month
)
as SELECT
    y.vote_id,
    y.date,
    y.yea_display_name,
    y.yea_first_name,
    y.yea_id,
    y.yea_last_name,
    y.yea_party,
    y.yea_state,
    n.nay_display_name,
    n.nay_first_name,
    n.nay_id,
    n.nay_last_name,
    n.nay_party,
    n.nay_state,
    nv.not_voting_display_name,
    nv.not_voting_first_name,
    nv.not_voting_id,
    nv.not_voting_last_name,
    nv.not_voting_party,
    nv.not_voting_state,
    p.present_display_name,
    p.present_first_name,
    p.present_id,
    p.present_last_name,
    p.present_party,
    p.present_state,
    y.year,
    y.month
FROM bills.view_votes_flatten_yeas_thomas_ids y JOIN
     bills.view_votes_flatten_nays_thomas_ids n
     ON (y.vote_id = n.vote_id) JOIN
     bills.view_votes_flatten_novote_thomas_ids nv
     ON (n.vote_id = nv.vote_id) JOIN
     bills.view_votes_flatten_present_thomas_ids p
     ON (nv.vote_id = p.vote_id);

CREATE VIEW bills.view_votes_flat_yeas_bioguide (
    vote_id,
    date,
    yea_display_name,
    yea_first_name,
    yea_id,
    yea_last_name,
    yea_party,
    yea_state,
    nay_display_name,
    nay_first_name,
    nay_id,
    nay_last_name,
    nay_party,
    nay_state,
    not_voting_display_name,
    not_voting_first_name,
    not_voting_id,
    not_voting_last_name,
    not_voting_party,
    not_voting_state,
    present_display_name,
    present_first_name,
    present_id,
    present_last_name,
    present_party,
    present_state,
    year,
    month
)
as SELECT
    v.vote_id,
    v.date,
    v.yea_display_name,
    v.yea_first_name,
    l.bioguide_id,
    v.yea_last_name,
    v.yea_party,
    v.yea_state,
    v.nay_display_name,
    v.nay_first_name,
    v.nay_id,
    v.nay_last_name,
    v.nay_party,
    v.nay_state,
    v.not_voting_display_name,
    v.not_voting_first_name,
    v.not_voting_id,
    v.not_voting_last_name,
    v.not_voting_party,
    v.not_voting_state,
    v.present_display_name,
    v.present_first_name,
    v.present_id,
    v.present_last_name,
    v.present_party,
    v.present_state,
    v.year,
    v.month
FROM bills.view_votes_flat_thomas v JOIN entities.legislators l
    ON v.yea_id = l.thomas_id;

CREATE VIEW bills.view_votes_flat_nays_bioguide (
    vote_id,
    date,
    yea_display_name,
    yea_first_name,
    yea_id,
    yea_last_name,
    yea_party,
    yea_state,
    nay_display_name,
    nay_first_name,
    nay_id,
    nay_last_name,
    nay_party,
    nay_state,
    not_voting_display_name,
    not_voting_first_name,
    not_voting_id,
    not_voting_last_name,
    not_voting_party,
    not_voting_state,
    present_display_name,
    present_first_name,
    present_id,
    present_last_name,
    present_party,
    present_state,
    year,
    month
)
as SELECT
    v.vote_id,
    v.date,
    v.yea_display_name,
    v.yea_first_name,
    v.yea_id,
    v.yea_last_name,
    v.yea_party,
    v.yea_state,
    v.nay_display_name,
    v.nay_first_name,
    l.bioguide_id,
    v.nay_last_name,
    v.nay_party,
    v.nay_state,
    v.not_voting_display_name,
    v.not_voting_first_name,
    v.not_voting_id,
    v.not_voting_last_name,
    v.not_voting_party,
    v.not_voting_state,
    v.present_display_name,
    v.present_first_name,
    v.present_id,
    v.present_last_name,
    v.present_party,
    v.present_state,
    v.year,
    v.month
FROM bills.view_votes_flat_yeas_bioguide v JOIN entities.legislators l
    ON v.nay_id = l.thomas_id;

CREATE VIEW bills.view_votes_flat_novote_bioguide (
    vote_id,
    date,
    yea_display_name,
    yea_first_name,
    yea_id,
    yea_last_name,
    yea_party,
    yea_state,
    nay_display_name,
    nay_first_name,
    nay_id,
    nay_last_name,
    nay_party,
    nay_state,
    not_voting_display_name,
    not_voting_first_name,
    not_voting_id,
    not_voting_last_name,
    not_voting_party,
    not_voting_state,
    present_display_name,
    present_first_name,
    present_id,
    present_last_name,
    present_party,
    present_state,
    year,
    month
)
as SELECT
    v.vote_id,
    v.date,
    v.yea_display_name,
    v.yea_first_name,
    v.yea_id,
    v.yea_last_name,
    v.yea_party,
    v.yea_state,
    v.nay_display_name,
    v.nay_first_name,
    v.nay_id,
    v.nay_last_name,
    v.nay_party,
    v.nay_state,
    v.not_voting_display_name,
    v.not_voting_first_name,
    l.bioguide_id,
    v.not_voting_last_name,
    v.not_voting_party,
    v.not_voting_state,
    v.present_display_name,
    v.present_first_name,
    v.present_id,
    v.present_last_name,
    v.present_party,
    v.present_state,
    v.year,
    v.month
FROM bills.view_votes_flat_nays_bioguide v JOIN entities.legislators l
    ON v.not_voting_id = l.thomas_id;

CREATE VIEW bills.view_votes_flat_bioguides (
    vote_id,
    date,
    yea_display_name,
    yea_first_name,
    yea_id,
    yea_last_name,
    yea_party,
    yea_state,
    nay_display_name,
    nay_first_name,
    nay_id,
    nay_last_name,
    nay_party,
    nay_state,
    not_voting_display_name,
    not_voting_first_name,
    not_voting_id,
    not_voting_last_name,
    not_voting_party,
    not_voting_state,
    present_display_name,
    present_first_name,
    present_id,
    present_last_name,
    present_party,
    present_state,
    year,
    month
)
as SELECT
    v.vote_id,
    v.date,
    v.yea_display_name,
    v.yea_first_name,
    v.yea_id,
    v.yea_last_name,
    v.yea_party,
    v.yea_state,
    v.nay_display_name,
    v.nay_first_name,
    v.nay_id,
    v.nay_last_name,
    v.nay_party,
    v.nay_state,
    v.not_voting_display_name,
    v.not_voting_first_name,
    v.not_voting_id,
    v.not_voting_last_name,
    v.not_voting_party,
    v.not_voting_state,
    v.present_display_name,
    v.present_first_name,
    l.bioguide_id,
    v.present_last_name,
    v.present_party,
    v.present_state,
    v.year,
    v.month
FROM bills.view_votes_flat_novote_bioguide v JOIN entities.legislators l
    ON v.present_id = l.thomas_id;

CREATE TABLE bills.votes_flat_bioguides (
    vote_id STRING,
    date STRING,
    yea_display_name STRING,
    yea_first_name STRING,
    yea_id STRING,
    yea_last_name STRING,
    yea_party STRING,
    yea_state STRING,
    nay_display_name STRING,
    nay_first_name STRING,
    nay_id STRING,
    nay_last_name STRING,
    nay_party STRING,
    nay_state STRING,
    not_voting_display_name STRING,
    not_voting_first_name STRING,
    not_voting_id STRING,
    not_voting_last_name STRING,
    not_voting_party STRING,
    not_voting_state STRING,
    present_display_name STRING,
    present_first_name STRING,
    present_id STRING,
    present_last_name STRING,
    present_party STRING,
    present_state STRING
)
PARTITIONED BY (year INT, month INT)
 STORED AS SEQUENCEFILE;

nohup hive -e 'INSERT OVERWRITE TABLE bills.votes_flat_thomas PARTITION (year, month) SELECT * FROM bills.view_votes_flat_thomas;'
nohup hive -e 'INSERT OVERWRITE TABLE bills.votes_flat_bioguides PARTITION (year, month) SELECT * FROM bills.view_votes_flat_bioguides;'