CREATE EXTERNAL TABLE current_committees_nested (
    rss_url STRING,
    name STRING,
    url STRING,
    house_committee_id STRING,
    phone STRING,
    subcommittees ARRAY<STRUCT<
        phone: STRING,
        thomas_id: STRING,
        name: STRING,
        address: STRING
    >>,
    address STRING,
    type STRING,
    thomas_id STRING
)
ROW FORMAT SERDE 'com.proofpoint.hive.serde.JsonSerde'
 WITH SERDEPROPERTIES ('errors.ignore' = 'true')
LOCATION 's3n://polianatest/full/currcommittees/';