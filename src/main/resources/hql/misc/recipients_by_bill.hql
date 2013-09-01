SELECT p.bioguide_id, collect_set(c.transaction_id)
FROM contributions c LEFT OUTER JOIN politician_metadata p
WHERE c.recipient_ext_id = p.crp_id
GROUP BY p.bioguide_id;


--ON b.bill_id = 's47-113'; AND array_contains(b.yeas_ids, p.bioguide_id);


hive -e 'add jar hive-serde-1.0.jar;SELECT c.recipient_ext_id, c.contributor_ext_id, c.amount, c.dates FROM contributions c JOIN politician_metadata p ON c.recipient_ext_id = p.crp_id JOIN bills b ON b.bill_id = "s47-113";' > s47_113.tsv


SELECT v.bill_id, p.bioguide_id, c.recipient_ext_id, c.contributor_ext_id, c.contributor_name, c.organization_name, c.amount, c.dates
FROM contributions c JOIN politicians p
ON c.recipient_ext_id = p.recipient_ext_id JOIN votes v
ON v.bill_id = 's47-113' AND p.bioguide_id = v.bioguide_id;


hive -e 'add jar hive-serde-1.0.jar;add jar csv-serde-1.1.2.jar;SELECT p.bioguide_id, c.contributor_ext_id, c.amount, c.dates FROM contributions_test c JOIN politician_metadata p ON c.recipient_ext_id = p.crp_id;' > contributions.tsv


SELECT p.bioguide_id, c.transaction_id
FROM contributions c FULL OUTER JOIN politician_metadata p
ON c.recipient_ext_id = p.crp_id limit 10;

CREATE TABLE IF NOT EXISTS politicians
AS SELECT * FROM politician_metadata;

SELECT p.crp_id FROM
FROM politician_metadata p JOIN bills b
WHERE array_contains(b.yeas_ids, p.bioguide_id);

SELECT crp_id FROM politician_metadata WHERE
array_contains((SELECT yeas_ids FROM bills WHERE bill_id = 's47-113'), bioguide_id);

SELECT p.bioguide_id, c.recipient_ext_id, c.contributor_ext_id, c.contributor_name, c.organization_name, c.amount, c.dates
FROM contributions c JOIN politicians p
ON c.recipient_ext_id = p.recipient_ext_id;

CREATE VIEW s47_contributions(bill_id, bioguide_id, vote, recipient_ext_id, contributor_ext_id, contributor_name, organization_name, amount, dates)
as SELECT v.bill_id, p.bioguide_id, v.vote, c.recipient_ext_id, c.contributor_ext_id, c.contributor_name, c.organization_name, c.amount, c.dates
FROM contributions c JOIN politicians p
ON c.recipient_ext_id = p.recipient_ext_id JOIN votes v
ON v.bill_id = 's47-113' AND p.bioguide_id = v.bioguide_id;

hive -e 'add jar hive-serde-1.0.jar;add jar csv-serde-1.1.2.jar; SELECT contributor_ext_id, sum(amount), count('