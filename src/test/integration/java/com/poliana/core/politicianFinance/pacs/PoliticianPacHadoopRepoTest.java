package com.poliana.core.politicianFinance.pacs;

import com.poliana.config.ApplicationConfig;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.AnnotationConfigContextLoader;

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
    public void testGetPacToPoliticianContributions() {

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

    @Autowired
    public void setPoliticianPacHadoopRepo(PoliticianPacHadoopRepo politicianPacHadoopRepo) {
        this.politicianPacHadoopRepo = politicianPacHadoopRepo;
    }
}
