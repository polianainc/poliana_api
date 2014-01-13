package com.poliana.core.industryFinance;

import com.poliana.core.industries.IndustryRepo;
import com.poliana.core.legislators.LegislatorService;
import org.easymock.IMocksControl;
import org.junit.Before;
import org.junit.Test;

import static org.easymock.EasyMock.createStrictControl;

/**
 * @author David Gilmore
 * @date 1/12/14
 */
public class IndustryContributionServiceTest {

    private IndustryContributionService industryContributionService;

    private IndustryRepo industryRepoMock;
    private LegislatorService legislatorServiceMock;
    private IndustryContributionHadoopRepo industryContributionHadoopRepoMock;

    private IMocksControl control;


    @Before
    public void setUp() throws Exception {

        this.control = createStrictControl();

        this.industryRepoMock = this.control.createMock(IndustryRepo.class);
        this.legislatorServiceMock = this.control.createMock(LegislatorService.class);
        this.industryContributionHadoopRepoMock = this.control.createMock(IndustryContributionHadoopRepo.class);

        this.industryContributionService = new IndustryContributionService();

        this.industryContributionService.setIndustryContributionHadoopRepo(this.industryContributionHadoopRepoMock);
        this.industryContributionService.setLegislatorService(this.legislatorServiceMock);
        this.industryContributionService.setIndustryContributionHadoopRepo(industryContributionHadoopRepoMock);

    }

    @Test
    public void testGetIndustryToPoliticianTotalsByStartAndEnd() throws Exception {


    }

    @Test
    public void testGetIdeologyVsContributions() throws Exception {

    }

    @Test
    public void testGetIndustryTotals() throws Exception {

    }

    @Test
    public void testGetIndustryCategoryTotals() throws Exception {

    }

    @Test
    public void testGetIndustryTotalsByChamber() throws Exception {

    }

    @Test
    public void testGetIndustryCategoryTotalsByChamber() throws Exception {

    }

    @Test
    public void testGetIndustryToPoliticianTotals() throws Exception {

    }

    @Test
    public void testGetIndustryTotalsAllTime() throws Exception {

    }

    @Test
    public void testLegislatorReceivedIndustryTotals() throws Exception {

    }
}
