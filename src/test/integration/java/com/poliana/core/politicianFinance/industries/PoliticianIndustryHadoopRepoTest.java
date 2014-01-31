package com.poliana.core.politicianFinance.industries;

import com.poliana.config.ApplicationConfig;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.AnnotationConfigContextLoader;

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
    public void testGetIndustryToPoliticianContributions_ByCongress() throws Exception {

        List<PoliticianIndustryContributionTotals> totals =
                politicianIndustryHadoopRepo.getIndustryToPoliticianContributions("O000167", 110);

        assertNotNull(totals);

        PoliticianIndustryContributionTotals total = totals.get(0);

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

        List<PoliticianIndustryContributionTotals> totals =
                politicianIndustryHadoopRepo.getIndustryCategoryToPoliticianContributions("O000167", 110);

        assertNotNull(totals);

        PoliticianIndustryContributionTotals total = totals.get(0);

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

        List<PoliticianIndustryContributionTotals> totals =
                politicianIndustryHadoopRepo.getIndustryToPoliticianContributions("O000167", 1189796049, 1289796049);

        assertNotNull(totals);

        PoliticianIndustryContributionTotals total = totals.get(0);

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

        List<PoliticianIndustryContributionTotals> totals =
                politicianIndustryHadoopRepo.getIndustryCategoryToPoliticianContributions("O000167", 1189796049, 1289796049);

        assertNotNull(totals);

        PoliticianIndustryContributionTotals total = totals.get(0);

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
    public void testGetIndustryToPoliticianContributions() throws Exception {

        List<PoliticianIndustryContributionTotals> totals =
                politicianIndustryHadoopRepo.getIndustryToPoliticianContributions("O000167");

        assertNotNull(totals);

        PoliticianIndustryContributionTotals total = totals.get(0);

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

        List<PoliticianIndustryContributionTotals> totals =
                politicianIndustryHadoopRepo.getIndustryCategoryToPoliticianContributions("O000167");

        assertNotNull(totals);

        PoliticianIndustryContributionTotals total = totals.get(0);

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

    @Autowired
    public void setPoliticianIndustryHadoopRepo(PoliticianIndustryHadoopRepo politicianIndustryHadoopRepo) {
        this.politicianIndustryHadoopRepo = politicianIndustryHadoopRepo;
    }
}
