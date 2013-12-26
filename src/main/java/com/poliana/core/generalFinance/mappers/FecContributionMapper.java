package com.poliana.core.generalFinance.mappers;

import com.poliana.core.generalFinance.entities.FecContribution;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class FecContributionMapper implements RowMapper<FecContribution> {
    public FecContribution mapRow(ResultSet rs, int rowNum) throws SQLException {
        FecContribution contribution = new FecContribution();
        contribution.setId(rs.getString("id"));
        contribution.setImportReferenceId(rs.getString("import_reference_id"));
        contribution.setCycle(rs.getString("cycle"));
        contribution.setTransactionNamespace(rs.getString("transaction_namespace"));
        contribution.setTransactionId(rs.getString("transaction_id"));
        contribution.setTransactionType(rs.getString("transaction_type"));
        contribution.setFilingId(rs.getString("filing_id"));
        contribution.setAmendment(rs.getString("is_amendment"));
        contribution.setAmount(rs.getDouble("amount"));
        contribution.setDates(rs.getString("dates"));
        contribution.setContributorName(rs.getString("contributor_name"));
        contribution.setContributorExtId(rs.getString("contributor_ext_id"));
        contribution.setContributorType(rs.getString("contributor_type"));
        contribution.setContributorOccupation(rs.getString("contributor_occupation"));
        contribution.setContributorEmployer(rs.getString("contributor_employer"));
        contribution.setContributorGender(rs.getString("contributor_gender"));
        contribution.setContributorAddress(rs.getString("contributor_address"));
        contribution.setContributorCity(rs.getString("contributor_city"));
        contribution.setContributorState(rs.getString("contributor_state"));
        contribution.setContributorZipcode(rs.getString("contributor_zipcode"));
        contribution.setContributorCategory(rs.getString("contributor_category"));
        contribution.setOrganizationName(rs.getString("organization_name"));
        contribution.setOrganizationExtId(rs.getString("organization_ext_id"));
        contribution.setParentOrganizationName(rs.getString("parent_organization_name"));
        contribution.setParentOrganizationExtId(rs.getString("parent_organization_ext_id"));
        contribution.setRecipientName(rs.getString("recipient_name"));
        contribution.setRecipientExtId(rs.getString("recipient_ext_id"));
        contribution.setRecipientParty(rs.getString("recipient_party"));
        contribution.setRecipientType(rs.getString("recipient_type"));
        contribution.setRecipientState(rs.getString("recipient_state"));
        contribution.setRecipientStateHeld(rs.getString("recipient_state_held"));
        contribution.setRecipientCategory(rs.getString("recipient_category"));
        contribution.setCommitteeName(rs.getString("committee_name"));
        contribution.setCommitteeExtId(rs.getString("committee_ext_id"));
        contribution.setCommitteeParty(rs.getString("committee_party"));
        contribution.setCandidacyStatus(rs.getString("candidacy_status"));
        contribution.setDistrict(rs.getString("district"));
        contribution.setDistrictHeld(rs.getString("district_held"));
        contribution.setSeat(rs.getString("seat"));
        contribution.setSeatHeld(rs.getString("seat_held"));
        contribution.setSeatStatus(rs.getString("seat_status"));
        contribution.setSeatResult(rs.getString("seat_result"));

        return contribution;
    }
}

