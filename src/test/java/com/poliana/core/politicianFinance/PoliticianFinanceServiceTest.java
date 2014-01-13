package com.poliana.core.politicianFinance;

import org.easymock.IMocksControl;
import org.junit.Before;
import org.junit.Test;

import static org.easymock.EasyMock.createStrictControl;

/**
 * @author David Gilmore
 * @date 1/13/14
 */
public class PoliticianFinanceServiceTest {

    private PoliticianFinanceService politicianFinanceService;

    private PoliticianIndustryMongoRepo politicianIndustryMongoRepoMock;
    private PoliticianIndustryHadoopRepo politicianIndustryHadoopRepoMock;

    private IMocksControl control;


    @Before
    public void setUp() throws Exception {

        this.control = createStrictControl();

        this.politicianIndustryMongoRepoMock = this.control.createMock(PoliticianIndustryMongoRepo.class);
        this.politicianIndustryHadoopRepoMock = this.control.createMock(PoliticianIndustryHadoopRepo.class);

        this.politicianFinanceService = new PoliticianFinanceService();

        this.politicianFinanceService.setPoliticianIndustryMongoRepo(this.politicianIndustryMongoRepoMock);
        this.politicianFinanceService.setPoliticianIndustryHadoopRepo(this.politicianIndustryHadoopRepoMock);
    }

    @Test
    public void testGetIndustryToPoliticianTotals_ByCongress() throws Exception {

    }

    @Test
    public void testGetIndustryToPoliticianTotals_ByStartAndEnd() throws Exception {

    }

    @Test
    public void testGetIndustryToPoliticianTotals_AllTime() throws Exception {

    }

    @Test
    public void testGetIndustryTotalsAllTime() throws Exception {

    }
}
