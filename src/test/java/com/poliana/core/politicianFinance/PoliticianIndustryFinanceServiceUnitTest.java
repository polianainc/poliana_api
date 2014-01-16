package com.poliana.core.politicianFinance;

import com.poliana.core.politicianFinance.industries.IndustryPoliticianContributionTotals;
import com.poliana.core.politicianFinance.industries.PoliticianIndustryFinanceService;
import com.poliana.core.politicianFinance.industries.PoliticianIndustryHadoopRepo;
import com.poliana.core.politicianFinance.industries.PoliticianIndustryMongoRepo;
import org.easymock.IMocksControl;
import org.junit.Before;
import org.junit.Test;
import org.mongodb.morphia.Key;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import static org.easymock.EasyMock.anyObject;
import static org.easymock.EasyMock.createStrictControl;
import static org.easymock.EasyMock.expect;

/**
 * @author David Gilmore
 * @date 1/13/14
 */
public class PoliticianIndustryFinanceServiceUnitTest {

    private PoliticianIndustryFinanceService politicianIndustryFinanceService;

    private PoliticianIndustryMongoRepo politicianIndustryMongoRepoMock;
    private PoliticianIndustryHadoopRepo politicianIndustryHadoopRepoMock;

    private IMocksControl control;


    @Before
    public void setUp() throws Exception {

        this.control = createStrictControl();

        this.politicianIndustryMongoRepoMock = this.control.createMock(PoliticianIndustryMongoRepo.class);
        this.politicianIndustryHadoopRepoMock = this.control.createMock(PoliticianIndustryHadoopRepo.class);

        this.politicianIndustryFinanceService = new PoliticianIndustryFinanceService();

        this.politicianIndustryFinanceService.setPoliticianIndustryMongoRepo(this.politicianIndustryMongoRepoMock);
        this.politicianIndustryFinanceService.setPoliticianIndustryHadoopRepo(this.politicianIndustryHadoopRepoMock);
    }

    @Test
    public void testGetIndustryToPoliticianTotals_ByCongress() throws Exception {

        List<IndustryPoliticianContributionTotals> contributionsListMock = new LinkedList<>();
        contributionsListMock.add(new IndustryPoliticianContributionTotals());

        expect(this.politicianIndustryMongoRepoMock.getIndustryToPoliticianContributions("O000167", 113)).andReturn(null);
        expect(this.politicianIndustryHadoopRepoMock.getIndustryToPoliticianContributions("O000167", 113)).andReturn(contributionsListMock);
        expect(this.politicianIndustryMongoRepoMock.saveIndustryToPoliticianContributions(contributionsListMock))
                .andReturn(new ArrayList<Key<IndustryPoliticianContributionTotals>>());

        this.control.replay();

        this.politicianIndustryFinanceService.getIndustryToPoliticianTotals("O000167", 113);
    }

    @Test
    public void testGetIndustryCategoryToPoliticianTotals_ByCongress() throws Exception {

        List<IndustryPoliticianContributionTotals> contributionsListMock = new LinkedList<>();
        contributionsListMock.add(new IndustryPoliticianContributionTotals());

        expect(this.politicianIndustryMongoRepoMock.getIndustryCategoryToPoliticianContributions("O000167", 113)).andReturn(null);
        expect(this.politicianIndustryHadoopRepoMock.getIndustryCategoryToPoliticianContributions("O000167", 113)).andReturn(contributionsListMock);
        expect(this.politicianIndustryMongoRepoMock.saveIndustryToPoliticianContributions(contributionsListMock))
                .andReturn(new ArrayList<Key<IndustryPoliticianContributionTotals>>());

        this.control.replay();

        this.politicianIndustryFinanceService.getIndustryCategoryToPoliticianTotals("O000167", 113);
    }

    @Test
    public void testGetIndustryToPoliticianTotals_ByStartAndEnd() throws Exception {

        List<IndustryPoliticianContributionTotals> contributionsListMock = new LinkedList<>();
        contributionsListMock.add(new IndustryPoliticianContributionTotals());

        expect(this.politicianIndustryMongoRepoMock.getIndustryToPoliticianContributions("O000167", 123456789L, 123456789L)).andReturn(null);
        expect(this.politicianIndustryHadoopRepoMock.getIndustryToPoliticianContributions("O000167", 123456789L, 123456789L)).andReturn(contributionsListMock);
        expect(this.politicianIndustryMongoRepoMock.saveIndustryToPoliticianContributions(contributionsListMock))
                .andReturn(new ArrayList<Key<IndustryPoliticianContributionTotals>>());

        this.control.replay();

        this.politicianIndustryFinanceService.getIndustryToPoliticianTotals("O000167", 123456789L, 123456789L);
    }

    @Test
    public void testGetIndustryCategoryToPoliticianTotals_ByStartAndEnd() throws Exception {

        List<IndustryPoliticianContributionTotals> contributionsListMock = new LinkedList<>();
        contributionsListMock.add(new IndustryPoliticianContributionTotals());

        expect(this.politicianIndustryMongoRepoMock.getIndustryCateogryToPoliticianContributions("O000167", 123456789L, 123456789L)).andReturn(null);
        expect(this.politicianIndustryHadoopRepoMock.getIndustryCategoryToPoliticianContributions("O000167", 123456789L, 123456789L)).andReturn(contributionsListMock);
        expect(this.politicianIndustryMongoRepoMock.saveIndustryToPoliticianContributions(contributionsListMock))
                .andReturn(new ArrayList<Key<IndustryPoliticianContributionTotals>>());

        this.control.replay();

        this.politicianIndustryFinanceService.getIndustryCategoryToPoliticianTotals("O000167", 123456789L, 123456789L);
    }

    @Test
    public void testGetIndustryToPoliticianTotals_AllTime() throws Exception {

        List<IndustryPoliticianContributionTotals> contributionsListMock = new LinkedList<>();
        contributionsListMock.add(new IndustryPoliticianContributionTotals());

        expect(this.politicianIndustryMongoRepoMock.getIndustryToPoliticianContributions("O000167")).andReturn(null);
        expect(this.politicianIndustryHadoopRepoMock.getIndustryToPoliticianContributions("O000167")).andReturn(contributionsListMock);
        expect(this.politicianIndustryMongoRepoMock.saveIndustryToPoliticianContributions(contributionsListMock))
                .andReturn(new ArrayList<Key<IndustryPoliticianContributionTotals>>());

        this.control.replay();

        this.politicianIndustryFinanceService.getIndustryToPoliticianTotals("O000167");
    }
}
