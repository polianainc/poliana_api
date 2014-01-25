package com.poliana.core.industryFinance;

import com.poliana.config.ApplicationConfig;
import com.poliana.core.industryFinance.entities.IndustryContributionTotalsMap;
import com.poliana.core.politicianFinance.industries.IndustryPoliticianContributionTotals;
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
 * @date 1/13/14
 */
@Profile("integration_test")
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes=ApplicationConfig.class, loader=AnnotationConfigContextLoader.class)
public class IndustryContributionHadoopRepoTest {

    private IndustryContributionHadoopRepo industryContributionHadoopRepo;

    @Test
    public void testGetIndustryContributionTotalsMap() {

        IndustryContributionTotalsMap totalsMap =
                industryContributionHadoopRepo.getIndustryContributionTotalsMap("K01", 110);

        assertNotNull(totalsMap);

        assertEquals("K01", totalsMap.getIndustryId());
        assertEquals(new Integer(110), totalsMap.getCongress());

        assertNotNull(totalsMap.getIndustryName());
        assertNotNull(totalsMap.getCategoryName());
        assertNotNull(totalsMap.getSector());
        assertNotNull(totalsMap.getSectorLong());
        assertNotNull(totalsMap.getSums());
    }

    @Test
    public void testGetIndustryCategoryContributionTotalsMap() {

        IndustryContributionTotalsMap totalsMap =
                industryContributionHadoopRepo.getIndustryCategoryContributionTotalsMap("K1000", 110);

        assertNotNull(totalsMap);

        assertEquals("K1000", totalsMap.getCategoryId());
        assertEquals(new Integer(110), totalsMap.getCongress());

        assertNotNull(totalsMap.getCategoryId());
        assertNotNull(totalsMap.getIndustryName());
        assertNotNull(totalsMap.getCategoryName());
        assertNotNull(totalsMap.getSector());
        assertNotNull(totalsMap.getSectorLong());
        assertNotNull(totalsMap.getSums());
    }

    @Test
    public void testGetIndustryContributionTotalsMapByChamber_Senate() {

        IndustryContributionTotalsMap totalsMap =
                industryContributionHadoopRepo.getIndustryContributionTotalsMapByChamber("K01", "s", 110);

        assertNotNull(totalsMap);

        assertEquals("K01", totalsMap.getIndustryId());
        assertEquals(new Integer(110), totalsMap.getCongress());
        assertEquals("s", totalsMap.getChamber());

        assertNotNull(totalsMap.getIndustryName());
        assertNotNull(totalsMap.getCategoryName());
        assertNotNull(totalsMap.getSector());
        assertNotNull(totalsMap.getSectorLong());
        assertNotNull(totalsMap.getSums());
    }

    @Test
    public void testGetIndustryContributionTotalsMapByChamber_House() {

        IndustryContributionTotalsMap totalsMap =
                industryContributionHadoopRepo.getIndustryContributionTotalsMapByChamber("K01", "h", 110);

        assertNotNull(totalsMap);

        assertEquals("K01", totalsMap.getIndustryId());
        assertEquals(new Integer(110), totalsMap.getCongress());
        assertEquals("h", totalsMap.getChamber());

        assertNotNull(totalsMap.getIndustryName());
        assertNotNull(totalsMap.getCategoryName());
        assertNotNull(totalsMap.getSector());
        assertNotNull(totalsMap.getSectorLong());
        assertNotNull(totalsMap.getSums());
    }

    @Test
    public void testGetIndustryCategoryContributionTotalsMapByChamber_Senate() {

        IndustryContributionTotalsMap totalsMap =
                industryContributionHadoopRepo.getIndustryCategoryContributionTotalsMap("K1000", "s", 110);

        assertNotNull(totalsMap);

        assertEquals("K1000", totalsMap.getCategoryId());
        assertEquals(new Integer(110), totalsMap.getCongress());
        assertEquals("s", totalsMap.getChamber());

        assertNotNull(totalsMap.getIndustryName());
        assertNotNull(totalsMap.getCategoryName());
        assertNotNull(totalsMap.getSector());
        assertNotNull(totalsMap.getSectorLong());
        assertNotNull(totalsMap.getSums());
    }

    @Test
    public void testGetIndustryCategoryContributionTotalsMapByChamber_House() {

        IndustryContributionTotalsMap totalsMap =
                industryContributionHadoopRepo.getIndustryCategoryContributionTotalsMap("K1000", "h", 110);

        assertNotNull(totalsMap);

        assertEquals("K1000", totalsMap.getCategoryId());
        assertEquals(new Integer(110), totalsMap.getCongress());
        assertEquals("h", totalsMap.getChamber());

        assertNotNull(totalsMap.getIndustryName());
        assertNotNull(totalsMap.getCategoryName());
        assertNotNull(totalsMap.getSector());
        assertNotNull(totalsMap.getSectorLong());
        assertNotNull(totalsMap.getSums());
    }

    @Test
    public void testGetIndustryContributionTotalsMapByTimeRange_Senate() {

        IndustryContributionTotalsMap totalsMap =
                industryContributionHadoopRepo.getIndustryContributionTotalsMapByChamber("K01", "s", 1230185819, 1290185819);

        assertNotNull(totalsMap);

        assertEquals("K01", totalsMap.getIndustryId());
        assertEquals("s", totalsMap.getChamber());
        assertEquals(new Long(1230185819), totalsMap.getBeginTimestamp());
        assertEquals(new Long(1290185819), totalsMap.getEndTimestamp());

        assertNotNull(totalsMap.getIndustryName());
        assertNotNull(totalsMap.getCategoryName());
        assertNotNull(totalsMap.getSector());
        assertNotNull(totalsMap.getSectorLong());
        assertNotNull(totalsMap.getSums());
    }

    @Test
    public void testGetIndustryContributionTotalsMapByTimeRange_House() {

        IndustryContributionTotalsMap totalsMap =
                industryContributionHadoopRepo.getIndustryContributionTotalsMapByChamber("K01", "h", 1230185819, 1290185819);

        assertEquals("K01", totalsMap.getIndustryId());
        assertEquals("h", totalsMap.getChamber());
        assertEquals(new Long(1230185819), totalsMap.getBeginTimestamp());
        assertEquals(new Long(1290185819), totalsMap.getEndTimestamp());

        assertNotNull(totalsMap.getIndustryName());
        assertNotNull(totalsMap.getCategoryName());
        assertNotNull(totalsMap.getSector());
        assertNotNull(totalsMap.getSectorLong());
        assertNotNull(totalsMap.getSums());
    }

    @Test
    public void testGetIndustryCategoryContributionTotalsMapByTimeRange_Senate() {

        IndustryContributionTotalsMap totalsMap =
                industryContributionHadoopRepo.getIndustryCategoryContributionTotalsMapByChamber("K1000", "s", 1230185819, 1290185819);

        assertNotNull(totalsMap);

        assertEquals("K1000", totalsMap.getCategoryId());
        assertEquals("s", totalsMap.getChamber());
        assertEquals(new Long(1230185819), totalsMap.getBeginTimestamp());
        assertEquals(new Long(1290185819), totalsMap.getEndTimestamp());

        assertNotNull(totalsMap.getIndustryName());
        assertNotNull(totalsMap.getCategoryName());
        assertNotNull(totalsMap.getSector());
        assertNotNull(totalsMap.getSectorLong());
        assertNotNull(totalsMap.getSums());
    }

    @Test
    public void testGetIndustryCategoryContributionTotalsMapByTimeRange_House() {

        IndustryContributionTotalsMap totalsMap =
                industryContributionHadoopRepo.getIndustryCategoryContributionTotalsMapByChamber("K1000", "h", 1230185819, 1290185819);

        assertEquals("K1000", totalsMap.getCategoryId());
        assertEquals("h", totalsMap.getChamber());
        assertEquals(new Long(1230185819), totalsMap.getBeginTimestamp());
        assertEquals(new Long(1290185819), totalsMap.getEndTimestamp());

        assertNotNull(totalsMap.getIndustryName());
        assertNotNull(totalsMap.getCategoryName());
        assertNotNull(totalsMap.getSector());
        assertNotNull(totalsMap.getSectorLong());
        assertNotNull(totalsMap.getSums());
    }

    @Test
    public void testGetIndustryToPoliticianContributions() {

        List<IndustryPoliticianContributionTotals> totalsList =
                industryContributionHadoopRepo.getIndustryToPoliticianContributions("K01");

        assertNotNull(totalsList);

        IndustryPoliticianContributionTotals totals = totalsList.get(0);

        assertEquals("K01", totals.getIndustryId());

        assertNotNull(totals.getBioguideId());
        assertNotNull(totals.getParty());
        assertNotNull(totals.getReligion());
        assertNotNull(totals.getIndustryName());
        assertNotNull(totals.getSector());
        assertNotNull(totals.getSectorLong());
        assertNotNull(totals.getContributionCount());
        assertNotNull(totals.getContributionSum());

        assertNull(totals.getCongress());
        assertNull(totals.getEndTimestamp());
        assertNull(totals.getBeginTimestamp());
    }

    @Test
    public void testGetIndustryCategoryToPoliticianContributions() {

        List<IndustryPoliticianContributionTotals> totalsList =
                industryContributionHadoopRepo.getIndustryCategoryToPoliticianContributions("K1000");

        assertNotNull(totalsList);

        IndustryPoliticianContributionTotals totals = totalsList.get(1);

        assertEquals("K1000", totals.getCategoryId());

        assertNotNull(totals.getBioguideId());
        assertNotNull(totals.getParty());
        assertNotNull(totals.getReligion());
        assertNotNull(totals.getIndustryName());
        assertNotNull(totals.getCategoryName());
        assertNotNull(totals.getSector());
        assertNotNull(totals.getSectorLong());
        assertNotNull(totals.getContributionCount());
        assertNotNull(totals.getContributionSum());

        assertNull(totals.getCongress());
        assertNull(totals.getEndTimestamp());
        assertNull(totals.getBeginTimestamp());
    }

    @Autowired
    public void setIndustryContributionHadoopRepo(IndustryContributionHadoopRepo industryContributionHadoopRepo) {
        this.industryContributionHadoopRepo = industryContributionHadoopRepo;
    }
}
