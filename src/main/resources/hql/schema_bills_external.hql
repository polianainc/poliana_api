CREATE EXTERNAL TABLE IF NOT EXISTS bills_external.bills_json (
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
 LOCATION 's3n://polianaprod/legislation/bills_json/';

CREATE EXTERNAL TABLE IF NOT EXISTS bills_external.amendments_json (
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

CREATE EXTERNAL TABLE IF NOT EXISTS bills_external.bill_meta_embedded (
    bill_id STRING,
    vote_id STRING,
    official_title STRING,
    popular_title STRING,
    short_title STRING,
    sponsor_name STRING,
    sponsor_state STRING,
    sponsor_id STRING,
    cosponsor_ids STRING,
    top_subject STRING,
    subjects STRING,
    summary STRING,
    introduced_at INT,
    house_passage_result STRING,
    house_passage_result_at INT,
    senate_cloture_result STRING,
    senate_cloture_result_at INT,
    senate_passage_result STRING,
    senate_passage_result_at INT,
    awaiting_signature BOOLEAN,
    enacted BOOLEAN,
    vetoed BOOLEAN,
    enacted_at INT,
    status STRING,
    status_at INT
)
PARTITIONED BY (congress INT, bill_type STRING)
STORED AS SEQUENCEFILE
LOCATION 's3n://polianaprod/legislation/bills_meta/';

CREATE EXTERNAL TABLE bills_external.bill_sponsorship_counts_monthly (
    sponsor_id STRING,
    cosponsor_id STRING,
    cosponsor_count INT,    
    bill_type STRING
)
PARTITIONED BY (congress INT, year INT, month INT)
STORED AS SEQUENCEFILE
LOCATION 's3n://polianaprod/legislation/bill_sponsorship_counts_monthly/'; 

CREATE EXTERNAL TABLE IF NOT EXISTS bills_external.house_votes_by_bill (
    vote_id STRING,
    yea_total INT,
    nay_total INT,
    not_voting_total INT,
    not_present_total INT,
    yea_votes STRING,
    nay_votes STRING,
    not_voting STRING,
    not_present STRING,
    datestring STRING
)
PARTITIONED BY (year INT, month INT)
STORED AS SEQUENCEFILE
LOCATION 's3n://polianaprod/legislation/house_votes_by_bill/';

CREATE EXTERNAL TABLE IF NOT EXISTS bills_external.house_votes_flat (
    vote_id STRING,
    vote STRING,
    date STRING,
    bioguide_id STRING,
    display_name STRING,
    first_name STRING,
    last_name STRING,
    party STRING,
    state STRING,
    district STRING,
    religion STRING
)
PARTITIONED BY (year INT, month INT)
STORED AS SEQUENCEFILE
LOCATION 's3n://polianaprod/legislation/house_votes_flat/';

CREATE EXTERNAL TABLE IF NOT EXISTS bills_external.senate_votes_by_bill (
    vote_id STRING,
    yea_total INT,
    nay_total INT,
    not_voting_total INT,
    not_present_total INT,
    yea_votes STRING,
    nay_votes STRING,
    not_voting STRING,
    not_present STRING,
    datestring STRING
)
PARTITIONED BY (year INT, month INT)
STORED AS SEQUENCEFILE
LOCATION 's3n://polianaprod/legislation/senate_votes_by_bill/';

CREATE EXTERNAL TABLE IF NOT EXISTS bills_external.senate_votes_flat (
    vote_id STRING,
    vote STRING,
    date STRING,
    bioguide_id STRING,
    display_name STRING,
    first_name STRING,
    last_name STRING,
    party STRING,
    state STRING,
    district STRING,
    religion STRING
)
PARTITIONED BY (year INT, month INT)
STORED AS SEQUENCEFILE
LOCATION 's3n://polianaprod/legislation/senate_votes_flat/';

CREATE EXTERNAL TABLE bills_external.vote_arrays (
    vote_id STRING,
    datestring STRING,
    Yea ARRAY<STRUCT<
        display_name: STRING,
        first_name: STRING,
        id: STRING,
        last_name: STRING,
        party: STRING,
        state: STRING
    >>,
    Nay ARRAY<STRUCT<
        display_name: STRING,
        first_name: STRING,
        id: STRING,
        last_name: STRING,
        party: STRING,
        state: STRING
    >>,
    Not_Voting ARRAY<STRUCT<
        display_name: STRING,
        first_name: STRING,
        id: STRING,
        last_name: STRING,
        party: STRING,
        state: STRING
    >>,
    Present ARRAY<STRUCT<
        display_name: STRING,
        first_name: STRING,
        id: STRING,
        last_name: STRING,
        party: STRING,
        state: STRING
    >>
)
PARTITIONED BY (year INT, month INT)
STORED AS SEQUENCEFILE
LOCATION 's3n://polianaprod/legislation/vote_arrays/';

CREATE EXTERNAL TABLE IF NOT EXISTS bills_external.votes_by_bill_embedded (
    vote_id STRING,
    yea_total INT,
    nay_total INT,
    not_voting_total INT,
    not_present_total INT,
    yea_votes STRING,
    nay_votes STRING,
    not_voting STRING,
    not_present STRING,
    date STRING
)
PARTITIONED BY (chamber STRING, year INT, month INT)
STORED AS SEQUENCEFILE
LOCATION 's3n://polianaprod/legislation/votes_by_bill/';

CREATE EXTERNAL TABLE IF NOT EXISTS bills_external.votes_flat (
    vote_id STRING,
    vote STRING,
    datestring STRING,
    display_name STRING,
    first_name STRING,
    id STRING,
    last_name STRING,
    party STRING,
    state STRING
)
PARTITIONED BY (year INT, month INT)
STORED AS SEQUENCEFILE
LOCATION 's3n://polianaprod/legislation/votes_flat/';

CREATE EXTERNAL TABLE IF NOT EXISTS bills_external.votes_json (
    category STRING,
    chamber STRING,
    congress INT,
    datestring STRING,
    nomination STRUCT<
        number: STRING,
        title: STRING
    >,
    amendment STRUCT<
        number: STRING,
        purpose: STRING,
        type: STRING
    >,
    bill STRUCT<
        number: INT,
        congress: INT,
        title: STRING,
        type: STRING
    >,
    number INT,
    question STRING,
    requires STRING,
    result STRING,
    result_text STRING,
    session STRING,
    source_url STRING,
    subject STRING,
    type STRING,
    updated_at STRING,
    vote_id STRING,
    votes STRUCT<
        Nay: ARRAY<STRUCT<
            display_name: STRING,
            first_name: STRING,
            id: STRING,
            last_name: STRING,
            party: STRING,
            state: STRING
        >>,
        Not_Voting: ARRAY<STRUCT<
            display_name: STRING,
            first_name: STRING,
            id: STRING,
            last_name: STRING,
            party: STRING,
            state: STRING
        >>,
        Present: ARRAY<STRUCT<
            display_name: STRING,
            first_name: STRING,
            id: STRING,
            last_name: STRING,
            party: STRING,
            state: STRING
        >>,
        Yea: ARRAY<STRUCT<
            display_name: STRING,
            first_name: STRING,
            id: STRING,
            last_name: STRING,
            party: STRING,
            state: STRING
        >>
    >
)
ROW FORMAT SERDE 'com.proofpoint.hive.serde.JsonSerde'
 WITH SERDEPROPERTIES ('errors.ignore' = 'true')
LOCATION 's3n://polianaprod/legislation/votes_json/';