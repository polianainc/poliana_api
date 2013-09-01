--PROD DESTROY SCRIPT

USE prod;

add jar csv-serde-1.1.2.jar;
add jar hive-serde-1.0.jar;

DROP TABLE IF EXISTS bills;
DROP TABLE IF EXISTS candidate_contr;
DROP TABLE IF EXISTS committee_contr;
DROP TABLE IF EXISTS individual_contr;
DROP TABLE IF EXISTS pac_to_candidate_contr;
DROP TABLE IF EXISTS pac_to_pac_contr;
DROP TABLE IF EXISTS fec_contributions;
DROP TABLE IF EXISTS expenditures;
DROP TABLE IF EXISTS politicians_metadata;
DROP TABLE IF EXISTS industry_codes;
DROP TABLE IF EXISTS cong_cmte_codes;
DROP TABLE IF EXISTS candidate_ids;
DROP DATABASE IF EXISTS prod;
