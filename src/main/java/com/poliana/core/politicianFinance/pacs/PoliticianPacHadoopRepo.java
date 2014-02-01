package com.poliana.core.politicianFinance.pacs;

import com.poliana.core.time.TimeService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;

/**
 * @author David Gilmore
 * @date 1/26/14
 */
@Repository
public class PoliticianPacHadoopRepo {

    private JdbcTemplate impalaTemplate;

    private TimeService timeService;

    private static final Logger logger = Logger.getLogger(PoliticianPacHadoopRepo.class);


    public PoliticianPacHadoopRepo() {

        this.timeService = new TimeService();
    }

    /**
     * Get a PAC to Politician contribution sums for all time.
     * @param bioguideId
     * @return
     */
    public List<PoliticianPacContributionsTotals> getPacToPoliticianContributions(String bioguideId) {

        try {
            String query =
                    "SELECT DISTINCT" +
                    "      pac_id" +
                    "    , cmte_nm as pac_name" +
                    "    , bioguide_id" +
                    "    , first_name" +
                    "    , last_name  " +
                    "    , party" +
                    "    , religion" +
                    "    , contribution_count" +
                    "    , contribution_sum " +
                    "FROM" +
                    "    (SELECT" +
                    "          pac_id" +
                    "        , bioguide_id" +
                    "        , first_name" +
                    "        , last_name  " +
                    "        , party" +
                    "        , religion" +
                    "        , COUNT(amount) as contribution_count" +
                    "        , SUM(amount) as contribution_sum" +
                    "    FROM " +
                    "        crp.pac_to_candidate_contributions" +
                    "    JOIN" +
                    "        entities.legislators" +
                    "    ON" +
                    "        cid = opensecrets_id" +
                    "    WHERE" +
                    "        bioguide_id = \'" + bioguideId + "\'" +
                    "    GROUP BY" +
                    "          pac_id" +
                    "        , bioguide_id" +
                    "        , first_name" +
                    "        , last_name" +
                    "        , party" +
                    "        , religion) " +
                    "    totals " +
                    "JOIN" +
                    "    entities.pacs " +
                    "ON " +
                    "    cmte_id = pac_id ";

            List<PoliticianPacContributionsTotals> contributionsList =
                    impalaTemplate.query(query, new PoliticianPacContributionTotalsMapper());

            logger.info("PAC contribution sums to " + bioguideId + " returned from Impala");

            return contributionsList;
        }
        catch (Exception e) {
            logger.error(e);
        }

        return null;
    }

    /**
     * Get a PAC to Politician contribution sums for a given timerange.
     * Note: The committee name filed during the congressional cycle of the beginTimestamp will be used
     * @param bioguideId
     * @param beginTimestamp
     * @param endTimestamp
     * @return
     */
    public List<PoliticianPacContributionsTotals> getPacToPoliticianContributions(String bioguideId, long beginTimestamp, long endTimestamp) {

        int congress = timeService.getCongressByTimestamp(beginTimestamp);

        try {
            String query =
                    "SELECT DISTINCT" +
                    "      pac_id" +
                    "    , cmte_nm as pac_name" +
                    "    , bioguide_id" +
                    "    , first_name" +
                    "    , last_name  " +
                    "    , party" +
                    "    , religion" +
                    "    , contribution_count" +
                    "    , contribution_sum " +
                    "FROM" +
                    "    (SELECT" +
                    "          pac_id" +
                    "        , bioguide_id" +
                    "        , first_name" +
                    "        , last_name  " +
                    "        , party" +
                    "        , religion" +
                    "        , COUNT(amount) as contribution_count" +
                    "        , SUM(amount) as contribution_sum" +
                    "    FROM " +
                    "        crp.pac_to_candidate_contributions" +
                    "    JOIN" +
                    "        entities.legislators" +
                    "    ON" +
                    "        cid = opensecrets_id" +
                    "    WHERE" +
                    "        bioguide_id = \'" + bioguideId + "\'" +
                    "    AND" +
                    "        date_ts > " + beginTimestamp +
                    "    AND" +
                    "        date_ts < " + endTimestamp +
                    "    GROUP BY" +
                    "          pac_id" +
                    "        , bioguide_id" +
                    "        , first_name" +
                    "        , last_name" +
                    "        , party" +
                    "        , religion) " +
                    "    totals " +
                    "JOIN" +
                    "    entities.pacs " +
                    "ON " +
                    "    cmte_id = pac_id " +
                    "AND" +
                    "    cycle = " + congress;

            List<PoliticianPacContributionsTotals> contributionsList =
                    impalaTemplate.query(query, new PoliticianPacContributionTotalsMapper(beginTimestamp, endTimestamp));

            logger.info("PAC contribution sums to " + bioguideId + " from " + beginTimestamp + " to " + endTimestamp + " returned from Impala");

            return contributionsList;
        }
        catch (Exception e) {
            logger.error(e);
        }

        return null;
    }

    /**
     * Get a PAC to Politician contribution sums for all time.
     * @param bioguideId
     * @return
     */
    public HashMap<Integer, List<PoliticianPacContributionsTotals>> getPacToPoliticianTotalsPerCongress(String bioguideId) {

        try {
            String query =
                    "SELECT " +
                    "       bioguide_id " +
                    "     , first_name " +
                    "     , last_name " +
                    "     , party " +
                    "     , religion " +
                    "     , pac_id   " +
                    "     , pac_name  " +
                    "     , contribution_count " +
                    "     , contribution_sum " +
                    "     , congress " +
                    "FROM  " +
                    "     entities.legislators legislators " +
                    "JOIN " +
                    "     (SELECT    " +
                    "            cid " +
                    "         ,  pac_id   " +
                    "         , cmte_nm AS pac_name   " +
                    "         , COUNT(amount) AS contribution_count   " +
                    "         , SUM(amount) AS contribution_sum   " +
                    "         , congress " +
                    "     FROM   " +
                    "         entities.pacs pacs   " +
                    "     JOIN   " +
                    "         crp.pac_to_candidate_contributions contributions   " +
                    "     ON   " +
                    "         pac_id = cmte_id   " +
                    "     AND   " +
                    "         cycle = congress        " +
                    "     GROUP BY    " +
                    "           cid " +
                    "         , pac_id   " +
                    "         , pac_name   " +
                    "         , congress) pac_contributions " +
                    "ON " +
                    "     legislators.opensecrets_id = cid " +
                    "WHERE bioguide_id = \'" + bioguideId + "\'";

            HashMap< Integer, List<PoliticianPacContributionsTotals>> contributionsList =
                    impalaTemplate.query(query, new PacContributionsPerCongressMapper());

            logger.info("PAC contribution sums to " + bioguideId + " for all time returned from Impala");

            return contributionsList;
        }
        catch (Exception e) {
            logger.error(e);
        }

        return null;
    }

    /**
     * Get a PAC to Politician contribution sums for a given timerange.
     * Note: The committee name filed during the congressional cycle of the beginTimestamp will be used
     * @param bioguideId
     * @param beginTimestamp
     * @param endTimestamp
     * @return
     */
    public HashMap<Integer, List<PoliticianPacContributionsTotals>> getPacToPoliticianTotalsPerCongress(String bioguideId, long beginTimestamp, long endTimestamp) {

        Integer[] cycles = timeService.getCongressionalCyclesByTimeRange(beginTimestamp, endTimestamp);

        String cyclesList = "";

        if (cycles.length > 0) {
            int i = 0;
            for (; i < cycles.length-1; i++)
                cyclesList += cycles[i] + ",";
            cyclesList += cycles[i];
        }

        try {
            String query =
                    "SELECT " +
                    "       bioguide_id " +
                    "     , first_name " +
                    "     , last_name " +
                    "     , party " +
                    "     , religion " +
                    "     , pac_id   " +
                    "     , pac_name  " +
                    "     , contribution_count " +
                    "     , contribution_sum " +
                    "     , congress " +
                    "FROM  " +
                    "     entities.legislators legislators " +
                    "JOIN " +
                    "     (SELECT    " +
                    "            cid " +
                    "         ,  pac_id   " +
                    "         , cmte_nm AS pac_name   " +
                    "         , COUNT(amount) AS contribution_count   " +
                    "         , SUM(amount) AS contribution_sum   " +
                    "         , congress " +
                    "     FROM   " +
                    "         entities.pacs pacs   " +
                    "     JOIN   " +
                    "         crp.pac_to_candidate_contributions contributions   " +
                    "     ON   " +
                    "         pac_id = cmte_id   " +
                    "     AND   " +
                    "         cycle = congress        " +
                    "     WHERE   " +
                    "          congress in (" + cyclesList + ") " +
                    "     GROUP BY    " +
                    "           cid " +
                    "         , pac_id   " +
                    "         , pac_name   " +
                    "         , congress) pac_contributions " +
                    "ON " +
                    "     legislators.opensecrets_id = cid " +
                    "WHERE bioguide_id = \'" + bioguideId + "\'";

            HashMap< Integer, List<PoliticianPacContributionsTotals>> contributionsList =
                    impalaTemplate.query(query, new PacContributionsPerCongressMapper());

            logger.info("PAC contribution sums to " + bioguideId + " from " + beginTimestamp + " to " + endTimestamp + " returned from Impala");

            return contributionsList;
        }
        catch (Exception e) {
            logger.error(e);
        }

        return null;
    }


    @Autowired
    public void setImpalaTemplate(JdbcTemplate impalaTemplate) {
        this.impalaTemplate = impalaTemplate;
    }

}
