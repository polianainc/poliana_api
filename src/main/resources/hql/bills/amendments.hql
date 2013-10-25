CREATE EXTERNAL TABLE bills.amendments_json_external (
    actions ARRAY<STRUCT<
        acted_at: STRING,
        references: ARRAY<STRING>,
        test: STRING,
        type: STRING,
        how: STRING,
        result: STRING,
        vote_type: STRING,
        location: STRING
    >>,
    amendment_id STRING,
    amendment_type STRING,
    amends_amendment STRING,
    amends_bill STRUCT<
        bill_id: STRING,
        bill_type: STRING,
        congress: INT,
        number: INT
    >,
    amends_treaty STRING,
    chamber STRING,
    congress STRING,
    description STRING,
    house_number INT,
    introduced_at STRING,
    number INT,
    purpose STRING,
    sponsor STRUCT<
        district: STRING,
        name: STRING,
        state: STRING,
        thomas_id: STRING,
        title: STRING,
        type: STRING
    >,
    status STRING,
    status_at STRING,
    updated_at STRING
)
ROW FORMAT SERDE 'com.proofpoint.hive.serde.JsonSerde'
 WITH SERDEPROPERTIES ('errors.ignore' = 'true')
 LOCATION 's3n://polianaprod/legislation/amendments_json/';