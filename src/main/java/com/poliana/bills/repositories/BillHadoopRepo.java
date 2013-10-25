package com.poliana.bills.repositories;

import com.poliana.bills.entities.Bill;
import com.poliana.bills.entities.BillAction;
import com.poliana.bills.mappers.*;
import com.poliana.bills.entities.BillVotes;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

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
        return impalaTemplate.queryForObject("" +
                "SELECT * FROM " +
                "bills.bill_meta_embedded b JOIN bills.votes_by_bill_embedded v " +
                "ON b.vote_id = v.vote_id WHERE bill_id = \'" + billId + "\'",
                new BillWithVotesMapper());
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
                new BillMapper());
    }

    /**
     * Returns a list of BillVotes objects matching the given billId.
     * Only meta information will be populated, no votes.
     * Selects from bills.votes_by_bill_embedded table
     * Impala is data source.
     *
     * @param billId  {String} Query parameter to the bill_id field.
     *
     * @see             Bill
     * @see             BillMetaEmbeddedMapper
     */
    public BillVotes billVotesByBillId(String billId) {
        return impalaTemplate.queryForObject("SELECT * " +
                "FROM bills.votes_by_bill_embedded WHERE vote_id = \'" + billId + "\'",
                new BillVotesMapper());
    }

    /**
     * Returns a list of BillVotes objects from the given congress.
     * Selects from bills.votes_by_bill_embedded table.
     * Impala is data source.
     *
     * @param congress  {int}
     *
     * @see             BillVotes
     * @see             BillMetaEmbeddedMapper
     */
    public List<BillVotes> billVotesByCongress(int congress, int limit) {
        String lim = "";
        if (limit > 0)
            lim = " LIMIT " + limit;

        return impalaTemplate.query("SELECT * " +
                "FROM bills.votes_by_bill_embedded WHERE congress = " + congress + lim,
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
                "FROM bills.votes_by_bill WHERE year = " + year + " LIMIT " + limit,
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
}
