package com.poliana.core.finance.pacs.repositories;

import com.poliana.core.finance.industries.mapppers.LegislatorRecievedIndustryTotalsMapper;
import com.poliana.core.finance.pacs.entities.PacPoliticianContrTotals;
import com.poliana.core.finance.pacs.mappers.PacPoliticianContrMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.List;

/**
 * @author David Gilmore
 * @date 12/19/13
 */
public class PacContributionHadoopRepo {

    @Autowired
    protected JdbcTemplate hiveTemplate;

    @Autowired
    protected JdbcTemplate impalaTemplate;

    public List<PacPoliticianContrTotals> legislatorReceivedPacTotals(String bioguideId, int beginTimestamp,
                                                                                  int endTimestamp) {
        return legislatorReceivedPacTotals(bioguideId, beginTimestamp, endTimestamp, 0);
    }

    public List<PacPoliticianContrTotals> legislatorReceivedPacTotals(String bioguideId, int beginTimestamp,
                                                                      int endTimestamp, int limit) {
        String lim = "";
        if (limit != 0)
            lim = " LIMIT " + limit;

        String query = "SELECT bioguide_id, real_code, sum(amount), i.cat_name, i.industry, i.sector_long FROM " +
                "campaign_finance.individual_contributions_timestamped c JOIN entities.legislators l " +
                "ON c.recip_id = l.opensecrets_id JOIN entities.industry_codes i ON c.real_code = i.cat_code " +
                " WHERE bioguide_id = \'" + bioguideId +"\' AND unix_time > " + beginTimestamp + " AND unix_time < "
                + endTimestamp + " GROUP BY bioguide_id, real_code, i.cat_name, i.industry, i.sector_long" + lim;

        return impalaTemplate.query(query, new PacPoliticianContrMapper(beginTimestamp, endTimestamp));
    }
}
