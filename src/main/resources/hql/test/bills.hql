CREATE EXTERNAL TABLE externals.bills_json (
    actions ARRAY<STRUCT<
        acted_at: STRING,
        committee: STRING,
        how: STRING,
        references: ARRAY<STRUCT<
            reference: STRING,
            type: STRING
            >>,
        roll: STRING,
        status: STRING,
        suspension: STRING,
        text: STRING,
        type: STRING,
        vote_type: STRING,
        location: STRING
        >>,
    amendments ARRAY<STRUCT<
        amendment_id: STRING,
        amendment_type: STRING,
        chamber: STRING,
        number: STRING
        >>,
    bill_id STRING,
    bill_type STRING,
    committees ARRAY<STRUCT<
        activity: ARRAY<STRING>,
        committee: STRING,
        committee_id: STRING
        >>,
    congress STRING,
    cosponsors ARRAY<STRUCT<
        district: STRING,
        name: STRING,
        sponsored_at: STRING,
        state: STRING,
        thomas_id: STRING,
        title: STRING,
        withdrawn_at: STRING
        >>,
    enacted_as STRING,
    history STRUCT<
        house_passage_result: STRING,
        house_passage_result_at: STRING,
        senate_cloture_result: STRING,
        senate_cloture_result_at: STRING,
        senate_passage_result_at: STRING,
        house_passage_result: STRING,
        awaiting_signature: BOOLEAN,
        enacted: BOOLEAN,
        vetoed: BOOLEAN,
        enacted_at: STRING
        >,
    introduced_at STRING,
    number STRING,
    official_title STRING,
    popular_title STRING,
    related_bills ARRAY<STRUCT<
        bill_id: STRING,
        reason: STRING
        >>,
    short_title STRING,
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
    subjects ARRAY<STRING>,
    subjects_top_term STRING,
    summary STRUCT<
        as: STRING,
        date: STRING,
        text: STRING
        >,
    titles ARRAY<STRUCT<
        as: STRING,
        title: STRING,
        type: STRING
        >>,
    updated_at STRING
)
ROW FORMAT SERDE 'com.proofpoint.hive.serde.JsonSerde'
 WITH SERDEPROPERTIES ('errors.ignore' = 'true')
LOCATION 's3n://polianatest/full/bills/';

PARTITIONED BY (congress STRING, bill_type STRING)

find . -name '*.html' |xargs perl -pi -e 's/\n//g'

CREATE TABLE bills_json (
    actions ARRAY<STRUCT<
        acted_at: STRING,
        committee: STRING,
        how: STRING,
        references: ARRAY<STRUCT<
            reference: STRING,
            type: STRING
            >>,
        roll: STRING,
        status: STRING,
        suspension: STRING,
        text: STRING,
        type: STRING,
        vote_type: STRING,
        location: STRING
        >>,
    amendments ARRAY<STRUCT<
        amendment_id: STRING,
        amendment_type: STRING,
        chamber: STRING,
        number: STRING
        >>,
    bill_id STRING,
    bill_type STRING,
    committees ARRAY<STRUCT<
        activity: ARRAY<STRING>,
        committee: STRING,
        committee_id: STRING
        >>,
    congress STRING,
    cosponsors ARRAY<STRUCT<
        district: STRING,
        name: STRING,
        sponsored_at: STRING,
        state: STRING,
        thomas_id: STRING,
        title: STRING,
        withdrawn_at: STRING
        >>,
    enacted_as STRING,
    history STRUCT<
        house_passage_result: STRING,
        house_passage_result_at: STRING,
        senate_cloture_result: STRING,
        senate_cloture_result_at: STRING,
        senate_passage_result_at: STRING,
        house_passage_result: STRING,
        awaiting_signature: BOOLEAN,
        enacted: BOOLEAN,
        vetoed: BOOLEAN,
        enacted_at: STRING
        >,
    introduced_at STRING,
    number STRING,
    official_title STRING,
    popular_title STRING,
    related_bills ARRAY<STRUCT<
        bill_id: STRING,
        reason: STRING
        >>,
    short_title STRING,
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
    subjects ARRAY<STRING>,
    subjects_top_term STRING,
    summary STRUCT<
        as: STRING,
        date: STRING,
        text: STRING
        >,
    titles ARRAY<STRUCT<
        as: STRING,
        title: STRING,
        type: STRING
        >>,
    updated_at STRING
)
ROW FORMAT SERDE 'com.proofpoint.hive.serde.JsonSerde'
 WITH SERDEPROPERTIES ('errors.ignore' = 'true')
LOCATION '/Users/dagilmore/Dev/polianalytics/poliana_processing/src/main/resources/data/bills/';


CREATE TABLE bills_json (
    actions ARRAY<STRUCT<
        acted_at: STRING,
        committee: STRING,
        how: STRING,
        references: ARRAY<STRUCT<
            reference: STRING,
            type: STRING
            >>,
        roll: STRING,
        status: STRING,
        suspension: STRING,
        text: STRING,
        type: STRING,
        vote_type: STRING,
        location: STRING
        >>,
    amendments ARRAY<STRUCT<
        amendment_id: STRING,
        amendment_type: STRING,
        chamber: STRING,
        number: STRING
        >>,
    bill_id STRING,
    bill_type STRING,
    committees ARRAY<STRUCT<
        activity: ARRAY<STRING>,
        committee: STRING,
        committee_id: STRING
        >>,
    congress STRING,
    cosponsors ARRAY<STRUCT<
        district: STRING,
        name: STRING,
        sponsored_at: STRING,
        state: STRING,
        thomas_id: STRING,
        title: STRING,
        withdrawn_at: STRING
        >>,
    enacted_as STRING,
    history STRUCT<
        house_passage_result: STRING,
        house_passage_result_at: STRING,
        senate_cloture_result: STRING,
        senate_cloture_result_at: STRING,
        senate_passage_result_at: STRING,
        house_passage_result: STRING,
        awaiting_signature: BOOLEAN,
        enacted: BOOLEAN,
        vetoed: BOOLEAN,
        enacted_at: STRING
        >,
    introduced_at STRING,
    number STRING,
    official_title STRING,
    popular_title STRING,
    related_bills ARRAY<STRUCT<
        bill_id: STRING,
        reason: STRING
        >>,
    short_title STRING,
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
    subjects ARRAY<STRING>,
    subjects_top_term STRING,
    summary STRUCT<
        as: STRING,
        date: STRING,
        text: STRING
        >,
    titles ARRAY<STRUCT<
        as: STRING,
        title: STRING,
        type: STRING
        >>,
    updated_at STRING
)
ROW FORMAT SERDE 'com.proofpoint.hive.serde.JsonSerde'
 WITH SERDEPROPERTIES (
    'errors.ignore' = 'true')
LOCATION '/Users/dagilmore/Dev/polianalytics/poliana_processing/src/main/resources/data/bills/';
