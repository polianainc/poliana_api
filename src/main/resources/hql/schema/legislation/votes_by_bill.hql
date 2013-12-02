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



CREATE EXTERNAL TABLE bills.votes_by_bill_embedded (
    vote_id STRING,
    vote STRING,
    total INT,
    votes STRING
)
PARTITIONED BY (year INT, month INT)
STORED AS SEQUENCEFILE
LOCATION 's3n://polianaprod/legislation/votes_by_bill_embedded/';

SELECT vote_id, total, regexp_replace(votes, " \\| ", "") FROM votes_by_bill_embedded3 LIMIT 2;

CREATE VIEW bills.view_yea_votes_by_bill (
    vote_id,
    yea_total,
    yea_votes,
    year,
    month
)
as SELECT vote_id, total, regexp_replace(votes, " \\| ", ""), year, month FROM votes_by_bill_embedded3 WHERE vote = 'yea';

CREATE VIEW bills.view_nay_votes_by_bill (
    vote_id,
    nay_total,
    nay_votes
)
as SELECT vote_id, total, regexp_replace(votes, " \\| ", "") FROM votes_by_bill_embedded3 WHERE vote = 'nay';

CREATE VIEW bills.view_not_voting_votes_by_bill (
    vote_id,
    not_voting_total,
    not_voting
)
as SELECT vote_id, total, regexp_replace(votes, " \\| ", "") FROM votes_by_bill_embedded3 WHERE vote = 'not_voting';

CREATE VIEW bills.view_present_votes_by_bill (
    vote_id,
    present_total,
    present
)
as SELECT vote_id, total, regexp_replace(votes, " \\| ", "") FROM votes_by_bill_embedded3 WHERE vote = 'present';

CREATE VIEW bills.view_votes_by_bill (
    vote_id,
    yea_total,
    nay_total,
    not_voting_total,
    present_total,
    yea_votes,
    nay_votes,
    not_voting,
    present,
    year,
    month    
)
as SELECT
    y.vote_id,
    y.yea_total,
    n.nay_total,
    no.not_voting_total,
    p.present_total,
    y.yea_votes,
    n.nay_votes,
    no.not_voting,
    p.present,
    y.year,
    y.month
FROM bills.view_yea_votes_by_bill y 
     FULL OUTER JOIN bills.view_nay_votes_by_bill n ON y.vote_id = n.vote_id
     FULL OUTER JOIN bills.view_not_voting_votes_by_bill no ON n.vote_id = no.vote_id
     FULL OUTER JOIN bills.view_present_votes_by_bill p ON no.vote_id = p.vote_id;

nohup hive -e 'INSERT OVERWRITE TABLE bills.votes_by_bill_embedded_external PARTITION (year,month) SELECT * FROM bills.view_votes_by_bill;' 


CREATE EXTERNAL TABLE bills.votes_by_bill_embedded_external (
    vote_id STRING,
    yea_total INT,
    nay_total INT,
    not_voting_total INT,
    present_total INT,
    yea_votes STRING,
    nay_votes STRING,
    not_voting STRING,
    present STRING
)
PARTITIONED BY (year INT, month INT)
STORED AS SEQUENCEFILE
LOCATION 's3n://polianaprod/legislation/votes_by_bill_embedded/';
