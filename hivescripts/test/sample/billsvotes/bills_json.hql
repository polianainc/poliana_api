set fs.s3n.awsAccessKeyId=AKIAI27OS66EVU7XE57A;
set fs.s3n.awsSecretAccessKey=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;

CREATE EXTERNAL TABLE bills_json ( 
    breakdown STRUCT<
        Party: STRUCT<
            R: STRUCT<
                Yea: DOUBLE,
                Nay: DOUBLE,
                No_Vote: DOUBLE,
                Present: DOUBLE
            >,
            D: STRUCT<
                Yea: DOUBLE,
                Nay: DOUBLE,
                No_Vote: DOUBLE,
                Present: DOUBLE
            >
        >,
        Total: STRUCT<
            Yea: DOUBLE,
            Nay: DOUBLE,
            No_Vote: DOUBLE,
            Present: DOUBLE
        >
    >,
    introduced_on STRING,
    congress STRING,
    required STRING,
    yeas_ids ARRAY<STRING>,
    short_title STRING,
    official_title STRING,
    result STRING,
    sponsor_id STRING,
    nays_ids ARRAY<STRING>,
    bill_id STRING
)
ROW FORMAT SERDE 'com.proofpoint.hive.serde.JsonSerde'
 WITH SERDEPROPERTIES ('errors.ignore' = 'true')
LOCATION 's3n://polianatest/sample/billsvotes/json/';