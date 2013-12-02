CREATE TABLE campaign_finance.candidate_contributions_crp LIKE campaign_finance.candidate_contributions_crp_external;
CREATE TABLE campaign_finance.committee_contributions_crp LIKE campaign_finance.committee_contributions_crp_external;
CREATE TABLE campaign_finance.fec_contributions LIKE campaign_finance.fec_contributions_external;
CREATE TABLE campaign_finance.expenditures LIKE campaign_finance.expenditures_external;
CREATE TABLE campaign_finance.individual_contributions LIKE campaign_finance.individual_contributions_external;
CREATE TABLE campaign_finance.pac_to_candidate_contributions LIKE campaign_finance.pac_to_candidate_contributions_external;
CREATE TABLE campaign_finance.pac_to_pac_contributions LIKE campaign_finance.pac_to_pac_contributions_external;
CREATE TABLE entities.candidate_ids LIKE entities.candidate_ids_external;
CREATE TABLE entities.congressional_committee_ids LIKE entities.congressional_committee_ids_external;
CREATE TABLE entities.current_committee_membership_json LIKE entities.current_committee_membership_json_external;
CREATE TABLE entities.current_committees_nested LIKE entities.current_committees_nested_external;
CREATE TABLE entities.legislators LIKE entities.legislators_external;
CREATE TABLE bills.amendments_json LIKE bills.amendments_json_external;
CREATE TABLE bills.votes_by_bill_embedded LIKE bills.votes_by_bill_embedded_external;
CREATE TABLE bills.votes_flat LIKE bills.votes_flat_external;
CREATE TABLE bills.bill_meta LIKE bills.bill_meta_external;
CREATE TABLE bills.bill_meta_embedded LIKE bills.bill_meta_embedded_external;

INSERT OVERWRITE TABLE campaign_finance.candidate_contributions_crp SELECT * FROM campaign_finance.candidate_contributions_crp_external;
INSERT OVERWRITE TABLE campaign_finance.committee_contributions_crp SELECT * FROM campaign_finance.committee_contributions_crp_external;
INSERT OVERWRITE TABLE campaign_finance.fec_contributions SELECT * FROM campaign_finance.fec_contributions_external;
INSERT OVERWRITE TABLE campaign_finance.expenditures SELECT * FROM campaign_finance.expenditures_external;
INSERT OVERWRITE TABLE campaign_finance.individual_contributions SELECT * FROM campaign_finance.individual_contributions_external;
INSERT OVERWRITE TABLE campaign_finance.pac_to_candidate_contributions SELECT * FROM campaign_finance.pac_to_candidate_contributions_external;
INSERT OVERWRITE TABLE campaign_finance.pac_to_pac_contributions SELECT * FROM campaign_finance.pac_to_pac_contributions_external;
INSERT OVERWRITE TABLE entities.candidate_ids SELECT * FROM entities.candidate_ids_external;
INSERT OVERWRITE TABLE entities.congressional_committee_ids SELECT * FROM entities.congressional_committee_ids_external;
INSERT OVERWRITE TABLE entities.current_committee_membership_json SELECT * FROM entities.current_committee_membership_json_external;
INSERT OVERWRITE TABLE entities.current_committees_nested SELECT * FROM entities.current_committees_nested_external;
INSERT OVERWRITE TABLE entities.legislators SELECT * FROM entities.legislators_external;
INSERT OVERWRITE TABLE bills.amendments_json PARTITION (congress, bill_type) SELECT * FROM bills.amendments_json_external;
INSERT OVERWRITE TABLE bills.votes_by_bill_embedded PARTITION (year, month)  SELECT * FROM bills.votes_by_bill_embedded_external;
INSERT OVERWRITE TABLE bills.votes_flat PARTITION (year, month) SELECT * FROM bills.votes_flat_external;
INSERT OVERWRITE TABLE bills.bill_meta PARTITION (congress, bill_type) SELECT * FROM bills.bill_meta_external;
INSERT OVERWRITE TABLE bills.bill_meta_embedded PARTITION (congress, bill_type) SELECT * FROM bills.bill_meta_embedded_external;





