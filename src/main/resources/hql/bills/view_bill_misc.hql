CREATE VIEW bills.view_house_votes_by_bill_yea (
    yea_vote_id,
    yea_vote,
    yea_date,
    yea_total,
    yea_votes,
    yea_year,
    yea_month
)
as SELECT
    vote_id,
    vote,
    date,
    COUNT(DISTINCT(bioguide_id)),
    COLLECT_SET(CONCAT_WS(",",bioguide_id,display_name,first_name,last_name,party,state,district,religion)),
    year,
    month
FROM bills.house_votes_flat_external WHERE vote = 'yea' GROUP BY
    vote_id,
    vote,
    date,
    year,
    month;

CREATE VIEW bills.view_house_votes_by_bill_nay (
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
    COLLECT_SET(CONCAT_WS(",",bioguide_id,display_name,first_name,last_name,party,state,district,religion)),
    year,
    month
FROM bills.house_votes_flat_external WHERE vote = 'nay' GROUP BY
    vote_id,
    vote,
    date,
    year,
    month;

CREATE VIEW bills.view_house_votes_by_bill_present (
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
    COLLECT_SET(CONCAT_WS(",",bioguide_id,display_name,first_name,last_name,party,state,district,religion)),
    year,
    month
FROM bills.house_votes_flat_external WHERE vote = 'not_present' GROUP BY
    vote_id,
    vote,
    date,
    year,
    month;

CREATE VIEW bills.view_house_votes_by_bill_not_voting (
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
    COLLECT_SET(CONCAT_WS(",",bioguide_id,display_name,first_name,last_name,party,state,district,religion)),
    year,
    month
FROM bills.house_votes_flat_external WHERE vote = 'not_voting' GROUP BY
    vote_id,
    vote,
    date,
    year,
    month;

CREATE VIEW bills.view_joined_house_votes_by_bill (
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
FROM bills.view_house_votes_by_bill_yea y FULL OUTER JOIN bills.view_house_votes_by_bill_nay n 
     ON y.yea_vote_id = n.nay_vote_id AND y.yea_date = n.nay_date FULL OUTER JOIN bills.view_house_votes_by_bill_present p
     ON n.nay_vote_id = p.present_vote_id AND n.nay_date = p.present_date FULL OUTER JOIN bills.view_house_votes_by_bill_not_voting nv
     ON p.present_vote_id = nv.not_voting_vote_id AND p.present_date = nv.not_voting_date;

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
LOCATION 's3n://polianaprod/legislation/house_votes_by_bill3/';

nohup hive -e 'INSERT OVERWRITE TABLE bills.house_votes_by_bill_external PARTITION (year,month) SELECT * FROM bills.view_joined_house_votes_by_bill;
INSERT OVERWRITE TABLE bills.senate_votes_by_bill_external2 PARTITION (year,month) SELECT * FROM bills.view_joined_senate_votes_by_bill;'


