CREATE TABLE IF NOT EXISTS industry_codes (
    Catcode STRING,
    Catname STRING,
    Catorder STRING,
    Industry STRING,
    Sector STRING,
    Sector_Long STRING
)
ROW FORMAT DELIMITED FIELDS TERMINATED BY '\t'
LOCATION '/Users/dagilmore/Dev/polianalytics/poliana_processing/src/main/resources/data/industry_codes/';
