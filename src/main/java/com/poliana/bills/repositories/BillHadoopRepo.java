package com.poliana.bills.repositories;

import com.poliana.bills.entities.Bill;
import com.poliana.bills.entities.BillAction;
import com.poliana.bills.mappers.*;
import com.poliana.bills.entities.BillVotes;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
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
     * Returns a Bill object from the given congress.
     * Bill meta and vote information is populated.
     * Selects from a join of bills.bill_meta_embedded
     * and bills.votes_by_bill_embedded.
     * Impala is data source.
     *
     * @param billId    {String} Query parameter to the bill_id field.
     *
     * @see             Bill
     * @see             BillWithVotesMapper
     */
    public Bill billsWithVotesByBillId(String billId) {
        List<Bill> billList = impalaTemplate.query("" +
                "SELECT * FROM " +
                "bills.bill_meta_embedded b JOIN bills.votes_by_bill v " +
                "ON b.vote_id = v.vote_id WHERE b.bill_id = \'" + billId + "\'",
                new BillWithVotesMapper());
        return maxVoteRecord(billList);
    }

    /**
     * Returns a list of Bill objects from the given congress.
     * Bill meta and vote information is populated.
     * Selects from a join of bills.bill_meta_embedded.
     * and bills.votes_by_bill_embedded.
     * Impala is data source.
     *
     * @param congress  {int} Query parameter to the congress field.
     * @param limit     {int} Specify limit. A limit of 0 returns all records
     *
     * @see             Bill
     * @see             BillWithVotesMapper
     */
    public List<Bill> billsWithVotesByCongress(int congress, int limit) {
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
     * Returns a list of Bill objects from the given congress.
     * Bill meta and vote information is populated.
     * Selects from a join of bills.bill_meta_embedded.
     * and bills.votes_by_bill_embedded.
     * Impala is data source.
     *
     * @param billType  {String} Query parameter to the bill_type field.
     * @param limit     {int} Specify limit. A limit of 0 returns all records
     *
     * @see             Bill
     * @see             BillWithVotesMapper
     */
    public List<Bill> billsWithVotesByBillType(String billType, int limit) {
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
     * Returns a list of Bill objects from the given congress.
     * Bill meta and vote information is populated.
     * Selects from a join of bills.bill_meta_embedded.
     * and bills.votes_by_bill_embedded.
     * Impala is data source.
     *
     * @param billType  {String} Query parameter to the bill_type field.
     * @param limit     {int} Specify limit. A limit of 0 returns all records
     *
     * @see             Bill
     * @see             BillWithVotesMapper
     */
    public List<Bill> billsWithVotesByBillTypeAndCongress(String billType, int congress, int limit) {
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
     * Returns a Bill object matching the given billId.
     * Only meta information will be populated, no votes.
     * Selects from bills.bill_meta_embedded table
     * Impala is data source.
     *
     * @param billId    {String} Query parameter to the bill_id field.
     *
     * @see             Bill
     * @see             BillMetaEmbeddedMapper
     */
    public Bill billMetaByBillId(String billId) {
        return impalaTemplate.queryForObject("SELECT * " +
                "FROM bills.bill_meta_embedded WHERE bill_id = \'" + billId + "\'",
                new BillMetaEmbeddedMapper());
    }

    /**
     * Returns a list of Bill objects matching the given billId.
     * Only meta information will be populated, no votes.
     * Selects from bills.bill_meta_embedded table
     * Impala is data source.
     *
     * @param congress  {int} Query parameter to the congress field.
     * @param limit     {int} Specify limit. A limit of 0 returns all matching records.
     *
     * @see             Bill
     * @see             BillMetaEmbeddedMapper
     */
    public List<Bill> billMetaByCongress(int congress, int limit) {
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
     * @see             Bill
     * @see             BillWithVotesMapper
     */
    public List<Bill> billMetaByBillTypeAndCongress(String billType, int congress, int limit) {
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

    private Bill maxVoteRecord(List<Bill> bills) {
        if (null == bills)
            return null;
        int index = 0;
        int size = 0;
        int max = 0;
        if(bills.size() > 1)
            System.out.println("hello there");
        for (Bill bill : bills) {
            BillVotes votes = bill.getVotes();
            int tmp = votes.getYeaTotal() + votes.getNayTotal() +
                    votes.getNotVotingTotal() + votes.getPresentTotal();
            if (tmp > size) {
                size = tmp;
                max = index;
            }
            index++;
        }
        return bills.get(max);
    }

    public void setImpalaTemplate(JdbcTemplate impalaTemplate) {
        this.impalaTemplate = impalaTemplate;
    }
}
