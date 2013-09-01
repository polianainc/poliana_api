SELECT sum(amount) FROM industry_contr_s743_113_yeas_6months;
SELECT industry_id, sum(amount) FROM industry_contr_s743_113_yeas_6months GROUP BY industry_id;
SELECT count(distinct(bioguide_id)) FROM industry_contr_s743_113_nays_6months;
SELECT industry_id, count(distinct(bioguide_id)) FROM industry_contr_s743_113_yeas_6months GROUP BY industry_id;


CREATE VIEW s743_bias_yeas_total_count(industry_id, yea_count) as
    SELECT industry_id, count(bioguide_id)
    FROM industry_contr_s743_113_yeas_6months
    GROUP BY industry_id;

CREATE VIEW s743_bias_nays_total_count(industry_id, nay_count) as
    SELECT industry_id, count(bioguide_id)
    FROM industry_contr_s743_113_nays_6months
    GROUP BY industry_id;

CREATE VIEW s743_bias_yeas_dcount(industry_id, yea_count) as
    SELECT industry_id, count(distinct(bioguide_id))
    FROM industry_contr_s743_113_yeas_6months
    GROUP BY industry_id;

CREATE VIEW s743_bias_nays_dcount(industry_id, nay_count) as
    SELECT industry_id, count(distinct(bioguide_id))
    FROM industry_contr_s743_113_nays_6months
    GROUP BY industry_id;

CREATE VIEW s743_yea_sums(industry_id, yea_sum) as
    SELECT industry_id, sum(amount)
    FROM industry_contr_s743_113_yeas_6months
    GROUP BY industry_id;

CREATE VIEW s743_nay_sums(industry_id, nay_sum) as
    SELECT industry_id, sum(amount)
    FROM industry_contr_s743_113_nays_6months
    GROUP BY industry_id;

CREATE VIEW s743_total_bias(industry_id, yea_total_count, nay_total_count) as
    SELECT y.industry_id, y.yea_count, n.nay_count
    FROM s743_bias_yeas_total_count y FULL OUTER JOIN s743_bias_nays_total_count n
    ON y.industry_id = n.industry_id;

CREATE VIEW s743_distinct_bias(industry_id, yea_distinct_count, nay_distinct_count) as
    SELECT y.industry_id, y.yea_count, n.nay_count
    FROM s743_bias_yeas_dcount y FULL OUTER JOIN s743_bias_nays_dcount n
    ON y.industry_id = n.industry_id;

CREATE VIEW s743_magnitude(industry_id, yea_sum, nay_sum) as
    SELECT y.industry_id, y.yea_sum, n.nay_sum
    FROM s743_yea_sums y FULL OUTER JOIN s743_nay_sums n
    ON y.industry_id = n.industry_id;

CREATE VIEW s743_trends(
    industry_id,
    yea_total_count,
    nay_total_count,
    yea_distinct_count,
    nay_distinct_count,
    yea_sum,
    nay_sum
    )
 as SELECT
    t.industry_id,
    t.yea_total_count,
    t.nay_total_count,
    d.yea_distinct_count,
    d.nay_distinct_count,
    s.yea_sum,
    s.nay_sum
    FROM s743_total_bias t
        FULL OUTER JOIN s743_distinct_bias d
            ON t.industry_id = d.industry_id
                AND t.industry_id != 'NULL'
                AND d.industry_id != 'NULL'
        FULL OUTER JOIN s743_magnitude s
            ON t.industry_id = s.industry_id
                AND s.industry_id != 'NULL';



DROP VIEW s743_bias_yeas_dcount;
DROP VIEW s743_bias_nays_total_count;
DROP VIEW s743_bias_yeas_dcount;
DROP VIEW s743_bias_nays_dcount;
DROP VIEW s743_total_bias;
DROP VIEW s743_distinct_bias;