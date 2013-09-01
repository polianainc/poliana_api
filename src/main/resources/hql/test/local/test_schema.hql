CREATE TABLE IF NOT EXISTS s743_financials(
    industry_id STRING,
    total_yea_count INT,
    total_nay_count INT,
    total_diff INT,
    distinct_yea_count INT,
    distinct_nay_count INT,
    distinct_diff INT
)
ROW FORMAT DELIMITED FIELDS TERMINATED BY ','
LOCATION '/Users/dagilmore/Dev/data_science/polianalytics/poliana_processing/src/main/resources/data/';