package com.poliana.campaignFinance.repositories;

import com.poliana.campaignFinance.mappers.*;
import com.poliana.campaignFinance.models.demo.Recipient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import com.poliana.campaignFinance.entities.*;

import java.util.List;

@Repository
public class ContributionHadoopRepo {

    @Autowired
    protected JdbcTemplate hiveTemplate;

    @Autowired
    protected JdbcTemplate impalaTemplate;

    /**
     * Returns a list of industry to politician contributions
     * for a given year and month. If month and/or limit are to be ignored,
     * call them with values zero.
     * Impala is data source.
     *
     * @param year      {int} Query parameter to the bill_id field.
     * @param month     {int} Values 0-12. Zero will return all totals from the specified year
     * @param limit     {int} A limit of 0 will return all totals for the given year/month
     * @param orderBy   {String} One of contributions_count and contributions_total
     *
     * @see             com.poliana.campaignFinance.entities.IndToPolContrTotals
     * @see             com.poliana.campaignFinance.mappers.IndToPolContrTotalsMapper
     */
    public List<IndToPolContrTotals> topIndToPolContrTotalsByYearMonth (
            int year, int month, int limit, String orderBy) {
        String mon = "";
        String lim = "";
        if (month != 0)
            mon = " AND month = " + month;

        String orderLC = orderBy.toLowerCase();
        if(orderLC.contains("count"))
            orderBy = " ORDER BY contributions_count DESC ";
        else if (orderLC.contains("amount") || orderLC.contains("total"))
            orderBy = " ORDER BY contributions_total DESC ";
        else
            orderBy = "";

        if (limit != 0)
            lim = " LIMIT " + limit;

        String query = "SELECT * FROM campaign_finance.industry_to_pol_contribution_monthly_totals " +
                "WHERE year = " + year + mon + orderBy + lim;

        return impalaTemplate.query(query, new IndToPolContrTotalsMapper());
    }

    /**
     * Returns a list of industry to politician contributions
     * for a given year and month. If month and/or limit are to be ignored,
     * call them with values zero.
     * Impala is data source.
     *
     * @param industryId{String} Query parameter to the industry_id field
     * @param year      {int} Query parameter to the bill_id field.
     * @param month     {int} Values 0-12. Zero will return all totals from the specified year
     * @param limit     {int} A limit of 0 will return all totals for the given year/month
     * @param orderBy   {String} One of contributions_count and contributions_total
     *
     * @see             com.poliana.campaignFinance.entities.IndToPolContrTotals
     * @see             com.poliana.campaignFinance.mappers.IndToPolContrTotalsMapper
     */
    public List<IndToPolContrTotals> topIndToPolContrTotalsByIndustryYearMonth(
            String industryId, int year, int month, int limit, String orderBy) {
        String mon = "";
        String lim = "";
        if (month != 0)
            mon = " AND month = " + month;

        String orderLC = orderBy.toLowerCase();
        if(orderLC.contains("count"))
            orderBy = " ORDER BY contributions_count DESC ";
        else if (orderLC.contains("amount") || orderLC.contains("total"))
            orderBy = " ORDER BY contributions_total DESC ";
        else
            orderBy = "";

        if (limit != 0)
            lim = " LIMIT " + limit;

        String query = "SELECT * FROM campaign_finance.industry_to_pol_contribution_monthly_totals " +
                "WHERE industry_id = \'" + industryId + "\' AND year = " + year + mon + orderBy + lim;

        return impalaTemplate.query(query, new IndToPolContrTotalsMapper());
    }

    /**
     * Returns a list of industry to politician contributions
     * for a given year and month. If month and/or limit are to be ignored,
     * call them with values zero.
     * Impala is data source.
     *
     * @param bioguideId{String} Query parameter to the bioguide_id field
     * @param year      {int} Query parameter to the bill_id field.
     * @param month     {int} Values 0-12. Zero will return all totals from the specified year
     * @param limit     {int} A limit of 0 will return all totals for the given year/month
     * @param orderBy   {String} One of contributions_count and contributions_total
     *
     * @see             com.poliana.campaignFinance.entities.IndToPolContrTotals
     * @see             com.poliana.campaignFinance.mappers.IndToPolContrTotalsMapper
     */
    public List<IndToPolContrTotals> topIndToPolContrTotalsByBioguideYearMonth(
            String bioguideId, int year, int month, int limit, String orderBy) {
        String mon = "";
        String lim = "";
        if (month != 0)
            mon = " AND month = " + month;

        String orderLC = orderBy.toLowerCase();
        if(orderLC.contains("count"))
            orderBy = " ORDER BY contributions_count DESC ";
        else if (orderLC.contains("amount") || orderLC.contains("total"))
            orderBy = " ORDER BY contributions_total DESC ";
        else
            orderBy = "";

        if (limit != 0)
            lim = " LIMIT " + limit;

        String query = "SELECT * FROM campaign_finance.industry_to_pol_contribution_monthly_totals " +
                "WHERE  bioguide_id = \'" + bioguideId + "\' AND year = " + year + mon + orderBy + lim;

        return impalaTemplate.query(query, new IndToPolContrTotalsMapper());
    }

    /**
     * Returns a list of industry to politician contributions
     * for a given year and month. If month and/or limit are to be ignored,
     * call them with values zero.
     * Impala is data source.
     *
     * @param industryId{String} Query parameter to the industry_id field
     * @param year      {int} Query parameter to the bill_id field.
     * @param month     {int} Values 0-12. Zero will return all totals from the specified year
     */
    public List<IndPartyTotals> indPartyContrTotalsByIndustryYearMonth(
            String industryId, int year, int month) {
        String query = "SELECT l.party, count(l.party), sum(c.amount), c.year, c.month FROM " +
                "campaign_finance.individual_contributions c JOIN entities.candidate_ids l " +
                "ON c.recip_id = l.recipient_ext_id WHERE year = " + year + " AND month = " + month +
                " AND real_code = \'" + industryId + "\' GROUP BY l.party, c.year, c.month";

        return impalaTemplate.query(query, new IndPartyTotalsMapper());
    }

    /**
     * Returns a list of industry to politician contributions
     * for a given year and month. If month and/or limit are to be ignored,
     * call them with values zero.
     * Impala is data source.
     *
     * @param year      {int} Query parameter to the bill_id field.
     * @param month     {int} Values 0-12. Zero will return all totals from the specified year
     * @param limit     {int} A limit of 0 will return all totals for the given year/month
     *
     * @see             com.poliana.campaignFinance.entities.IndToPolContrTotals
     * @see             com.poliana.campaignFinance.mappers.IndToPolContrTotalsMapper
     */
    public List<IndToPolContrTotals> indToPolContrTotalsByYearAndMonth(int year, int month, int limit) {
        String mon = "";
        String lim = "";
        if (month != 0)
            mon = " AND month = " + month;

        if (limit != 0)
            lim = " LIMIT " + limit;

        String query = "SELECT * FROM campaign_finance.industry_to_pol_contribution_monthly_totals " +
                "WHERE year = " + year + mon + lim;

        return impalaTemplate.query(query, new IndToPolContrTotalsMapper());
    }




////////////////////////////////////////////////////////////////////////////////////////////////////////////

////////////////////////////////////////////////////////////////////////////////////////////////////////////




    public List<Recipient> topIndustryRecipients(String billId, String vote, int limit) {
        String lim = "";

        if (limit != 0)
            lim = " LIMIT " + limit;

        List<Recipient> topRecipients = hiveTemplate.query("" +
                "SELECT industry_id, bioguide_id, first_name, last_name, sum(amount) AS sum " +
                "FROM industry_contr_" + billId + "_113_" + vote + "_6months" +
                " GROUP BY industry_id, bioguide_id, first_name, last_name" + lim,
                new RecipientMapper());
        return topRecipients;
    }

    public List<Recipient> topIndustryRecipients(String billId, String vote) {
        return topIndustryRecipients(billId, vote, 0);
    }

    public int countDistinctIndustryContributions(String billId, String vote) {
        return hiveTemplate.queryForInt("SELECT count(distinct(bioguide_id)) FROM " +
                "industry_contr_" + billId + "_113_" + vote + "_6months");
    }

    public int sumIndustryContributions(String billId, String vote) {
        return hiveTemplate.queryForInt("SELECT sum(" + vote + "_sum)FROM " + billId + "_trends");
    }

    public List<IndustryContrTotals> industryContributionTotals(String billId) {
        List<IndustryContrTotals> table = hiveTemplate.query("SELECT * FROM "
                + billId + "_trends", new IndustryContrTotalsMapper());
        return table;
    }
}