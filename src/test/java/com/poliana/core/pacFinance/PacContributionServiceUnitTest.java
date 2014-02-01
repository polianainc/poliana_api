package com.poliana.core.pacFinance;

import com.poliana.core.pacFinance.entities.PacContributionTotalsMap;
import com.poliana.core.pacFinance.repositories.PacContributionHadoopRepo;
import com.poliana.core.pacFinance.repositories.PacContributionMongoRepo;
import org.bson.types.ObjectId;
import org.easymock.IMocksControl;
import org.junit.Before;
import org.junit.Test;
import org.mongodb.morphia.Key;

import static org.easymock.EasyMock.createStrictControl;
import static org.easymock.EasyMock.expect;

/**
 * @author David Gilmore
 * @date 1/30/14
 */
public class PacContributionServiceUnitTest {

    private PacContributionService pacContributionService;

    private PacContributionMongoRepo pacContributionMongoRepoMock;
    private PacContributionHadoopRepo pacContributionHadoopRepoMock;

    private IMocksControl control;


    @Before
    public void setUp() throws Exception {

        this.control = createStrictControl();

        this.pacContributionMongoRepoMock = this.control.createMock(PacContributionMongoRepo.class);
        this.pacContributionHadoopRepoMock = this.control.createMock(PacContributionHadoopRepo.class);

        this.pacContributionService = new PacContributionService();

        this.pacContributionService.setPacContributionMongoRepo(this.pacContributionMongoRepoMock);
        this.pacContributionService.setPacContributionHadoopRepo(this.pacContributionHadoopRepoMock);
    }

    @Test
    public void testGetPacTotals_ByCongress() {

        PacContributionTotalsMap contributionMap = new PacContributionTotalsMap();

        expect(this.pacContributionMongoRepoMock.getPacContributionTotalsMap("K01", 113)).andReturn(null);
        expect(this.pacContributionHadoopRepoMock.getPacContributionTotalsMap("K01", 113)).andReturn(contributionMap);
        expect(this.pacContributionMongoRepoMock.savePacContributionTotalsMap(contributionMap))
                .andReturn(new Key<>(PacContributionTotalsMap.class, new ObjectId()));

        this.control.replay();

        this.pacContributionService.getPacContributionTotalsMap("K01", 113);

        this.control.verify();
    }

    @Test
    public void testGetPacContributionTotalsMap_ByChamberAndCongress() {

        PacContributionTotalsMap contributionMap = new PacContributionTotalsMap();

        expect(this.pacContributionMongoRepoMock.getPacContributionTotalsMap("K01", "s", 113)).andReturn(null);
        expect(this.pacContributionHadoopRepoMock.getPacContributionTotalsMap("K01", "s", 113)).andReturn(contributionMap);
        expect(this.pacContributionMongoRepoMock.savePacContributionTotalsMap(contributionMap))
                .andReturn(new Key<>(PacContributionTotalsMap.class, new ObjectId()));

        this.control.replay();

        this.pacContributionService.getPacContributionTotalsMap("K01", "s", 113);

        this.control.verify();
    }

    @Test
    public void testGetPacContributionTotalsMap_ByTimeRange() {

        PacContributionTotalsMap contributionMap = new PacContributionTotalsMap();

        expect(this.pacContributionMongoRepoMock.getPacContributionTotalsMap("K01", 1230185819, 1290185819)).andReturn(null);
        expect(this.pacContributionHadoopRepoMock.getPacContributionTotalsMap("K01", 1230185819, 1290185819)).andReturn(contributionMap);
        expect(this.pacContributionMongoRepoMock.savePacContributionTotalsMap(contributionMap))
                .andReturn(new Key<>(PacContributionTotalsMap.class, new ObjectId()));

        this.control.replay();

        this.pacContributionService.getPacContributionTotalsMap("K01", 1230185819, 1290185819);

        this.control.verify();
    }

    @Test
    public void testGetPacContributionTotalsMap_ByChamberAndTimeRange() {

        PacContributionTotalsMap contributionMap = new PacContributionTotalsMap();

        expect(this.pacContributionMongoRepoMock.getPacContributionTotalsMap("K01", "s", 1230185819, 1290185819)).andReturn(null);
        expect(this.pacContributionHadoopRepoMock.getPacContributionTotalsMap("K01", "s", 1230185819, 1290185819)).andReturn(contributionMap);
        expect(this.pacContributionMongoRepoMock.savePacContributionTotalsMap(contributionMap))
                .andReturn(new Key<>(PacContributionTotalsMap.class, new ObjectId()));

        this.control.replay();

        this.pacContributionService.getPacContributionTotalsMap("K01", "s", 1230185819, 1290185819);

        this.control.verify();
    }

}
