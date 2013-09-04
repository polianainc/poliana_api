package poliana.data.models.bill;

import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class ContributionsMapper implements RowMapper<Contribution> {
    public Contribution mapRow(ResultSet rs, int rowNum) throws SQLException {
        Contribution billContrRecords = new Contribution();
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
