package com.poliana.core.pacFinance;

import com.poliana.config.ApplicationConfig;
import com.poliana.core.pacFinance.entities.PacContributionTotalsMap;
import com.poliana.core.pacFinance.repositories.PacContributionHadoopRepo;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.AnnotationConfigContextLoader;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

/**
 * @author David Gilmore
 * @date 1/26/14
 */
@Profile("integration_test")
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes=ApplicationConfig.class, loader=AnnotationConfigContextLoader.class)
public class PacContributionHadoopRepoTest {

    private PacContributionHadoopRepo pacContributionHadoopRepo;

    @Test
    public void testGetPacPoliticianContributionTotals__ByCongress() throws Exception {

        PacContributionTotalsMap totalsMap =
                pacContributionHadoopRepo.getPacContributionTotalsMap("C00097196", 110);

        assertNotNull(totalsMap);

        assertEquals("C00097196", totalsMap.getPacId());

        assertEquals(new Integer(110), totalsMap.getCongress());

        assertNotNull(totalsMap.getPacName());
        assertNotNull(totalsMap.getSums());
    }

    @Test
    public void testGetPacPoliticianContributionTotals__ByChamberAndCongress() throws Exception {

        PacContributionTotalsMap totalsMap =
                pacContributionHadoopRepo.getPacContributionTotalsMap("C00097196", "s", 110);

        assertNotNull(totalsMap);

        assertEquals("C00097196", totalsMap.getPacId());

        assertEquals(new Integer(110), totalsMap.getCongress());

        assertNotNull(totalsMap.getChamber());
        assertNotNull(totalsMap.getPacName());
        assertNotNull(totalsMap.getSums());
    }

    @Test
    public void testGetPacContributionTotalsMap__ByTimeRange() throws Exception {

        PacContributionTotalsMap totalsMap =
                pacContributionHadoopRepo.getPacContributionTotalsMap("C00097196", 1233446415, 1296518415);

        assertNotNull(totalsMap);

        assertEquals("C00097196", totalsMap.getPacId());

        assertNotNull(totalsMap.getPacName());
        assertNotNull(totalsMap.getSums());
        assertEquals(new Long(1233446415), totalsMap.getBeginTimestamp());
        assertEquals(new Long(1296518415), totalsMap.getEndTimestamp());
    }

    @Test
    public void testGetPacContributionTotalsMap__ByChamberAndTimeRange() throws Exception {

        PacContributionTotalsMap totalsMap =
                pacContributionHadoopRepo.getPacContributionTotalsMap("C00097196", "s", 1233446415, 1296518415);

        assertNotNull(totalsMap);

        assertEquals("C00097196", totalsMap.getPacId());

        assertNotNull(totalsMap.getPacName());
        assertNotNull(totalsMap.getSums());
        assertEquals(new Long(1233446415), totalsMap.getBeginTimestamp());
        assertEquals(new Long(1296518415), totalsMap.getEndTimestamp());
    }

    @Autowired
    public void setPacContributionHadoopRepo(PacContributionHadoopRepo pacContributionHadoopRepo) {
        this.pacContributionHadoopRepo = pacContributionHadoopRepo;
    }
}
