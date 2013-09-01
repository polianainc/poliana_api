add jar hive-serde-1.0.jar;add jar csv-serde-1.1.2.jar;

CREATE VIEW test_s47_contributions(bill_id, bioguide_id, vote, recipient_ext_id, transaction_id, contributor_ext_id, contributor_name, organization_name, amount, dates)
as SELECT v.bill_id, p.bioguide_id, v.vote, c.recipient_ext_id, c.transaction_id, c.contributor_ext_id, c.contributor_name, c.organization_name, c.amount, c.dates
FROM contributions_test c JOIN politicians p
ON c.recipient_ext_id = p.recipient_ext_id JOIN votes v
ON v.bill_id = 's47-113' AND p.bioguide_id = v.bioguide_id;

CREATE VIEW test_s47_yeas(y_contributor_ext_id, y_amount, y_count) as
SELECT contributor_ext_id, sum(amount), count(transaction_id) 
FROM s47_contributions WHERE vote = "yea"
GROUP BY contributor_ext_id;

CREATE VIEW test_s47_nays(n_contributor_ext_id, n_amount, n_count) as
SELECT contributor_ext_id, sum(amount), count(transaction_id) 
FROM s47_contributions WHERE vote = "nay"
GROUP BY contributor_ext_id;

CREATE VIEW test_s47_balanced(contributor_ext_id, y_amount, y_count, n_amount, n_count) AS
SELECT y.y_contributor_ext_id, y.y_amount, y.y_count, n.n_amount, n.n_count 
FROM test_s47_yeas y FULL OUTER JOIN test_s47_nays n 
WHERE y.y_contributor_ext_id = n.n_contributor_ext_id;

CREATE VIEW test_s47_difference(contributor_ext_id, amount_diff, count_diff) AS
SELECT y.y_contributor_ext_id, y.y_amount - n.n_amount, y.y_count - n.n_count 
FROM test_s47_yeas y FULL OUTER JOIN test_s47_nays n 
WHERE y.y_contributor_ext_id = n.n_contributor_ext_id;

CREATE VIEW s47_contributions(bill_id, bioguide_id, vote, recipient_ext_id, transaction_id, contributor_ext_id, contributor_name, organization_name, amount, dates)
as SELECT v.bill_id, p.bioguide_id, v.vote, c.recipient_ext_id, c.transaction_id, c.contributor_ext_id, c.contributor_name, c.organization_name, c.amount, c.dates
FROM contributions_test c JOIN politicians p
ON c.recipient_ext_id = p.recipient_ext_id JOIN votes v
ON v.bill_id = 's47-113' AND p.bioguide_id = v.bioguide_id;

CREATE VIEW s47_yeas(y_contributor_ext_id, y_amount, y_count) as
SELECT contributor_ext_id, sum(amount), count(transaction_id) 
FROM s47_contributions WHERE vote = "yea"
GROUP BY contributor_ext_id;

CREATE VIEW s47_nays(n_contributor_ext_id, n_amount, n_count) as
SELECT contributor_ext_id, sum(amount), count(transaction_id) 
FROM s47_contributions WHERE vote = "nay"
GROUP BY contributor_ext_id;

CREATE VIEW s47_balanced(contributor_ext_id, y_amount, y_count, n_amount, n_count) AS
SELECT y.y_contributor_ext_id, y.y_amount, y.y_count, n.n_amount, n.n_count 
FROM s47_yeas y FULL OUTER JOIN s47_nays n 
WHERE y.y_contributor_ext_id = n.n_contributor_ext_id;

CREATE VIEW s47_difference(contributor_ext_id, amount_diff, count_diff) AS
SELECT y.y_contributor_ext_id, y.y_amount - n.n_amount, y.y_count - n.n_count 
FROM s47_yeas y FULL OUTER JOIN s47_nays n 
WHERE y.y_contributor_ext_id = n.n_contributor_ext_id;

SELECT * FROM test_s47_yeas;

hive -hiveconf hive.root.logger=INFO,console;

hive -e 'add jar hive-serde-1.0.jar;add jar csv-serde-1.1.2.jar; SELECT * FROM test_s47_balanced;' > test_s47_balanced.tsv 

hive -e 'add jar hive-serde-1.0.jar;add jar csv-serde-1.1.2.jar; USE test_full; SELECT * FROM hr1068_113;' > hr1068_113.tsv
hive -e 'add jar hive-serde-1.0.jar;add jar csv-serde-1.1.2.jar; USE test_full; SELECT fec_trans_id FROM individual_contr;' crp_transaction_ids.tsv
hive -e 'add jar hive-serde-1.0.jar;add jar csv-serde-1.1.2.jar; SELECT transaction_id FROM fec_contributions;' > fec_transaction_ids.csv;
hive -e 'add jar hive-serde-1.0.jar;add jar csv-serde-1.1.2.jar; SELECT transaction_id, organization_ext_id, organization_name FROM fec_contributions;' > organizations.tsv
