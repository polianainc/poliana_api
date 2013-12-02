CREATE EXTERNAL TABLE current_committee_membership_nested (
    committee_id ARRAY<STRUCT<
        party: STRING,
        thomas: STRING,
        bioguide: STRING,
        rank: STRING,
        name: STRING
    >>
)
ROW FORMAT SERDE 'com.proofpoint.hive.serde.JsonSerde'
 WITH SERDEPROPERTIES ('errors.ignore' = 'true')
LOCATION 's3n://polianatest/full//';