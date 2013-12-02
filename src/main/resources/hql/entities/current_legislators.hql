CREATE EXTERNAL TABLE politician_ids (
    bio STRUCT<
        gender: STRING,
        birthday: STRING,
        religion: STRING
    >,
    terms ARRAY<STRUCT<
        start: STRING,
        state: STRING,
        term_end: STRING,
        district: STRING,
        party: STRING,
        type: STRING,
        state_rank: STRING,
        url: STRING,
        fax: STRING,
        office: STRING,
        class: STRING,
        phone: STRING,
        address: STRING,
        contact_form: STRING,
        rss_url: STRING
    >>,
    id STRUCT<
        thomas: STRING,
        opensecrets: STRING,
        fec: ARRAY<STRING>,
        votesmart: STRING,
        ballotpedia: STRING,
        lis: STRING,
        wikipedia: STRING,
        bioguide: STRING,
        govtrack: STRING,
        maplight:STRING,
        icpsr: STRING,
        cspan: STRING,
        house_history: STRING,
        washington_post: STRING
    >,
    name STRUCT<
        last: STRING,
        official_full: STRING,
        first: STRING
    >
)
ROW FORMAT SERDE 'com.proofpoint.hive.serde.JsonSerde'
 WITH SERDEPROPERTIES ('errors.ignore' = 'true')
LOCATION 's3n://polianatest/full/currlegislators/';