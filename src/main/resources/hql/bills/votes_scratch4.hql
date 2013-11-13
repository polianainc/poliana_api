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
) as SELECT DISTINCT
    v.vote_id,
    'yea',
    v.date,
    l.bioguide_id,
    l.official_full,
    l.first_name,
    l.last_name,
    l.party,
    v.state,
    l.district,
    l.religion,
    v.year,
    v.month
FROM
bills.votes_flat_external v FULL OUTER JOIN entities.senate_terms_external l
    ON v.id = l.bioguide_id WHERE vote = 'yea' and SUBSTR(vote_id,0,1) = 's';

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
) as SELECT DISTINCT
    v.vote_id,
    'nay',
    v.date,
    l.bioguide_id,
    l.official_full,
    l.first_name,
    l.last_name,
    l.party,
    v.state,
    l.district,
    l.religion,
    v.year,
    v.month
FROM
bills.votes_flat_external v FULL OUTER JOIN entities.senate_terms_external l
    ON v.id = l.bioguide_id WHERE vote = 'nay' and SUBSTR(vote_id,0,1) = 's';

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
) as SELECT DISTINCT
    v.vote_id,
    'not_present',
    v.date,
    l.bioguide_id,
    l.official_full,
    l.first_name,
    l.last_name,
    l.party,
    v.state,
    l.district,
    l.religion,
    v.year,
    v.month
FROM
bills.votes_flat_external v FULL OUTER JOIN entities.senate_terms_external l
    ON v.id = l.bioguide_id WHERE vote = 'present' and SUBSTR(vote_id,0,1) = 's';

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
) as SELECT DISTINCT
    v.vote_id,
    'not_voting',
    v.date,
    l.bioguide_id,
    l.official_full,
    l.first_name,
    l.last_name,
    l.party,
    v.state,
    l.district,
    l.religion,
    v.year,
    v.month
FROM
bills.votes_flat_external v FULL OUTER JOIN entities.senate_terms_external l
    ON v.id = l.bioguide_id WHERE vote = 'not_voting' and SUBSTR(vote_id,0,1) = 's';

CREATE EXTERNAL TABLE bills.senate_votes_flat_external2 LIKE bills.senate_votes_flat_external LOCATION 's3n://polianaprod/legislation/senate_votes_flat2/';

INSERT INTO TABLE bills.senate_votes_flat_external2 PARTITION (year, month) SELECT * FROM bills.view_senate_votes_flat_yeas;
INSERT INTO TABLE bills.senate_votes_flat_external2 PARTITION (year, month) SELECT * FROM bills.view_senate_votes_flat_nays;
INSERT INTO TABLE bills.senate_votes_flat_external2 PARTITION (year, month) SELECT * FROM bills.view_senate_votes_flat_present;
INSERT INTO TABLE bills.senate_votes_flat_external2 PARTITION (year, month) SELECT * FROM bills.view_senate_votes_flat_not_voting;









