package com.poliana.campaignFinance.mappers;

import com.poliana.campaignFinance.models.Bill;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class IndustryContributionMapper implements RowMapper<Bill> {
    public Bill mapRow(ResultSet rs, int rowNum) throws SQLException {
        Bill billContrRecords = new Bill();
        billContrRecords.setBillIntroduction(rs.getString("bill_introduction"));
        billContrRecords.setBioguideId(rs.getString("bioguide_id"));
        billContrRecords.setRecipientExtId(rs.getString("recipient_ext_id"));
        billContrRecords.setFirstName(rs.getString("first_name"));
        billContrRecords.setLastName(rs.getString("last_name"));
        billContrRecords.setIndustryId(rs.getString("industry_id"));
        billContrRecords.setTransactionId(rs.getString("transaction_id"));
        billContrRecords.setAmount(rs.getInt("amount"));
        billContrRecords.setDates(rs.getString("dates"));
        return billContrRecords;
    }
}
