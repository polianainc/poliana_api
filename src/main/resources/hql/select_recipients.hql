set fs.s3n.awsAccessKeyId=AKIAI27OS66EVU7XE57A;
set fs.s3n.awsSecretAccessKey=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;

add jar /usr/local/csv-serde-1.1.2.jar;
add jar /usr/local/hive-serde-1.0.jar;

SELECT v.bill_id, p.bioguide_id, c.recipient_ext_id, c.contributor_ext_id, c.contributor_name, c.organization_name, c.amount, c.dates
FROM contributions_test c JOIN politicians p
ON c.recipient_ext_id = p.recipient_ext_id JOIN votes v
ON v.bill_id = 's47-113' AND p.bioguide_id = v.bioguide_id;