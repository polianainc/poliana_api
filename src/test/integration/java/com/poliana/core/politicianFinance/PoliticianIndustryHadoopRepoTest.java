package com.poliana.core.politicianFinance;

import com.poliana.config.ApplicationConfig;
import com.poliana.core.politicianFinance.industries.PoliticianIndustryContributionsTotals;
import com.poliana.core.politicianFinance.industries.PoliticianIndustryHadoopRepo;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.AnnotationConfigContextLoader;

import java.util.HashMap;
import java.util.List;

import static org.junit.Assert.*;

/**
 * @author David Gilmore
 * @date 1/14/14
 */
@Profile("integration_test")
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes=ApplicationConfig.class, loader=AnnotationConfigContextLoader.class)
public class PoliticianIndustryHadoopRepoTest {

    private PoliticianIndustryHadoopRepo politicianIndustryHadoopRepo;

    @Test
    public void testGetIndustryToPoliticianContributions() throws Exception {

        List<PoliticianIndustryContributionsTotals> totals =
                politicianIndustryHadoopRepo.getIndustryToPoliticianContributions("O000167");

        assertNotNull(totals);

        PoliticianIndustryContributionsTotals total = totals.get(0);

        assertEquals("O000167", total.getBioguideId());

        assertNotNull(total.getIndustryId());
        assertNotNull(total.getFirstName());
        assertNotNull(total.getLastName());
        assertNotNull(total.getIndustryName());
        assertNotNull(total.getSectorLong());
        assertNotNull(total.getParty());
        assertNotNull(total.getReligion());
        assertNotNull(total.getContributionCount());
        assertNotNull(total.getContributionSum());

        assertNull(total.getCategoryId());
        assertNull(total.getBeginTimestamp());
        assertNull(total.getEndTimestamp());
        assertNull(total.getCongress());
        assertNull(total.getYear());
        assertNull(total.getMonth());
    }

    @Test
    public void testGetIndustryCategoryToPoliticianContributions() throws Exception {

        List<PoliticianIndustryContributionsTotals> totals =
                politicianIndustryHadoopRepo.getIndustryCategoryToPoliticianContributions("O000167");

        assertNotNull(totals);

        PoliticianIndustryContributionsTotals total = totals.get(0);

        assertEquals("O000167", total.getBioguideId());

        assertNotNull(total.getCategoryId());
        assertNotNull(total.getFirstName());
        assertNotNull(total.getLastName());
        assertNotNull(total.getIndustryName());
        assertNotNull(total.getCategoryName());
        assertNotNull(total.getSector());
        assertNotNull(total.getSectorLong());
        assertNotNull(total.getParty());
        assertNotNull(total.getReligion());
        assertNotNull(total.getContributionCount());
        assertNotNull(total.getContributionSum());

        assertNull(total.getIndustryId());
        assertNull(total.getBeginTimestamp());
        assertNull(total.getEndTimestamp());
        assertNull(total.getCongress());
        assertNull(total.getYear());
        assertNull(total.getMonth());
    }

    @Test
    public void testGetIndustryToPoliticianContributions_ByCongress() throws Exception {

        List<PoliticianIndustryContributionsTotals> totals =
                politicianIndustryHadoopRepo.getIndustryToPoliticianContributions("O000167", 110);

        assertNotNull(totals);

        PoliticianIndustryContributionsTotals total = totals.get(0);

        assertEquals("O000167", total.getBioguideId());
        assertEquals(110, total.getCongress().intValue());

        assertNotNull(total.getIndustryId());
        assertNotNull(total.getFirstName());
        assertNotNull(total.getLastName());
        assertNotNull(total.getIndustryName());
        assertNotNull(total.getSector());
        assertNotNull(total.getSectorLong());
        assertNotNull(total.getParty());
        assertNotNull(total.getReligion());
        assertNotNull(total.getContributionCount());
        assertNotNull(total.getContributionSum());

        assertNull(total.getCategoryId());
        assertNull(total.getBeginTimestamp());
        assertNull(total.getEndTimestamp());
        assertNull(total.getYear());
        assertNull(total.getMonth());
    }

    @Test
    public void testGetIndustryCategoryToPoliticianContributions_ByCongress() throws Exception {

        List<PoliticianIndustryContributionsTotals> totals =
                politicianIndustryHadoopRepo.getIndustryCategoryToPoliticianContributions("O000167", 110);

        assertNotNull(totals);

        PoliticianIndustryContributionsTotals total = totals.get(0);

        assertEquals("O000167", total.getBioguideId());
        assertEquals(110, total.getCongress().intValue());

        assertNotNull(total.getCategoryId());
        assertNotNull(total.getFirstName());
        assertNotNull(total.getLastName());
        assertNotNull(total.getIndustryName());
        assertNotNull(total.getCategoryName());
        assertNotNull(total.getSector());
        assertNotNull(total.getSectorLong());
        assertNotNull(total.getParty());
        assertNotNull(total.getReligion());
        assertNotNull(total.getContributionCount());
        assertNotNull(total.getContributionSum());

        assertNull(total.getIndustryId());
        assertNull(total.getBeginTimestamp());
        assertNull(total.getEndTimestamp());
        assertNull(total.getYear());
        assertNull(total.getMonth());
    }

    @Test
    public void testGetIndustryToPoliticianContributions_ByTimeRange() throws Exception {

        List<PoliticianIndustryContributionsTotals> totals =
                politicianIndustryHadoopRepo.getIndustryToPoliticianContributions("O000167", 1189796049, 1289796049);

        assertNotNull(totals);

        PoliticianIndustryContributionsTotals total = totals.get(0);

        assertEquals("O000167", total.getBioguideId());
        assertEquals(1189796049, total.getBeginTimestamp().intValue());
        assertEquals(1289796049, total.getEndTimestamp().intValue());

        assertNotNull(total.getIndustryId());
        assertNotNull(total.getFirstName());
        assertNotNull(total.getLastName());
        assertNotNull(total.getIndustryName());
        assertNotNull(total.getSector());
        assertNotNull(total.getSectorLong());
        assertNotNull(total.getParty());
        assertNotNull(total.getReligion());
        assertNotNull(total.getContributionCount());
        assertNotNull(total.getContributionSum());

        assertNull(total.getCategoryId());
        assertNull(total.getCategoryName());
        assertNull(total.getCongress());
        assertNull(total.getYear());
        assertNull(total.getMonth());
    }

    @Test
    public void testGetIndustryCategoryToPoliticianContributions_ByTimeRange() throws Exception {

        List<PoliticianIndustryContributionsTotals> totals =
                politicianIndustryHadoopRepo.getIndustryCategoryToPoliticianContributions("O000167", 1189796049, 1289796049);

        assertNotNull(totals);

        PoliticianIndustryContributionsTotals total = totals.get(0);

        assertEquals("O000167", total.getBioguideId());
        assertEquals(1189796049, total.getBeginTimestamp().intValue());
        assertEquals(1289796049, total.getEndTimestamp().intValue());

        assertNotNull(total.getCategoryId());
        assertNotNull(total.getFirstName());
        assertNotNull(total.getLastName());
        assertNotNull(total.getIndustryName());
        assertNotNull(total.getCategoryName());
        assertNotNull(total.getSector());
        assertNotNull(total.getSectorLong());
        assertNotNull(total.getParty());
        assertNotNull(total.getReligion());
        assertNotNull(total.getContributionCount());
        assertNotNull(total.getContributionSum());

        assertNull(total.getIndustryId());
        assertNull(total.getCongress());
        assertNull(total.getYear());
        assertNull(total.getMonth());
    }


    @Test
    public void testGetIndustryToPoliticianTotalsPerCongress_AllTime() throws Exception {

        HashMap<Integer, List<PoliticianIndustryContributionsTotals>> totalsMap =
                politicianIndustryHadoopRepo.getIndustryToPoliticianTotalsPerCongress("O000167");

        assertNotNull(totalsMap);

        assertNotNull(totalsMap.get(108));
        assertNotNull(totalsMap.get(109));

        List<PoliticianIndustryContributionsTotals> contributions110 = totalsMap.get(110);

        PoliticianIndustryContributionsTotals contributionFrom110 = contributions110.get(0);

        assertEquals("O000167", contributionFrom110.getBioguideId());
        assertEquals("Barack", contributionFrom110.getFirstName());
        assertEquals("Obama", contributionFrom110.getLastName());
        assertEquals("Democrat", contributionFrom110.getParty());
        assertEquals("United Church of Christ", contributionFrom110.getReligion());

        assertEquals(new Integer(110), contributionFrom110.getCongress());

        assertNotNull(contributionFrom110.getIndustryId());
        assertNotNull(contributionFrom110.getIndustryName());

        assertNotNull(contributionFrom110.getContributionCount());
        assertNotNull(contributionFrom110.getContributionSum());
    }

    @Test
    public void testGetIndustryCategoryeToPoliticianTotalsPerCongress_AllTime() throws Exception {

    }

    @Test
    public void testGetIndustryToPoliticianTotalsPerCongress_ByTimeRange() throws Exception {

        HashMap<Integer, List<PoliticianIndustryContributionsTotals>> totalsMap =
                politicianIndustryHadoopRepo.getIndustryToPoliticianTotalsPerCongress("O000167", 1075429263, 1201727289);

        assertNotNull(totalsMap);

        assertNotNull(totalsMap.get(108));
        assertNotNull(totalsMap.get(109));
        List<PoliticianIndustryContributionsTotals> contributions110 = totalsMap.get(110);

        PoliticianIndustryContributionsTotals contributionFrom110 = contributions110.get(0);

        assertEquals("O000167", contributionFrom110.getBioguideId());
        assertEquals("Barack", contributionFrom110.getFirstName());
        assertEquals("Obama", contributionFrom110.getLastName());

        assertEquals("Democrat", contributionFrom110.getParty());
        assertEquals("United Church of Christ", contributionFrom110.getReligion());

        assertEquals(new Integer(110), contributionFrom110.getCongress());

        assertNotNull(contributionFrom110.getContributionCount());
        assertNotNull(contributionFrom110.getContributionSum());
    }

    @Test
    public void testGetIndustryCategoryToPoliticianTotalsPerCongress__ByTimeRange() throws Exception {


    }

    @Autowired
    public void setPoliticianIndustryHadoopRepo(PoliticianIndustryHadoopRepo politicianIndustryHadoopRepo) {
        this.politicianIndustryHadoopRepo = politicianIndustryHadoopRepo;
    }
}
