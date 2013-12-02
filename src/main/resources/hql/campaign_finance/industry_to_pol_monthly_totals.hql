CREATE TABLE campaign_finance.industry_to_pol_contribution_monthly_totals (
    bioguide_id STRING,
    party STRING,
    industry_id STRING,
    contributions_count INT,
    contributions_total INT
)
PARTITIONED BY (year INT, month INT)
STORED AS SEQUENCEFILE;
LOCATION 's3n://polianaprod/campaign_finance/industry_to_pol_contribution_monthly_totals/';

SELECT DISTINCT party FROM campaign_finance.view_industry_to_pol_contribution_monthly_totals;