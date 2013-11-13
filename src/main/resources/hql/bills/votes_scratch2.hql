CREATE VIEW bills.view_senate_votes_flat_not_voting (
    vote_id,
    vote,
    date,
    bioguide_id,
    display_name,
    first_name,
    last_name,
    party,
    state,
    district,
    religion,
    year,
    month 
) as SELECT
    v.vote_id,
    'not_voting',
    v.date,
    l.bioguide_id,
    l.official_full,
    l.first_name,
    l.last_name,
    l.party,
    v.not_voting_state,
    null,
    l.religion,
    v.year,
    v.month
FROM
bills.senate_votes_flat_not_voting_external v FULL OUTER JOIN entities.legislators_external l
    ON v.not_voting_bioguide_id = l.bioguide_id;

CREATE VIEW bills.view_senate_votes_flat_yeas (
    vote_id,
    vote,
    date,
    bioguide_id,
    display_name,
    first_name,
    last_name,
    party,
    state,
    district,
    religion,
    year,
    month 
) as SELECT
    v.vote_id,
    'yea',
    v.date,
    l.bioguide_id,
    l.official_full,
    l.first_name,
    l.last_name,
    l.party,
    v.yea_state,
    null,
    l.religion,
    v.year,
    v.month
FROM
bills.senate_votes_flat_yeas_external v FULL OUTER JOIN entities.legislators_external l
    ON v.yea_bioguide_id = l.lis_id;

CREATE VIEW bills.view_senate_votes_flat_nays (
    vote_id,
    vote,
    date,
    bioguide_id,
    display_name,
    first_name,
    last_name,
    party,
    state,
    district,
    religion,
    year,
    month 
) as SELECT
    v.vote_id,
    'nay',
    v.date,
    l.bioguide_id,
    l.official_full,
    l.first_name,
    l.last_name,
    l.party,
    v.nay_state,
    null,
    l.religion,
    v.year,
    v.month
FROM
bills.senate_votes_flat_nays_external v FULL OUTER JOIN entities.legislators_external l
    ON v.nay_bioguide_id = l.lis_id;

INSERT INTO TABLE bills.senate_votes_flat_external3 PARTITION (year, month) SELECT * FROM bills.view_senate_votes_flat_not_voting;
INSERT INTO TABLE bills.senate_votes_flat_external3 PARTITION (year, month) SELECT * FROM bills.view_senate_votes_flat_yeas;
INSERT INTO TABLE bills.senate_votes_flat_external3 PARTITION (year, month) SELECT * FROM bills.view_senate_votes_flat_nays;    
INSERT INTO TABLE bills.senate_votes_flat_external3 PARTITION (year, month) SELECT * FROM bills.view_senate_votes_flat_present;

CREATE VIEW bills.view_senate_votes_flat_present (
    vote_id,
    vote,
    date,
    bioguide_id,
    display_name,
    first_name,
    last_name,
    party,
    state,
    district,
    religion,
    year,
    month 
) as SELECT
    v.vote_id,
    'not_present',
    v.date,
    l.bioguide_id,
    l.official_full,
    l.first_name,
    l.last_name,
    l.party,
    v.present_state,
    null,
    l.religion,
    v.year,
    v.month
FROM
bills.senate_votes_flat_present_external v FULL OUTER JOIN entities.legislators_external l
    ON v.present_bioguide_id = l.lis_id;



CREATE VIEW bills.view_senate_votes_by_bill_yea (
    vote_id,
    vote,
    date,
    yea_total,
    yea_votes,
    year,
    month
)
as SELECT
    vote_id,
    vote,
    date,
    COUNT(DISTINCT(bioguide_id)),
    COLLECT_SET(CONCAT_WS(bioguide_id,",",display_name,",",first_name,",",last_name,",",party,",",state,",",district,",",religion)),
    year,
    month
FROM bills.senate_votes_flat_external3 WHERE vote = 'yea' and vote_id is not null GROUP BY
    vote_id,
    vote,
    date,
    year,
    month;

CREATE VIEW bills.view_senate_votes_by_bill_nay (
    nay_vote_id,
    nay_vote,
    nay_date,
    nay_total,
    nay_votes,
    nay_year,
    nay_month
)
as SELECT
    vote_id,
    vote,
    date,
    COUNT(DISTINCT(bioguide_id)),
    COLLECT_SET(CONCAT_WS(bioguide_id,",",display_name,",",first_name,",",last_name,",",party,",",state,",",district,",",religion)),
    year,
    month
FROM bills.senate_votes_flat_external3 WHERE vote = 'nay' and vote_id is not null GROUP BY
    vote_id,
    vote,
    date,
    year,
    month;

CREATE VIEW bills.view_senate_votes_by_bill_present (
    present_vote_id,
    present_vote,
    present_date,
    present_total,
    present_votes,
    present_year,
    present_month
)
as SELECT
    vote_id,
    vote,
    date,
    COUNT(DISTINCT(bioguide_id)),
    COLLECT_SET(CONCAT_WS(bioguide_id,",",display_name,",",first_name,",",last_name,",",party,",",state,",",district,",",religion)),
    year,
    month
FROM bills.senate_votes_flat_external3 WHERE vote = 'not_present' and vote_id is not null GROUP BY
    vote_id,
    vote,
    date,
    year,
    month;

CREATE VIEW bills.view_senate_votes_by_bill_not_voting (
    not_voting_vote_id,
    not_voting_vote,
    not_voting_date,
    not_voting_total,
    not_voting_votes,
    not_voting_year,
    not_voting_month
)
as SELECT
    vote_id,
    vote,
    date,
    COUNT(DISTINCT(bioguide_id)),
    COLLECT_SET(CONCAT_WS(bioguide_id,",",display_name,",",first_name,",",last_name,",",party,",",state,",",district,",",religion)),
    year,
    month
FROM bills.senate_votes_flat_external3 WHERE vote = 'not_voting' and vote_id is not null GROUP BY
    vote_id,
    vote,
    date,
    year,
    month;

CREATE VIEW bills.view_joined_senate_votes_by_bill (
    vote_id,
    yea_total,
    nay_total,
    not_voting_total,
    present_total,
    yea_votes,
    nay_votes,
    not_voting,
    present,
    date,
    year,
    month
)
as SELECT
    y.yea_vote_id,
    y.yea_total,
    n.nay_total,
    nv.not_voting_total,
    p.present_total,
    y.yea_votes,
    n.nay_votes,
    nv.not_voting_votes,
    p.present_votes,
    y.yea_date,
    y.yea_year,
    y.yea_month
FROM bills.view_senate_votes_by_bill_yea y FULL OUTER JOIN bills.view_senate_votes_by_bill_nay n 
     ON y.yea_vote_id = n.nay_vote_id  FULL OUTER JOIN bills.view_senate_votes_by_bill_present p
     ON n.nay_vote_id = p.present_vote_id FULL OUTER JOIN bills.view_senate_votes_by_bill_not_voting nv
     ON p.present_vote_id = nv.not_voting_vote_id;


CREATE EXTERNAL TABLE bills.senate_votes_by_bill_external (
    vote_id STRING,
    yea_total INT,
    nay_total INT,
    not_voting_total INT,
    not_present_total INT,
    yea_votes STRING,
    nay_votes STRING,
    not_voting STRING,
    not_present STRING,
    date  STRING
)
PARTITIONED BY (year INT, month INT)
STORED AS SEQUENCEFILE
LOCATION 's3n://polianaprod/legislation/senate_votes_by_bill/';

CREATE EXTERNAL TABLE bills.senate_votes_by_bill_external3 
LIKE bills.senate_votes_by_bill_external
LOCATION 's3n://polianaprod/legislation/senate_votes_by_bill3/';

CREATE EXTERNAL TABLE bills.house_votes_flat_external (
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

CREATE EXTERNAL TABLE bills.senate_votes_flat_external 
LIKE bills.house_votes_flat_external 
LOCATION 's3n://polianaprod/legislation/senate_votes_flat/';

nohup hive -e 'INSERT OVERWRITE TABLE bills.senate_votes_flat_external4 PARTITION (year, month) SELECT * FROM bills.view_joined_senate_votes_by_bill;'

CREATE EXTERNAL TABLE bills.house_votes_by_bill_external (
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

CREATE EXTERNAL TABLE bills.house_votes_by_bill_external2 
LIKE bills.house_votes_by_bill_external 
LOCATION 's3n://polianaprod/legislation/house_votes_by_bill2/';

CREATE EXTERNAL TABLE bills.house_votes_by_bill_external3 
LIKE bills.house_votes_by_bill_external 
LOCATION 's3n://polianaprod/legislation/house_votes_by_bill3/';

CREATE VIEW bills.view_bvotes_by_bill (
    bvote_id,
    byea_total,
    bnay_total,
    bnot_voting_total,
    bnot_present_total,
    byea_votes,
    bnay_votes,
    bnot_voting,
    bnot_present,
    bdate,
    bchamber,
    byear,
    bmonth
) as SELECT * FROM bills.votes_by_bill_embedded_external;

CREATE VIEW bills.view_remove_duplicates_votes (
    vote_id,
    yea_total,
    nay_total,
    not_voting_total,
    not_present_total,
    yea_votes,
    nay_votes,
    not_voting,
    not_present,
    date,
    chamber,
    year,
    month
)
as SELECT
    a.vote_id,
    CASE WHEN a.yea_total IS NOT NULL THEN a.yea_total ELSE b.byea_total END,
    CASE WHEN a.nay_total IS NOT NULL THEN a.nay_total ELSE b.bnay_total END,
    CASE WHEN a.not_voting_total IS NOT NULL THEN a.not_voting_total ELSE b.bnot_voting_total END,
    CASE WHEN a.not_present_total IS NOT NULL THEN a.not_present_total ELSE b.bnot_present_total END,
    CASE WHEN a.yea_votes IS NOT NULL THEN a.yea_votes ELSE b.byea_votes END,
    CASE WHEN a.nay_votes IS NOT NULL THEN a.nay_votes ELSE b.bnay_votes END,
    CASE WHEN a.not_voting IS NOT NULL THEN a.not_voting ELSE b.bnot_voting END,
    CASE WHEN a.not_present IS NOT NULL THEN a.not_present ELSE b.bnot_present END,
    CASE WHEN a.date IS NOT NULL THEN a.date ELSE b.bdate END,
    CASE WHEN a.chamber IS NOT NULL THEN a.chamber ELSE b.bchamber END,
    CASE WHEN a.year IS NOT NULL THEN a.year ELSE b.byear END,
    CASE WHEN a.month IS NOT NULL THEN a.month ELSE b.bmonth END
FROM bills.votes_by_bill_embedded_external a JOIN bills.view_bvotes_by_bill b
    ON a.vote_id = b.bvote_id AND a.date = b.bdate WHERE vote_id is not null;


SELECT 
    CASE WHEN religion IS NOT NULL THEN religion ELSE party END
FROM legislators LIMIT 100; 
