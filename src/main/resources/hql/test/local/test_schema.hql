CREATE TABLE IF NOT EXISTS s743_trends (
    industry_id STRING,
    yea_total_count STRING,
    nay_total_count INT,
    yea_distinct_count INT,
    nay_distinct_count INT,
    yea_sum DOUBLE,
    nay_sum DOUBLE
)
ROW FORMAT DELIMITED FIELDS TERMINATED BY ','
LOCATION '/Users/dagilmore/Dev/data_science/polianalytics/poliana_processing/src/main/resources/data/industry_trends/s743_trends/';

CREATE TABLE IF NOT EXISTS s954_trends (
    industry_id STRING,
    yea_total_count INT,
    nay_total_count INT,
    yea_distinct_count INT,
    nay_distinct_count INT,
    yea_sum DOUBLE,
    nay_sum DOUBLE
)
ROW FORMAT DELIMITED FIELDS TERMINATED BY ','
LOCATION '/Users/dagilmore/Dev/data_science/polianalytics/poliana_processing/src/main/resources/data/industry_trends/s954_trends/';

CREATE TABLE IF NOT EXISTS hres198_trends (
    industry_id STRING,
    yea_total_count INT,
    nay_total_count INT,
    yea_distinct_count INT,
    nay_distinct_count INT,
    yea_sum DOUBLE,
    nay_sum DOUBLE
)
ROW FORMAT DELIMITED FIELDS TERMINATED BY ','
LOCATION '/Users/dagilmore/Dev/data_science/polianalytics/poliana_processing/src/main/resources/data/industry_trends/hres198_trends/';

CREATE TABLE IF NOT EXISTS hr2397_trends (
    industry_id STRING,
    yea_total_count INT,
    nay_total_count INT,
    yea_distinct_count INT,
    nay_distinct_count INT,
    yea_sum DOUBLE,
    nay_sum DOUBLE
)
ROW FORMAT DELIMITED FIELDS TERMINATED BY ','
LOCATION '/Users/dagilmore/Dev/data_science/polianalytics/poliana_processing/src/main/resources/data/industry_trends/hr2397_trends/';

