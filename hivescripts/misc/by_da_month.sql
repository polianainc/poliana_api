hive -e 'add jar csv-serde-1.1.2.jar; SELECT v.bill_id, v.vote, p.bioguide_id, c.recipient_ext_id, c.contributor_ext_id, c.contributor_name, c.organization_name, c.amount, c.dates FROM contributions c JOIN politicians p ON c.recipient_ext_id = p.recipient_ext_id JOIN votes v ON v.bill_id = "s47-113" AND p.bioguide_id = v.bioguide_id WHERE UNIX_TIMESTAMP(CONCAT(c.dates, " 00:00:00")) >= UNIX_TIMESTAMP(CONCAT(v.introduced_on, " 00:00:00")) - 2629743 AND UNIX_TIMESTAMP(CONCAT(c.dates, " 00:00:00")) <= UNIX_TIMESTAMP(CONCAT(v.introduced_on, " 00:00:00")) + 2629743;' | tr "\01" "," > contributions_s47_113_month.csv



add jar csv-serde-1.1.2.jar; 
SELECT v.bill_id, v.vote, p.bioguide_id, c.recipient_ext_id, c.contributor_ext_id, c.contributor_name, c.organization_name, c.amount, c.dates 
FROM contributions c JOIN politicians p ON c.recipient_ext_id = p.recipient_ext_id JOIN votes v 
ON v.bill_id = "s47-113" AND p.bioguide_id = v.bioguide_id
WHERE
UNIX_TIMESTAMP(CONCAT(c.dates, ' 00:00:00')) >= UNIX_TIMESTAMP(CONCAT(v.introduced_on, ' 00:00:00')) - 2629743 AND 
UNIX_TIMESTAMP(CONCAT(c.dates, ' 00:00:00')) <= UNIX_TIMESTAMP(CONCAT(v.introduced_on, ' 00:00:00')) + 2629743;


WHERE UNIX_TIMESTAMP(CONCAT(c.dates, ' 00:00:00')) >= UNIX_TIMESTAMP(CONCAT(v.introduced_on, ' 00:00:00')) - 2629743 AND UNIX_TIMESTAMP(CONCAT(c.dates, ' 00:00:00')) <= UNIX_TIMESTAMP(CONCAT(v.introduced_on, ' 00:00:00')) + 2629743;