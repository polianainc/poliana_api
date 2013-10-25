CREATE EXTERNAL TABLE social_media_nested (
    id STRUCT<
        bioguide: STRING,
        thomas: STRING,
        govtrack: STRING
    >,
    social STRUCT<
        facebook: STRING,
        twitter: STRING,
        facebook_id: STRING,
        youtube: STRING,
        youtube_id: STRING
    >
    )
    ROW FORMAT SERDE 'com.proofpoint.hive.serde.JsonSerde'
     WITH SERDEPROPERTIES ('errors.ignore' = 'true')
    LOCATION 's3n://polianatest/full/legislatorsocialmedia/';