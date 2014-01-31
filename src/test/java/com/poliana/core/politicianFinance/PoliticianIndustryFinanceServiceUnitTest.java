package com.poliana.core.politicianFinance;

import com.poliana.core.politicianFinance.industries.PoliticianIndustryContributionTotals;
import com.poliana.core.politicianFinance.industries.PoliticianIndustryFinanceService;
import com.poliana.core.politicianFinance.industries.PoliticianIndustryHadoopRepo;
import com.poliana.core.politicianFinance.industries.PoliticianIndustryMongoRepo;
import com.poliana.core.time.TimeService;
import org.easymock.IMocksControl;
import org.junit.Before;
import org.junit.Test;
import org.mongodb.morphia.Key;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

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
    private TimeService timeServiceMock;

    private IMocksControl control;


    @Before
    public void setUp() throws Exception {

        this.control = createStrictControl();

        this.politicianIndustryMongoRepoMock = this.control.createMock(PoliticianIndustryMongoRepo.class);
        this.politicianIndustryHadoopRepoMock = this.control.createMock(PoliticianIndustryHadoopRepo.class);
        this.timeServiceMock = this.control.createMock(TimeService.class);

        this.politicianIndustryFinanceService = new PoliticianIndustryFinanceService();

        this.politicianIndustryFinanceService.setPoliticianIndustryMongoRepo(this.politicianIndustryMongoRepoMock);
        this.politicianIndustryFinanceService.setPoliticianIndustryHadoopRepo(this.politicianIndustryHadoopRepoMock);
        this.politicianIndustryFinanceService.setTimeService(timeServiceMock);
    }

    @Test
    public void testGetIndustryToPoliticianTotals_ByCongress() throws Exception {

        List<PoliticianIndustryContributionTotals> contributionsListMock = new LinkedList<>();
        contributionsListMock.add(new PoliticianIndustryContributionTotals());

        expect(this.politicianIndustryMongoRepoMock.getIndustryToPoliticianContributions("O000167", 113)).andReturn(null);
        expect(this.politicianIndustryHadoopRepoMock.getIndustryToPoliticianContributions("O000167", 113)).andReturn(contributionsListMock);
        expect(this.politicianIndustryMongoRepoMock.saveIndustryToPoliticianContributions(contributionsListMock))
                .andReturn(new ArrayList<Key<PoliticianIndustryContributionTotals>>());

        this.control.replay();

        this.politicianIndustryFinanceService.getIndustryToPoliticianTotals("O000167", 113);

        this.control.verify();
    }

    @Test
    public void testGetIndustryCategoryToPoliticianTotals_ByCongress() throws Exception {

        List<PoliticianIndustryContributionTotals> contributionsListMock = new LinkedList<>();
        contributionsListMock.add(new PoliticianIndustryContributionTotals());

        expect(this.politicianIndustryMongoRepoMock.getIndustryCategoryToPoliticianContributions("O000167", 113)).andReturn(null);
        expect(this.politicianIndustryHadoopRepoMock.getIndustryCategoryToPoliticianContributions("O000167", 113)).andReturn(contributionsListMock);
        expect(this.politicianIndustryMongoRepoMock.saveIndustryToPoliticianContributions(contributionsListMock))
                .andReturn(new ArrayList<Key<PoliticianIndustryContributionTotals>>());

        this.control.replay();

        this.politicianIndustryFinanceService.getIndustryCategoryToPoliticianTotals("O000167", 113);

        this.control.verify();
    }

    @Test
    public void testGetIndustryToPoliticianTotals_ByStartAndEnd() throws Exception {

        List<PoliticianIndustryContributionTotals> contributionsListMock = new LinkedList<>();
        contributionsListMock.add(new PoliticianIndustryContributionTotals());

        expect(this.politicianIndustryMongoRepoMock.getIndustryToPoliticianContributions("O000167", 123456789L, 123456789L)).andReturn(null);
        expect(this.politicianIndustryHadoopRepoMock.getIndustryToPoliticianContributions("O000167", 123456789L, 123456789L)).andReturn(contributionsListMock);
        expect(this.politicianIndustryMongoRepoMock.saveIndustryToPoliticianContributions(contributionsListMock))
                .andReturn(new ArrayList<Key<PoliticianIndustryContributionTotals>>());

        this.control.replay();

        this.politicianIndustryFinanceService.getIndustryToPoliticianTotals("O000167", 123456789L, 123456789L);

        this.control.verify();
    }

    @Test
    public void testGetIndustryCategoryToPoliticianTotals_ByStartAndEnd() throws Exception {

        List<PoliticianIndustryContributionTotals> contributionsListMock = new LinkedList<>();
        contributionsListMock.add(new PoliticianIndustryContributionTotals());

        expect(this.politicianIndustryMongoRepoMock.getIndustryCateogryToPoliticianContributions("O000167", 123456789L, 123456789L)).andReturn(null);
        expect(this.politicianIndustryHadoopRepoMock.getIndustryCategoryToPoliticianContributions("O000167", 123456789L, 123456789L)).andReturn(contributionsListMock);
        expect(this.politicianIndustryMongoRepoMock.saveIndustryToPoliticianContributions(contributionsListMock))
                .andReturn(new ArrayList<Key<PoliticianIndustryContributionTotals>>());

        this.control.replay();

        this.politicianIndustryFinanceService.getIndustryCategoryToPoliticianTotals("O000167", 123456789L, 123456789L);

        this.control.verify();
    }

    @Test
    public void testGetIndustryToPoliticianTotals_AllTime() throws Exception {

        List<PoliticianIndustryContributionTotals> contributionsListMock = new LinkedList<>();
        contributionsListMock.add(new PoliticianIndustryContributionTotals());

        expect(this.politicianIndustryMongoRepoMock.getIndustryToPoliticianContributions("O000167")).andReturn(null);
        expect(this.politicianIndustryHadoopRepoMock.getIndustryToPoliticianContributions("O000167")).andReturn(contributionsListMock);
        expect(this.politicianIndustryMongoRepoMock.saveIndustryToPoliticianContributions(contributionsListMock))
                .andReturn(new ArrayList<Key<PoliticianIndustryContributionTotals>>());

        this.control.replay();

        this.politicianIndustryFinanceService.getIndustryToPoliticianTotals("O000167");

        this.control.verify();
    }

    @Test
    public void testGetIndustryCategoryToPoliticianTotals_AllTime() throws Exception {

        List<PoliticianIndustryContributionTotals> contributionsListMock = new LinkedList<>();
        contributionsListMock.add(new PoliticianIndustryContributionTotals());

        expect(this.politicianIndustryMongoRepoMock.getIndustryToPoliticianContributions("O000167")).andReturn(null);
        expect(this.politicianIndustryHadoopRepoMock.getIndustryToPoliticianContributions("O000167")).andReturn(contributionsListMock);
        expect(this.politicianIndustryMongoRepoMock.saveIndustryToPoliticianContributions(contributionsListMock))
                .andReturn(new ArrayList<Key<PoliticianIndustryContributionTotals>>());

        this.control.replay();

        this.politicianIndustryFinanceService.getIndustryToPoliticianTotals("O000167");

        this.control.verify();
    }

    @Test
    public void testGetIndustryToPoliticianTotalsPerCongress__AllTime() throws Exception {


        List<PoliticianIndustryContributionTotals> contributionsListMock = new LinkedList<>();
        List<PoliticianIndustryContributionTotals> contributionsListMock1 = new LinkedList<>();

        contributionsListMock.add(new PoliticianIndustryContributionTotals());
        contributionsListMock1.add(new PoliticianIndustryContributionTotals());

        HashMap<Integer, List<PoliticianIndustryContributionTotals>> contributionsMapMock = new HashMap<>();

        contributionsMapMock.put(new Integer(110), contributionsListMock);
        contributionsMapMock.put(new Integer(111), contributionsListMock1);

        expect(this.politicianIndustryHadoopRepoMock.getIndustryToPoliticianTotalsPerCongress("O000167")).andReturn(contributionsMapMock);

        expect(this.politicianIndustryMongoRepoMock.countIndustryToPoliticianContributions("O000167", 110)).andReturn(0L);

        expect(this.politicianIndustryMongoRepoMock.saveIndustryToPoliticianContributions(contributionsListMock))
                .andReturn(new ArrayList<Key<PoliticianIndustryContributionTotals>>());

        expect(this.politicianIndustryMongoRepoMock.countIndustryToPoliticianContributions("O000167", 111)).andReturn(1L);

        this.control.replay();

        this.politicianIndustryFinanceService.getIndustryToPoliticianTotalsPerCongress("O000167");

        this.control.verify();
    }

    @Test
    public void testGetIndustryCategoryToPoliticianTotalsPerCongress__AllTime() throws Exception {

        List<PoliticianIndustryContributionTotals> contributionsListMock = new LinkedList<>();
        List<PoliticianIndustryContributionTotals> contributionsListMock1 = new LinkedList<>();

        contributionsListMock.add(new PoliticianIndustryContributionTotals());
        contributionsListMock1.add(new PoliticianIndustryContributionTotals());

        HashMap<Integer, List<PoliticianIndustryContributionTotals>> contributionsMapMock = new HashMap<>();

        contributionsMapMock.put(new Integer(110), contributionsListMock);
        contributionsMapMock.put(new Integer(111), contributionsListMock1);

        expect(this.politicianIndustryHadoopRepoMock.getIndustryCategoryToPoliticianTotalsPerCongress("O000167")).andReturn(contributionsMapMock);

        expect(this.politicianIndustryMongoRepoMock.countIndustryCategoryToPoliticianContributions("O000167", 110)).andReturn(0L);

        expect(this.politicianIndustryMongoRepoMock.saveIndustryToPoliticianContributions(contributionsListMock))
                .andReturn(new ArrayList<Key<PoliticianIndustryContributionTotals>>());

        expect(this.politicianIndustryMongoRepoMock.countIndustryCategoryToPoliticianContributions("O000167", 111)).andReturn(1L);

        this.control.replay();

        this.politicianIndustryFinanceService.getIndustryCategoryToPoliticianTotalsPerCongress("O000167");

        this.control.verify();
    }

    @Test
    public void testGetIndustryToPoliticianTotalsPerCongress__ByTimeRange() throws Exception {

        List<PoliticianIndustryContributionTotals> contributionsListMock = new LinkedList<>();
        List<PoliticianIndustryContributionTotals> contributionsListMock1 = new LinkedList<>();

        contributionsListMock.add(new PoliticianIndustryContributionTotals());
        contributionsListMock1.add(new PoliticianIndustryContributionTotals());

        HashMap<Integer, List<PoliticianIndustryContributionTotals>> contributionsMapMock = new HashMap<>();

        contributionsMapMock.put(new Integer(110), contributionsListMock);
        contributionsMapMock.put(new Integer(111), contributionsListMock1);

        expect(this.timeServiceMock.getCongressionalCyclesByTimeRange(1290935588, 1390935588)).andReturn(new Integer[] {110, 111});
        expect(this.politicianIndustryMongoRepoMock.getIndustryToPoliticianContributionsIterator("O000167", 110, 111)).andReturn(null);
        expect(this.politicianIndustryHadoopRepoMock.getIndustryToPoliticianTotalsPerCongress("O000167", 1290935588, 1390935588)).andReturn(contributionsMapMock);

        expect(this.politicianIndustryMongoRepoMock.countIndustryToPoliticianContributions("O000167", 110)).andReturn(0L);

        expect(this.politicianIndustryMongoRepoMock.saveIndustryToPoliticianContributions(contributionsListMock))
                .andReturn(new ArrayList<Key<PoliticianIndustryContributionTotals>>());

        expect(this.politicianIndustryMongoRepoMock.countIndustryToPoliticianContributions("O000167", 111)).andReturn(1L);

        this.control.replay();

        this.politicianIndustryFinanceService.getIndustryToPoliticianTotalsPerCongress("O000167", 1290935588, 1390935588);

        this.control.verify();
    }

    @Test
    public void testGetIndustryCategoryToPoliticianTotalsPerCongress__ByTimeRange() throws Exception {

        List<PoliticianIndustryContributionTotals> contributionsListMock = new LinkedList<>();
        List<PoliticianIndustryContributionTotals> contributionsListMock1 = new LinkedList<>();

        contributionsListMock.add(new PoliticianIndustryContributionTotals());
        contributionsListMock1.add(new PoliticianIndustryContributionTotals());

        HashMap<Integer, List<PoliticianIndustryContributionTotals>> contributionsMapMock = new HashMap<>();

        contributionsMapMock.put(new Integer(110), contributionsListMock);
        contributionsMapMock.put(new Integer(111), contributionsListMock1);

        expect(this.timeServiceMock.getCongressionalCyclesByTimeRange(1290935588, 1390935588)).andReturn(new Integer[] {110, 111});
        expect(this.politicianIndustryMongoRepoMock.getIndustryCategoryToPoliticianContributionsIterator("O000167", 110, 111)).andReturn(null);
        expect(this.politicianIndustryHadoopRepoMock.getIndustryCategoryToPoliticianTotalsPerCongress("O000167", 1290935588, 1390935588)).andReturn(contributionsMapMock);

        expect(this.politicianIndustryMongoRepoMock.countIndustryCategoryToPoliticianContributions("O000167", 110)).andReturn(0L);

        expect(this.politicianIndustryMongoRepoMock.saveIndustryToPoliticianContributions(contributionsListMock))
                .andReturn(new ArrayList<Key<PoliticianIndustryContributionTotals>>());

        expect(this.politicianIndustryMongoRepoMock.countIndustryCategoryToPoliticianContributions("O000167", 111)).andReturn(1L);

        this.control.replay();

        this.politicianIndustryFinanceService.getIndustryCategoryToPoliticianTotalsPerCongress("O000167", 1290935588, 1390935588);

        this.control.verify();
    }
}
