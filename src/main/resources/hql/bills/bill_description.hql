nohup hive -e 'INSERT OVERWRITE TABLE bills.bills_overview_flat_thomas_ids PARTITION (congress, bill_type) SELECT * FROM bills.view_bill_overview2_expl_subjects;'


nohup hive -e 'INSERT OVERWRITE TABLE bills.bills_overview
               PARTITION (congress, bill_type)
               SELECT
                   bill_id,
                   official_title,
                   popular_title,
                   short_title,
                   sponsor_name,
                   sponsor_state,
                   sponsor_id,
                   collect_set(cosponsor_id),
                   top_subject,
                   collect_set(subject),
                   summary,
                   introduced_at,
                   congress,
                   bill_type
               FROM bills.bills_overview_flat
               GROUP BY
                   bill_id,
                   official_title,
                   popular_title,
                   short_title,
                   sponsor_name,
                   sponsor_state,
                   sponsor_id,
                   top_subject,
                   summary,
                   introduced_at,
                   congress,
                   bill_type;'

CREATE VIEW bills.bills_general_overview (
      bill_id,
      official_title,
      popular_title,
      short_title,
      sponsor_name,
      sponsor_state,
      sponsor_id,
      cosponsor_ids,
      top_subject,
      subjects,
      summary,
      introduced_at,
      house_passage_result,
      house_passage_result_at,
      senate_cloture_result,
      senate_cloture_result_at,
      senate_passage_result,
      senate_passage_result_at,
      awaiting_signature,
      enacted,
      vetoed,
      enacted_at,
      congress,
      bill_type
)
as SELECT
      b.bill_id,
      b.official_title,
      b.popular_title,
      b.short_title,
      b.sponsor_name,
      b.sponsor_state,
      b.sponsor_id,
      b.cosponsor_ids,
      b.top_subject,
      b.subjects,
      b.summary,
      b.introduced_at,
      h.house_passage_result,
      h.house_passage_result_at,
      h.senate_cloture_result,
      h.senate_cloture_result_at,
      h.senate_passage_result,
      h.senate_passage_result_at,
      h.awaiting_signature,
      h.enacted,
      h.vetoed,
      h.enacted_at,
      b.congress,
      b.bill_type
FROM bills.bills_overview b FULL OUTER JOIN bills.view_bill_history h
    ON bill_id_resolve(b.bill_id) = h.bill_id;



INSERT OVERWRITE TABLE bills.bills_overview_flat
PARTITION (congress, bill_type)
SELECT * FROM bills.view_bill_overview4_flat_cosponsor_bioguide;

INSERT OVERWRITE TABLE bills.bills_overview
PARTITION (congress, bill_type)
SELECT 
    bill_id,
    official_title,
    popular_title,
    short_title,
    sponsor_name,
    sponsor_state,
    sponsor_bioguide,
    collect_set(cosponsor_id),
    top_subject,
    collect_set(subject),
    summary,
    introduced_at,
    congress,
    bill_type
FROM bills.view_bill_overview4_flat_cosponsor_bioguide
GROUP BY
    bill_id,
    official_title,
    popular_title,
    short_title,
    sponsor_name,
    sponsor_state,
    sponsor_bioguide,
    top_subject,
    summary,
    introduced_at,
    congress,
    bill_type;



CREATE VIEW bills.view_bill_history (
     bill_id,
     house_passage_result,
     house_passage_result_at,
     senate_cloture_result,
     senate_cloture_result_at,
     senate_passage_result,
     senate_passage_result_at,
     awaiting_signature,
     enacted,
     vetoed,
     enacted_at
)
as SELECT
     bill_id,
     history.house_passage_result,
     history.house_passage_result_at,
     history.senate_cloture_result,
     history.senate_cloture_result_at,
     history.senate_passage_result,
     history.senate_passage_result_at,
     history.awaiting_signature,
     history.enacted,
     history.vetoed,
     history.enacted_at
FROM bills.bills_json1;

CREATE VIEW bills.view_vote_arrays (
    bill_id,
    yeas,
    nays,
    not_voting,
    present
)
as SELECT
    vote_id_resolve(vote_id),
    votes.Yea,
    votes.Nay,
    votes.Not_Voting,
    votes.Present
FROM bills.votes_json;

CREATE VIEW bills.view_votes_flat (
    bill_id,
    yea,
    nay,
    not_voting,
    present
)
as SELECT
    bill_id,
    yea,
    nay,
    no_vote,
    attending
FROM  bills.view_vote_arrays
     LATERAL VIEW explode(yeas) yeas AS yea
     LATERAL VIEW explode(nays) nays AS nay
     LATERAL VIEW explode(not_voting) not_voting AS no_vote
     LATERAL VIEW explode(present) present AS attending;

CREATE VIEW bills.view_bills_and_votes (
    bill_id,
    official_title,
    popular_title,
    short_title,
    sponsor_name,
    sponsor_state,
    sponsor_id,
    cosponsor_ids,
    top_subject,
    subjects,
    summary,
    introduced_at,
    yeas,
    nays,
    not_voting,
    present,
    congress,
    bill_type
)
as SELECT
    b.bill_id,
    b.official_title,
    b.popular_title,
    b.short_title,
    b.sponsor_name,
    b.sponsor_state,
    b.sponsor_id,
    b.cosponsor_ids,
    b.top_subject,
    b.subjects,
    b.summary,
    b.introduced_at,
    v.yeas,
    v.nays,
    v.not_voting,
    v.present,
    b.congress,
    b.bill_type
FROM
    bills.bills_overview b JOIN bills.view_vote_arrays v
    ON bill_id_resolve(b.bill_id) = v.bill_id;

 CREATE VIEW bills.view_bill (
     bill_id,
     official_title,
     popular_title,
     short_title,
     sponsor_name,
     sponsor_state,
     sponsor_id,
     cosponsor_ids,
     top_subject,
     subjects,
     summary,
     introduced_at,
     house_passage_result,
     house_passage_result_at,
     senate_cloture_result,
     senate_cloture_result_at,
     senate_passage_result_at,
     awaiting_signature,
     enacted,
     vetoed,
     enacted_at,
     yeas,
     nays,
     not_voting,
     present,
     congress,
     bill_type
 )
 as SELECT
     b.bill_id,
     b.official_title,
     b.popular_title,
     b.short_title,
     b.sponsor_name,
     b.sponsor_state,
     b.sponsor_id,
     b.cosponsor_ids,
     b.top_subject,
     b.subjects,
     b.summary,
     b.introduced_at,
     h.house_passage_result,
     h.house_passage_result_at,
     h.senate_cloture_result,
     h.senate_cloture_result_at,
     h.senate_passage_result_at,
     h.awaiting_signature,
     h.enacted,
     h.vetoed,
     h.enacted_at,
     b.yeas,
     b.nays,
     b.not_voting,
     b.present,
     b.congress,
     b.bill_type
 FROM
     bills.view_bills_and_votes b JOIN bills.view_bill_history h
     ON b.bill_id = h.bill_id;

CREATE TABLE bills.bills (
    bill_id STRING,
    official_title STRING,
    popular_title STRING,
    short_title STRING,
    sponsor_name STRING,
    sponsor_state STRING,
    sponsor_id STRING,
    cosponsor_ids ARRAY<STRING>,
    top_subject STRING,
    subjects ARRAY<STRING>,
    summary STRING,
    introduced_at STRING,
    house_passage_result STRING,
    house_passage_result_at STRING,
    senate_cloture_result STRING,
    senate_cloture_result_at STRING,
    senate_passage_result_at STRING,
    awaiting_signature BOOLEAN,
    enacted BOOLEAN,
    vetoed BOOLEAN,
    enacted_at STRING,
    yeas ARRAY<STRUCT<
        display_name: STRING,
        first_name: STRING,
        id: STRING,
        last_name: STRING,
        party: STRING,
        state: STRING
    >>,
    nays ARRAY<STRUCT<
        display_name: STRING,
        first_name: STRING,
        id: STRING,
        last_name: STRING,
        party: STRING,
        state: STRING
    >>,
    not_voting ARRAY<STRUCT<
        display_name: STRING,
        first_name: STRING,
        id: STRING,
        last_name: STRING,
        party: STRING,
        state: STRING
    >>,
    present ARRAY<STRUCT<
        display_name: STRING,
        first_name: STRING,
        id: STRING,
        last_name: STRING,
        party: STRING,
        state: STRING
    >>
)
PARTITIONED BY (congress STRING, bill_type STRING)
 STORED AS SEQUENCEFILE;

 CREATE TABLE bills.bills (
     bill_id STRING,
     official_title STRING,
     popular_title STRING,
     short_title STRING,
     sponsor_name STRING,
     sponsor_state STRING,
     sponsor_id STRING,
     cosponsor_ids ARRAY<STRING>,
     top_subject STRING,
     subjects ARRAY<STRING>,
     summary STRING,
     introduced_at STRING,
     house_passage_result STRING,
     house_passage_result_at STRING,
     senate_cloture_result STRING,
     senate_cloture_result_at STRING,
     senate_passage_result_at STRING,
     awaiting_signature BOOLEAN,
     enacted BOOLEAN,
     vetoed BOOLEAN,
     enacted_at STRING,
     yeas ARRAY<STRUCT<
         display_name: STRING,
         first_name: STRING,
         id: STRING,
         last_name: STRING,
         party: STRING,
         state: STRING
     >>,
     nays ARRAY<STRUCT<
         display_name: STRING,
         first_name: STRING,
         id: STRING,
         last_name: STRING,
         party: STRING,
         state: STRING
     >>,
     not_voting ARRAY<STRUCT<
         display_name: STRING,
         first_name: STRING,
         id: STRING,
         last_name: STRING,
         party: STRING,
         state: STRING
     >>,
     present ARRAY<STRUCT<
         display_name: STRING,
         first_name: STRING,
         id: STRING,
         last_name: STRING,
         party: STRING,
         state: STRING
     >>
 )
 PARTITIONED BY (congress STRING, bill_type STRING)
 LOCATION 's3n://polianaprod/legislation/bills_'

