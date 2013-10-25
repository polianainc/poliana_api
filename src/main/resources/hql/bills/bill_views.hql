CREATE VIEW bills.view_bill_overview_expl_cosponsors (
    bill_id,
    official_title,
    popular_title,
    short_title,
    sponsor_name,
    sponsor_state,
    sponsor_thomas,
    cosponsor_id,
    top_subject,
    subjects,
    summary,
    introduced_at,
    congress,
    bill_type
)
as SELECT
    bill_id,
    official_title,
    popular_title,
    short_title,
    sponsor.name,
    sponsor.state,
    sponsor.thomas_id,
    cosponsor,
    subjects_top_term,
    subjects,
    summary.text,
    introduced_at,
    congress,
    bill_type
FROM bills.bills_json
LATERAL VIEW explode(cosponsors) consponsors AS cosponsor;


CREATE VIEW bills.view_bill_overview2_expl_subjects (
    bill_id,
    official_title,
    popular_title,
    short_title,
    sponsor_name,
    sponsor_state,
    sponsor_thomas,
    cosponsor_id,
    top_subject,
    subject,
    summary,
    introduced_at,
    congress,
    bill_type
)
as SELECT
    bill_id,
    official_title,
    popular_title,
    short_title,
    sponsor_name,
    sponsor_state,
    sponsor_thomas,
    cosponsor_id.thomas_id,
    top_subject,
    subject,
    summary,
    introduced_at,
    congress,
    bill_type
FROM bills.view_bill_overview_expl_cosponsors
LATERAL VIEW explode(subjects) subjects AS subject;

CREATE VIEW bills.view_bill_overview3_flat_sponsor_bioguide (
    bill_id,
    official_title,
    popular_title,
    short_title,
    sponsor_name,
    sponsor_state,
    sponsor_bioguide,
    cosponsor_id,
    top_subject,
    subject,
    summary,
    introduced_at,
    congress,
    bill_type
)
as SELECT
    b.bill_id,
    b.official_title,
    b.popular_title,
    b.short_title,
    b.sponsor_name,
    b.sponsor_state,
    l.bioguide_id,
    b.cosponsor_id,
    b.top_subject,
    b.subject,
    b.summary,
    b.introduced_at,
    b.congress,
    b.bill_type
FROM bills.view_bill_overview2_expl_subjects b FULL OUTER JOIN people.legislators l
    ON b.sponsor_thomas = l.thomas_id;

CREATE VIEW bills.view_bill_overview4_flat_cosponsor_bioguide (
    bill_id,
    official_title,
    popular_title,
    short_title,
    sponsor_name,
    sponsor_state,
    sponsor_bioguide,
    cosponsor_id,
    top_subject,
    subject,
    summary,
    introduced_at,
    congress,
    bill_type
)
as SELECT
    b.bill_id,
    b.official_title,
    b.popular_title,
    b.short_title,
    b.sponsor_name,
    b.sponsor_state,
    b.sponsor_bioguide,
    l.bioguide_id,
    b.top_subject,
    b.subject,
    b.summary,
    b.introduced_at,
    b.congress,
    b.bill_type
FROM bills.view_bill_overview3_flat_sponsor_bioguide b JOIN people.legislators l
    ON b.cosponsor_id = l.thomas_id;
