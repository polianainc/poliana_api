package com.poliana.core.industryFinance;

import com.poliana.core.industryFinance.IndustryContributionHadoopRepo;
import com.poliana.core.industryFinance.IndustryContributionMongoRepo;
import com.poliana.core.industryFinance.entities.IndustryContributionTotalsMap;
import com.poliana.core.industryFinance.services.IndustryContributionService;
import com.poliana.core.legislators.LegislatorService;
import org.bson.types.ObjectId;
import org.easymock.IMocksControl;
import org.junit.Before;
import org.junit.Test;
import org.mongodb.morphia.Key;

import static org.easymock.EasyMock.createStrictControl;
import static org.easymock.EasyMock.expect;

/**
 * @author David Gilmore
 * @date 1/12/14
 */
public class IndustryContributionServiceUnitTest {

    private IndustryContributionService industryContributionService;

    private LegislatorService legislatorServiceMock;
    private IndustryContributionMongoRepo industryContributionMongoRepoMock;
    private IndustryContributionHadoopRepo industryContributionHadoopRepoMock;

    private IMocksControl control;


    @Before
    public void setUp() throws Exception {

        this.control = createStrictControl();

        this.legislatorServiceMock = this.control.createMock(LegislatorService.class);
        this.industryContributionMongoRepoMock = this.control.createMock(IndustryContributionMongoRepo.class);
        this.industryContributionHadoopRepoMock = this.control.createMock(IndustryContributionHadoopRepo.class);

        this.industryContributionService = new IndustryContributionService();

        this.industryContributionService.setLegislatorService(this.legislatorServiceMock);
        this.industryContributionService.setIndustryContributionMongoRepo(this.industryContributionMongoRepoMock);
        this.industryContributionService.setIndustryContributionHadoopRepo(this.industryContributionHadoopRepoMock);
    }

    @Test
    public void testGetIndustryTotals_ByCongress() {

        IndustryContributionTotalsMap contributionMap = new IndustryContributionTotalsMap();

        expect(this.industryContributionMongoRepoMock.getIndustryContributionTotalsMap("K01", 113)).andReturn(null);
        expect(this.industryContributionHadoopRepoMock.getIndustryContributionTotalsMap("K01", 113)).andReturn(contributionMap);
        expect(this.industryContributionMongoRepoMock.saveIndustryContributionTotalsMap(contributionMap))
                .andReturn(new Key<>(IndustryContributionTotalsMap.class, new ObjectId()));

        this.control.replay();
    }

    @Test
    public void testGetIndustryCategoryTotals_ByCongress() {

        IndustryContributionTotalsMap contributionMap = new IndustryContributionTotalsMap();

        expect(this.industryContributionMongoRepoMock.getIndustryCategoryContributionTotalsMap("K1000", 113)).andReturn(null);
        expect(this.industryContributionHadoopRepoMock.getIndustryCategoryContributionTotalsMap("K1000", 113)).andReturn(contributionMap);
        expect(this.industryContributionMongoRepoMock.saveIndustryContributionTotalsMap(contributionMap))
                .andReturn(new Key<>(IndustryContributionTotalsMap.class, new ObjectId()));

        this.control.replay();
    }

    @Test
    public void testGetIndustryContributionTotalsMap_ByChamberAndCongress() {

        IndustryContributionTotalsMap contributionMap = new IndustryContributionTotalsMap();

        expect(this.industryContributionMongoRepoMock.getIndustryContributionTotalsMapByChamber("K01", "s", 113)).andReturn(null);
        expect(this.industryContributionHadoopRepoMock.getIndustryContributionTotalsMapByChamber("K01", "s", 113)).andReturn(contributionMap);
        expect(this.industryContributionMongoRepoMock.saveIndustryContributionTotalsMap(contributionMap))
                .andReturn(new Key<>(IndustryContributionTotalsMap.class, new ObjectId()));

        this.control.replay();
    }

    @Test
    public void testGetIndustryCategoryContributionTotalsMap_ByChamberAndCongress() {

        IndustryContributionTotalsMap contributionMap = new IndustryContributionTotalsMap();

        expect(this.industryContributionMongoRepoMock.getIndustryCategoryContributionTotalsMap("K01", "s", 113)).andReturn(null);
        expect(this.industryContributionHadoopRepoMock.getIndustryCategoryContributionTotalsMap("K01", "s", 113)).andReturn(contributionMap);
        expect(this.industryContributionMongoRepoMock.saveIndustryContributionTotalsMap(contributionMap))
                .andReturn(new Key<>(IndustryContributionTotalsMap.class, new ObjectId()));

        this.control.replay();
    }
}
