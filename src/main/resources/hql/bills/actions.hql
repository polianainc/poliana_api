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