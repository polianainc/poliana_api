CREATE TABLE recipient_contributions (
recipient_ext_id STRING,
contributions ARRAY<STRUCT<
	cycle: STRING,
	contributor_category: STRING,
	contributor_id: STRING,
	organization_id: STRING,
	contributor_employer: STRING,
	contributor_name: STRING,
	organization_name: STRING,
	amount: STRING,
	date: STRING
	>>
);

INSERT OVERWRITE INTO recipient_contributions SELECT distinct(recipient_ext_id), "" FROM contributions;



INSERT OVERWRITE INTO TABLE recipient_contributions (recipient_ext_id)



