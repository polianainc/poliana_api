package com.poliana.core.legislation.repositories;

import com.poliana.core.legislation.entities.Sponsorship;
import com.poliana.core.legislation.mappers.IndividualSponsorshipMapper;
import com.poliana.core.legislation.entities.deprecated.BillAction;
import com.poliana.core.legislation.entities.deprecated.BillPojo;
import com.poliana.core.legislation.entities.deprecated.BillVotes;
import com.poliana.core.legislation.mappers.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.Calendar;
import java.util.List;

/**
 * @author David Gilmore
 * @date 10/18/13
 */

@Repository
public class BillHadoopRepo {
    @Autowired
    private JdbcTemplate hiveTemplate;
    @Autowired
    private JdbcTemplate impalaTemplate;


    /**
     * Returns a list of all bills during a specified congress.
     * Impala is data source.
     *
     * @param congress  {int} Query parameter to the congress field.
     */
    public List<String> allBillIdsByCongress(int congress) {
        return impalaTemplate.queryForList("SELECT bill_id FROM " +
                "bills.bill_meta_embedded WHERE congress = " + congress,
                String.class);
    }

    /**
     * Returns a list of all bills which have been voted on
     * during a specified congress.
     * Impala is data source.
     *
     * @param congress  {int} Query parameter to the congress field.
     */
    public List<String> allBillIdsWithVotesByCongress(int congress) {
        return impalaTemplate.queryForList("SELECT bill_id FROM " +
                "bills.bill_meta_embedded b JOIN bills.votes_by_bill v " +
                "ON b.vote_id = v.vote_id WHERE congress = " + congress,
                String.class);
    }

    /**
     * Returns a list of BillPojo objects from the given congress.
     * BillPojo meta and vote information is populated.
     * Selects from a join of bills.bill_meta_embedded.
     * and bills.votes_by_bill_embedded.
     * Impala is data source.
     *
     * @param congress  {int} Query parameter to the congress field.
     * @param limit     {int} Specify limit. A limit of 0 returns all records
     *
     * @see             com.poliana.core.legislation.entities.deprecated.BillPojo
     * @see             BillWithVotesMapper
     */
    public List<BillPojo> billsWithVotesByCongress(int congress, int limit) {
        String lim = "";
        if (limit > 0)
            lim = " LIMIT " + limit;

        return impalaTemplate.query("" +
                "SELECT * FROM " +
                "bills.bill_meta_embedded b JOIN bills.votes_by_bill_embedded v " +
                "ON b.vote_id = v.vote_id WHERE congress = " + congress + lim,
                new BillWithVotesMapper());
    }

    /**
     * Returns a list of BillPojo objects which have votes from the given congress.
     * BillPojo meta alone is populated.
     * Selects from a join of bills.bill_meta_embedded.
     * and bills.votes_by_bill_embedded.
     * Impala is data source.
     *
     * @param congress  {int} Query parameter to the congress field.
     * @param limit     {int} Specify limit. A limit of 0 returns all records
     *
     * @see             com.poliana.core.legislation.entities.deprecated.BillPojo
     * @see             BillMapper
     */
    public List<BillPojo> billsHavingVotesByCongress(int congress, int limit) {
        String lim = "";
        if (limit > 0)
            lim = " LIMIT " + limit;

        return impalaTemplate.query("" +
                "SELECT * FROM " +
                "bills.bill_meta_embedded b JOIN bills.votes_by_bill v " +
                "ON b.vote_id = v.vote_id WHERE congress = " + congress + lim,
                new BillMapper());
    }


    /**
     * Returns a list of BillPojo objects which have votes from the given congress.
     * BillPojo meta alone is populated.
     * Selects from a join of bills.bill_meta_embedded.
     * and bills.votes_by_bill_embedded.
     * Impala is data source.
     *
     * @param congress  {int} Query parameter to the congress field.
     * @param limit     {int} Specify limit. A limit of 0 returns all records
     *
     * @see             com.poliana.core.legislation.entities.deprecated.BillPojo
     * @see             BillMapper
     */
    public List<BillPojo> billsHavingVotesByCongressChamber(int congress, int limit, String chamber) {
        String lim = "";
        if (limit > 0)
            lim = " LIMIT " + limit;

        return impalaTemplate.query("" +
                "SELECT * FROM " +
                "bills.bill_meta_embedded b JOIN bills.votes_by_bill v " +
                "ON b.vote_id = v.vote_id WHERE congress = " + congress +
                " AND chamber = \'" + chamber + "\'" + lim,
                new BillMapper());
    }

    /**
     * Returns a list of BillPojo objects from the given congress.
     * BillPojo meta and vote information is populated.
     * Selects from a join of bills.bill_meta_embedded.
     * and bills.votes_by_bill_embedded.
     * Impala is data source.
     *
     * @param billType  {String} Query parameter to the bill_type field.
     * @param limit     {int} Specify limit. A limit of 0 returns all records
     *
     * @see             com.poliana.core.legislation.entities.deprecated.BillPojo
     * @see             BillWithVotesMapper
     */
    public List<BillPojo> billsWithVotesByBillType(String billType, int limit) {
        String lim = "";
        if (limit > 0)
            lim = " LIMIT " + limit;

        return impalaTemplate.query("" +
                "SELECT * FROM " +
                "bills.bill_meta_embedded b JOIN bills.votes_by_bill v " +
                "ON b.vote_id = v.vote_id WHERE bill_type = \'" + billType + "\' " + lim,
                new BillWithVotesMapper());
    }

    /**
     * Returns a list of BillPojo objects from the given congress.
     * BillPojo meta and vote information is populated.
     * Selects from a join of bills.bill_meta_embedded.
     * and bills.votes_by_bill_embedded.
     * Impala is data source.
     *
     * @param billType  {String} Query parameter to the bill_type field.
     * @param limit     {int} Specify limit. A limit of 0 returns all records
     *
     * @see             com.poliana.core.legislation.entities.deprecated.BillPojo
     * @see             BillWithVotesMapper
     */
    public List<BillPojo> billsWithVotesByBillTypeAndCongress(String billType, int congress, int limit) {
        String lim = "";
        if (limit > 0)
            lim = " LIMIT " + limit;

        return impalaTemplate.query("" +
                "SELECT * FROM " +
                "bills.bill_meta_embedded b JOIN bills.votes_by_bill v " +
                "ON b.vote_id = v.vote_id WHERE bill_type = \'" + billType +
                "\' AND congress = " + congress + lim,
                new BillWithVotesMapper());
    }

    /**
     * Returns a BillPojo object matching the given billId.
     * Only meta information will be populated, no votes.
     * Selects from bills.bill_meta_embedded table
     * Impala is data source.
     *
     * @param billId    {String} Query parameter to the bill_id field.
     *
     * @see             com.poliana.core.legislation.entities.deprecated.BillPojo
     * @see             BillMetaEmbeddedMapper
     */
    public BillPojo billMetaByBillId(String billId) {
        return impalaTemplate.queryForObject("SELECT * " +
                "FROM bills.bill_meta_embedded WHERE bill_id = \'" + billId + "\'",
                new BillMetaEmbeddedMapper());
    }

    /**
     * Returns a list of BillPojo objects matching the given billId.
     * Only meta information will be populated, no votes.
     * Selects from bills.bill_meta_embedded table
     * Impala is data source.
     *
     * @param congress  {int} Query parameter to the congress field.
     * @param limit     {int} Specify limit. A limit of 0 returns all matching records.
     *
     * @see             com.poliana.core.legislation.entities.deprecated.BillPojo
     * @see             BillMetaEmbeddedMapper
     */
    public List<BillPojo> billMetaByCongress(int congress, int limit) {
        String lim = "";
        if (limit > 0)
            lim = " LIMIT " + limit;

        return impalaTemplate.query("SELECT * " +
                "FROM bills.bill_meta_embedded WHERE congress = " + congress + lim,
                new BillMetaEmbeddedMapper());
    }

    /**
     *
     * @param billType  {String} Query parameter to the bill_type field.
     * @param congress  {int} Query parameter to the bill_type field.
     * @param limit     {int} Specify limit. A limit of 0 returns all records
     *
     * @see             com.poliana.core.legislation.entities.deprecated.BillPojo
     * @see             BillWithVotesMapper
     */
    public List<BillPojo> billMetaByBillTypeAndCongress(String billType, int congress, int limit) {
        String lim = "";
        if (limit > 0)
            lim = " LIMIT " + limit;

        return impalaTemplate.query("" +
                "SELECT * FROM " +
                "bills.bill_meta_embedded " +
                " WHERE bill_type = \'" + billType + "\' AND congress = " + congress + lim,
                new BillMetaEmbeddedMapper());
    }

    /**
     * Returns a list of BillVotes objects matching the given vote_id.
     * Only meta information will be populated, no votes.
     * Selects from bills.votes_by_bill_embedded table
     * Impala is data source.
     *
     * @param voteId  {String} Query parameter to the vote_id field.
     *
     * @see             BillVotes
     * @see             BillMetaEmbeddedMapper
     */
    public List<BillVotes> billVotesByVoteId(String voteId) {
        return impalaTemplate.query("SELECT * " +
                "FROM bills.votes_by_bill3 WHERE vote_id = \'" + voteId + "\'",
                new BillVotesMapper());
    }

    /**
     * Returns a list of BillVotes objects from the given congress.
     * Selects from bills.votes_by_bill_embedded table
     * Impala is data source.
     *
     * @param year      {int} Query parameter to the year field.
     * @param limit     {int} Specify limit. A limit of 0 returns all matching records.
     *
     * @see             BillVotes
     * @see             BillMetaEmbeddedMapper
     */
    public List<BillVotes> billVotesByYear(int year, int limit) {
        return impalaTemplate.query("SELECT * " +
                "FROM bills.votes_by_bill_embedded WHERE year = " + year + " LIMIT " + limit,
                new BillVotesMapper());
    }

    /**
     * Returns a BillAction object matching the given billId.
     * Selects from bills.bills_actions table
     * Impala is data source.
     *
     * @param billId    {String} Query parameter to the bill_id field.
     *
     * @see             BillAction
     * @see             BillActionMapper
     */
    public List<BillAction> billActions(String billId) {
        return hiveTemplate.query("SELECT * " +
                "FROM bills.bills_actions WHERE bill_id = \'" + billId + "\'",
                new BillActionMapper());
    }

    public List<String> uniqueVoters(String chamber, int beginTimestamp, int endTimestamp) {
        String c = (chamber.contains("s")) ? "sen" : "rep";

        Calendar cal = Calendar.getInstance();

        cal.setTimeInMillis((long)beginTimestamp*1000);
        int beginYear = cal.get(cal.YEAR);
        int beginMonth = cal.get(cal.MONTH);

        cal.setTimeInMillis((long)endTimestamp*1000);
        int endYear = cal.get(cal.YEAR);
        int endMonth = cal.get(cal.MONTH);

        String query = "SELECT DISTINCT id FROM bills.votes_flat v JOIN entities.legislators_flat_terms l " +
                "ON v.id = l.bioguide_id WHERE year >= " + beginYear + " AND year <= " + endYear + " AND " +
                "term_type = \'" + c + "\'";
        return impalaTemplate.queryForList(query, String.class);
    }

    public List<Sponsorship> getSponsorships(String chamber, int congress) {
        String query = "SELECT s.bioguide_id, c.bioguide_id, count(c.bioguide_id) FROM " +
                "bills.bill_sponsorship_flat b JOIN entities.legislators s ON b.sponsor_thomas_id = s.thomas_id " +
                "JOIN entities.legislators c ON b.cosponsor_thomas_id = c.thomas_id WHERE congress = \"" +
                congress + "\" " + "AND SUBSTR(bill_type,1,1) = \"" + chamber +"\" GROUP BY s.bioguide_id, c.bioguide_id";
        return impalaTemplate.query(query, new IndividualSponsorshipMapper(chamber,congress));
    }
}
