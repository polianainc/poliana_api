package com.poliana.core.politicianFinance;

import com.poliana.core.politicianFinance.pacs.PoliticianPacContributionsTotals;
import com.poliana.core.politicianFinance.pacs.PoliticianPacFinanceService;
import com.poliana.core.politicianFinance.pacs.PoliticianPacHadoopRepo;
import com.poliana.core.politicianFinance.pacs.PoliticianPacMongoRepo;
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
 * @date 1/27/14
 */
public class PoliticianPacFinanceServiceUnitTest {

    private PoliticianPacFinanceService politicianPacFinanceService;

    private PoliticianPacMongoRepo politicianPacMongoRepoMock;
    private PoliticianPacHadoopRepo politicianPacHadoopRepoMock;
    private TimeService timeServiceMock;

    private IMocksControl control;


    @Before
    public void setUp() throws Exception {

        this.control = createStrictControl();

        this.politicianPacMongoRepoMock = this.control.createMock(PoliticianPacMongoRepo.class);
        this.politicianPacHadoopRepoMock = this.control.createMock(PoliticianPacHadoopRepo.class);
        this.timeServiceMock = this.control.createMock(TimeService.class);

        this.politicianPacFinanceService = new PoliticianPacFinanceService();

        this.politicianPacFinanceService.setPoliticianPacMongoRepo(this.politicianPacMongoRepoMock);
        this.politicianPacFinanceService.setPoliticianPacHadoopRepo(this.politicianPacHadoopRepoMock);
        this.politicianPacFinanceService.setTimeService(timeServiceMock);
    }

    @Test
    public void testGetPacToPoliticianTotals() throws Exception {

        List<PoliticianPacContributionsTotals> contributionsListMock = new LinkedList<>();
        contributionsListMock.add(new PoliticianPacContributionsTotals());

        expect(this.politicianPacMongoRepoMock.getPacToPoliticianContributions("O000167")).andReturn(null);
        expect(this.politicianPacHadoopRepoMock.getPacToPoliticianContributions("O000167")).andReturn(contributionsListMock);
        expect(this.politicianPacMongoRepoMock.savePacToPoliticianContributions(contributionsListMock))
                .andReturn(new ArrayList<Key<PoliticianPacContributionsTotals>>());

        this.control.replay();

        this.politicianPacFinanceService.getPacToPoliticianTotals("O000167");

        this.control.verify();
    }

    @Test
    public void testGetPacToPoliticianTotals__ByTimeRange() throws Exception {

        List<PoliticianPacContributionsTotals> contributionsListMock = new LinkedList<>();
        contributionsListMock.add(new PoliticianPacContributionsTotals());

        expect(this.politicianPacMongoRepoMock.getPacToPoliticianContributions("O000167", 1290935588, 1390935588)).andReturn(null);
        expect(this.politicianPacHadoopRepoMock.getPacToPoliticianContributions("O000167", 1290935588, 1390935588)).andReturn(contributionsListMock);
        expect(this.politicianPacMongoRepoMock.savePacToPoliticianContributions(contributionsListMock))
                .andReturn(new ArrayList<Key<PoliticianPacContributionsTotals>>());

        this.control.replay();

        this.politicianPacFinanceService.getPacToPoliticianTotals("O000167", 1290935588, 1390935588);

        this.control.verify();
    }

    @Test
    public void testGetPacToPoliticianTotalsPerCongress__ByTimeRange() throws Exception {

        List<PoliticianPacContributionsTotals> contributionsListMock = new LinkedList<>();
        List<PoliticianPacContributionsTotals> contributionsListMock1 = new LinkedList<>();

        contributionsListMock.add(new PoliticianPacContributionsTotals());
        contributionsListMock1.add(new PoliticianPacContributionsTotals());

        HashMap<Integer, List<PoliticianPacContributionsTotals>> contributionsMapMock = new HashMap<>();

        contributionsMapMock.put(new Integer(110), contributionsListMock);
        contributionsMapMock.put(new Integer(111), contributionsListMock1);

        expect(this.timeServiceMock.getCongressionalCyclesByTimeRange(1290935588, 1390935588)).andReturn(new Integer[] {110, 111});
        expect(this.politicianPacMongoRepoMock.getPacToPoliticianContributionsIterator("O000167", 110, 111)).andReturn(null);
        expect(this.politicianPacHadoopRepoMock.getPacToPoliticianTotalsPerCongress("O000167", 1290935588, 1390935588)).andReturn(contributionsMapMock);

        expect(this.politicianPacMongoRepoMock.countPacToPoliticianContributions("O000167", 110)).andReturn(0L);

        expect(this.politicianPacMongoRepoMock.savePacToPoliticianContributions(contributionsListMock))
                .andReturn(new ArrayList<Key<PoliticianPacContributionsTotals>>());

        expect(this.politicianPacMongoRepoMock.countPacToPoliticianContributions("O000167", 111)).andReturn(1L);

        this.control.replay();

        this.politicianPacFinanceService.getPacToPoliticianTotalsPerCongress("O000167", 1290935588, 1390935588);

        this.control.verify();
    }
}
