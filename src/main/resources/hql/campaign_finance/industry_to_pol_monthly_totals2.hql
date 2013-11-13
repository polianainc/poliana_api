CREATE VIEW campaign_finance.view_individual_contributions (
    cycle,
    fec_trans_id,
    contrib_id,
    contrib,
    recip_id,
    org_name,
    ult_org,
    real_code,
    dates,
    amount,
    street,
    city,
    state,
    zip,
    recip_code,
    type,
    cmtel_id,
    other_id,
    gender,
    microfilm,
    occupation,
    employer,
    source,
    year,
    month
) as SELECT
    cycle,
    fec_trans_id,
    contrib_id,
    contrib,
    recip_id,
    org_name,
    ult_org,
    real_code,
    dates,
    amount,
    street,
    city,
    state,
    zip,
    recip_code,
    type,
    cmtel_id,
    other_id,
    gender,
    microfilm,
    occupation,
    employer,
    source,
    year(from_unixtime(slash_date(dates))),
    month(from_unixtime(slash_date(dates)))
FROM campaign_finance.individual_contributions_external;

CREATE TABLE campaign_finance.individual_contributions (
    cycle STRING,
    fec_trans_id STRING,
    contrib_id STRING,
    contrib STRING,
    recip_id STRING,
    org_name STRING,
    ult_org STRING,
    real_code STRING,
    dates STRING,
    amount INT,
    street STRING,
    city STRING,
    state STRING,
    zip STRING,
    recip_code STRING,
    type STRING,
    cmtel_id STRING,
    other_id STRING,
    gender STRING,
    microfilm STRING,
    occupation STRING,
    employer STRING,
    source STRING
)
PARTITIONED BY (year INT, month INT)
STORED AS SEQUENCEFILE;

INSERT OVERWRITE TABLE campaign_finance.individual_contributions 
PARTITION (year,month) SELECT * FROM campaign_finance.view_individual_contributions;

CREATE EXTERNAL TABLE campaign_finance.industry_to_pol_contribution_monthly_totals_external (
    opensecrets_id STRING,
    bioguide_id STRING,
    party STRING,
    industry_id STRING,
    contributions_count INT,
    contributions_total INT
)
PARTITIONED BY (year INT, month INT)
STORED AS SEQUENCEFILE
LOCATION 's3n://polianaprod/campaign_finance/industry_to_pol_contribution_monthly_totals/';

CREATE VIEW campaign_finance.view_industry_to_pol_contribution_monthly_totals (
    opensecrets_id,
    industry_id,
    contributions_count,
    contributions_total,
    year,
    month
) as SELECT
    recip_id,
    real_code,
    count(amount),
    sum(amount),
    year,
    month
FROM campaign_finance.individual_contributions
    GROUP BY
    recip_id,
    real_code,
    year,
    month;


CREATE EXTERNAL TABLE campaign_finance.industry_to_pol_contribution_monthly_totals_external (
    bioguide_id STRING,
    party STRING,
    industry_id STRING,
    contributions_count INT,
    contributions_total INT
)
PARTITIONED BY (year INT, month INT)
STORED AS SEQUENCEFILE
LOCATION 's3n://polianaprod/campaign_finance/industry_to_pol_contribution_monthly_totals/';


nohup hive -e 'INSERT OVERWRITE TABLE campaign_finance.industry_to_pol_contribution_monthly_totals2 PARTITION (year, month) SELECT * FROM campaign_finance.view_industry_to_pol_contribution_monthly_totals;'

INSERT OVERWRITE TABLE campaign_finance.industry_to_pol_contribution_monthly_totals_external 
PARTITION (year, month) 
SELECT * FROM campaign_finance.view_industry_to_pol_contribution_monthly_totals;

CREATE VIEW campaign_finance.view_industry_to_pol_contribution_monthly_totals (
    opensecrets_id,
    bioguide_id,
    party,
    industry_id,
    contributions_count,
    contributions_total,
    year,
    month    
) as SELECT
    c.opensecrets_id,
    l.bioguide_id,
    l.party,
    c.industry_id,
    c.contributions_count,
    c.contributions_total,
    c.year,
    c.month   
FROM campaign_finance.industry_to_pol_contribution_monthly_totals2 c FULL OUTER JOIN
    entities.legislators_external l ON c.opensecrets_id = l.opensecrets_id;
