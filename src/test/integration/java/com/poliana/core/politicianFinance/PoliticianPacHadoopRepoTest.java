package com.poliana.core.politicianFinance;

import com.poliana.config.ApplicationConfig;
import com.poliana.core.politicianFinance.pacs.PoliticianPacContributionsTotals;
import com.poliana.core.politicianFinance.pacs.PoliticianPacHadoopRepo;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.AnnotationConfigContextLoader;

import java.util.HashMap;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

/**
 * @author David Gilmore
 * @date 1/26/14
 */
@Profile("integration_test")
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes=ApplicationConfig.class, loader=AnnotationConfigContextLoader.class)
public class PoliticianPacHadoopRepoTest {

    private PoliticianPacHadoopRepo politicianPacHadoopRepo;

    @Test
    public void testGetPacToPoliticianContributions() throws Exception {

        List<PoliticianPacContributionsTotals> totals =
                politicianPacHadoopRepo.getPacToPoliticianContributions("O000167", 1169868209, 1269868209);

        assertNotNull(totals);

        PoliticianPacContributionsTotals total = totals.get(0);

        assertEquals("O000167", total.getBioguideId());
        assertEquals(new Long(1169868209), total.getBeginTimestamp());
        assertEquals(new Long(1269868209), total.getEndTimestamp());

        assertNotNull(total.getFirstName());
        assertNotNull(total.getLastName());
        assertNotNull(total.getParty());
        assertNotNull(total.getReligion());
        assertNotNull(total.getPacId());
        assertNotNull(total.getPacName());
        assertNotNull(total.getContributionCount());
        assertNotNull(total.getContributionSum());
    }

    @Test
    public void testGetPacToPoliticianTotalsPerCongress_AllTime() throws Exception {

        HashMap<Integer, List<PoliticianPacContributionsTotals>> totalsMap =
                politicianPacHadoopRepo.getPacToPoliticianTotalsPerCongress("O000167");

        assertNotNull(totalsMap);

        assertNotNull(totalsMap.get(108));
        assertNotNull(totalsMap.get(109));
        List<PoliticianPacContributionsTotals> contributions110 = totalsMap.get(110);

        PoliticianPacContributionsTotals contributionFrom110 = contributions110.get(0);

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
    public void testGetPacToPoliticianTotalsPerCongress_ByTimeRange() throws Exception {

        HashMap<Integer, List<PoliticianPacContributionsTotals>> totalsMap =
                politicianPacHadoopRepo.getPacToPoliticianTotalsPerCongress("O000167", 1075429263, 1201727289);

        assertNotNull(totalsMap);

        assertNotNull(totalsMap.get(108));
        assertNotNull(totalsMap.get(109));
        List<PoliticianPacContributionsTotals> contributions110 = totalsMap.get(110);

        PoliticianPacContributionsTotals contributionFrom110 = contributions110.get(0);

        assertEquals("O000167", contributionFrom110.getBioguideId());
        assertEquals("Barack", contributionFrom110.getFirstName());
        assertEquals("Obama", contributionFrom110.getLastName());

        assertEquals("Democrat", contributionFrom110.getParty());
        assertEquals("United Church of Christ", contributionFrom110.getReligion());

        assertEquals(new Integer(110), contributionFrom110.getCongress());

        assertNotNull(contributionFrom110.getContributionCount());
        assertNotNull(contributionFrom110.getContributionSum());
    }

    @Autowired
    public void setPoliticianPacHadoopRepo(PoliticianPacHadoopRepo politicianPacHadoopRepo) {
        this.politicianPacHadoopRepo = politicianPacHadoopRepo;
    }
}
