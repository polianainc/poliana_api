use test_full;
add jar csv-serde-1.1.2.jar;
add jar hive-serde-1.0.jar;
add jar HiveSwarm-1.0-SNAPSHOT.jar;
create temporary function liberal_timestamp as 'com.livingsocial.hive.udf.UnixLiberalTimestamp';
create temporary function slash_timestamp as 'com.livingsocial.hive.udf.UnixSlashTimestamp';
create temporary function months_previous as 'com.livingsocial.hive.udf.MonthsPreviousTimestamp';

CREATE EXTERNAL TABLE bill_contributions_external(
    bill_introduction STRING,
    bioguide_id STRING,
    recipient_ext_id STRING,
    first_name STRING,
    last_name STRING,
    industry_id STRING,
    transaction_id STRING,
    amount INT,
    dates STRING
)
PARTITIONED BY (bill_id STRING, vote STRING)
LOCATION 's3n://polianatest/contributiontrends/alltime/';

CREATE VIEW s743_113_yeas(
    bill_id,
    bill_introduction,
    bioguide_id,
    recipient_ext_id,
    first_name,
    last_name,
    industry_id,
    transaction_id,
    vote,
    amount,
    dates
)
as SELECT
    votes.bill_id,
    votes.introduced_on,
    pols.bioguide_id,
    crp.recip_id,
    pols.first_name,
    pols.last_name,
    crp.real_code,
    crp.fec_trans_id,
    votes.vote,
    crp.amount,
    crp.dates
FROM individual_contr crp JOIN politicians_metadata pols
    ON crp.recip_id = pols.recipient_ext_id
    JOIN bills votes
        ON votes.bill_id = 's743-113' AND pols.bioguide_id = votes.bioguide_id AND votes.vote = 'yea';

INSERT OVERWRITE TABLE bill_contributions_external
    PARTITION (bill_id = "s743-113", vote = 'yea')
    SELECT
     bill_introduction,
     bioguide_id,
     recipient_ext_id,
     first_name,
     last_name,
     industry_id,
     transaction_id,
     amount,
     dates
    FROM
     s743_113_yeas;

--Grab industry contributions to politicians voting yes 
CREATE VIEW industry_contr_s743_113_yeas_6months(
     bill_introduction,
     bioguide_id,
     recipient_ext_id,
     first_name,
     last_name,
     industry_id,
     transaction_id,
     amount,
     dates
)
as SELECT bill_introduction, bioguide_id, recipient_ext_id, first_name,
        last_name, industry_id, transaction_id, amount, dates
    FROM bill_contributions_external
    WHERE
    bill_id = 's743-113'
    AND
    vote = 'yea'
    AND
    slash_timestamp(dates) BETWEEN months_previous(bill_introduction, 6) AND liberal_timestamp(bill_introduction);

CREATE VIEW s743_113_nays(
    bill_id,
    bill_introduction,
    bioguide_id,
    recipient_ext_id,
    first_name,
    last_name,
    industry_id,
    transaction_id,
    vote,
    amount,
    dates
)
as SELECT
    votes.bill_id,
    votes.introduced_on,
    pols.bioguide_id,
    crp.recip_id,
    pols.first_name,
    pols.last_name,
    crp.real_code,
    crp.fec_trans_id,
    votes.vote,
    crp.amount,
    crp.dates
FROM individual_contr crp JOIN politicians_metadata pols
    ON crp.recip_id = pols.recipient_ext_id
    JOIN bills votes
        ON votes.bill_id = 's743-113' AND pols.bioguide_id = votes.bioguide_id AND votes.vote = 'nay';

INSERT OVERWRITE TABLE bill_contributions_external
    PARTITION (bill_id = "s743-113", vote = 'nay')
    SELECT
     bill_introduction,
     bioguide_id,
     recipient_ext_id,
     first_name,
     last_name,
     industry_id,
     transaction_id,
     amount,
     dates
    FROM
     s743_113_nays;

--Grab industry contributions to politicians voting yes
CREATE VIEW industry_contr_s743_113_nays_6months(
     bill_introduction,
     bioguide_id,
     recipient_ext_id,
     first_name,
     last_name,
     industry_id,
     transaction_id,
     amount,
     dates
)
as SELECT bill_introduction, bioguide_id, recipient_ext_id, first_name,
        last_name, industry_id, transaction_id, amount, dates
    FROM bill_contributions_external
    WHERE
    bill_id = 's743-113'
    AND
    vote = 'nay'
    AND
    slash_timestamp(dates) BETWEEN months_previous(bill_introduction, 6) AND liberal_timestamp(bill_introduction);





