 CREATE EXTERNAL TABLE bills.bill_meta_external (
    bill_id STRING,
    vote_id STRING,
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
    introduced_at INT,
    house_passage_result STRING,
    house_passage_result_at INT,
    senate_cloture_result STRING,
    senate_cloture_result_at INT,
    senate_passage_result STRING,
    senate_passage_result_at INT,
    awaiting_signature BOOLEAN,
    enacted BOOLEAN,
    vetoed BOOLEAN,
    enacted_at INT,
    status STRING,
    status_at INT
)
PARTITIONED BY (congress INT, bill_type STRING)
STORED AS SEQUENCEFILE
LOCATION 's3n://polianaprod/legislation/bills_meta/';

for file in os.listdir("."):
    w = open("tmp/{0}".format(file),'wb')
    w.write(open(file,'rb').read().rstrip('\n'))

CREATE VIEW bills.view_bill_sponsorship_flat (
    bill_id,
    sponsor_id,
    cosponsor_id,
    introduced_at,
    bill_type,
    congress,
    year,
    month
) as SELECT 
    bill_id,
    sponsor_id,
    cosponsor_id,
    introduced_at,
    bill_type,
    congress,
    year(from_unixtime(introduced_at)),
    month(from_unixtime(introduced_at))
FROM bills.bill_meta_external 
LATERAL VIEW explode(cosponsor_ids) cosponsor_ids AS cosponsor_id;

CREATE TABLE bills.bill_sponsorship_flat_external (
    bill_id STRING,
    sponsor_id STRING,
    cosponsor_id STRING,
    introduced_at INT,
    bill_type STRING

)
PARTITIONED BY (congress INT, year INT, month INT)
STORED AS SEQUENCEFILE
LOCATION 's3n://polianaprod/legislation/bill_sponsorship_flat/';

nohup hive -e 'INSERT OVERWRITE TABLE bills.bill_sponsorship_flat_external 
PARTITION (congress, year, month)
SELECT * FROM bills.view_bill_sponsorship_flat;'


CREATE VIEW bills.view_bill_sponsorship_counts_monthly (
    sponsor_id,
    cosponsor_id,
    cosponsor_count,
    bill_type,
    congress,
    year,
    month
)
as SELECT 
    sponsor_id,
    cosponsor_id,
    count(cosponsor_id),
    bill_type,
    congress,
    year,
    month
FROM bills.bill_sponsorship_flat_external
GROUP BY 
    sponsor_id,
    cosponsor_id,
    bill_type,
    congress,
    year,
    month;

 

CREATE TABLE bills.bill_sponsorship_counts_monthly (
    sponsor_id STRING,
    cosponsor_id STRING,
    cosponsor_count INT,    
    bill_type STRING
)
PARTITIONED BY (congress INT, year INT, month INT);

CREATE EXTERNAL TABLE bills.bill_sponsorship_counts_monthly (
    sponsor_id STRING,
    cosponsor_id STRING,
    cosponsor_count INT,    
    bill_type STRING
)
PARTITIONED BY (congress INT, year INT, month INT)
STORED AS SEQUENCEFILE
LOCATION 's3n://polianaprod/legislation/bill_sponsorship_counts_monthly/'; 

nohup hive -e 'INSERT OVERWRITE TABLE bills.bill_sponsorship_counts_monthly_external 
PARTITION (congress, year, month)
SELECT * FROM bills.view_bill_sponsorship_counts_monthly;'