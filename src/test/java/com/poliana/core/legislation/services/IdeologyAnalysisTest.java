package com.poliana.core.legislation.services;

import com.poliana.core.entities.entities.Legislator;
import com.poliana.core.entities.repositories.EntitiesMongoRepo;
import com.poliana.core.legislation.entities.Sponsorship;
import com.poliana.core.legislation.repositories.BillHadoopRepo;
import org.bson.types.ObjectId;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import java.util.*;

import static org.mockito.Matchers.*;
import static org.mockito.Mockito.mock;
import static org.powermock.api.mockito.PowerMockito.when;
import static org.springframework.test.util.ReflectionTestUtils.setField;

/**
 * @author David Gilmore
 * @date 12/2/13
 */
@RunWith(PowerMockRunner.class)
@PrepareForTest(IdeologyAnalysis.class)
public class IdeologyAnalysisTest {

    IdeologyAnalysis ideologyAnalysis;

    @Before
    public void setUp() {
        ideologyAnalysis = PowerMockito.spy(new IdeologyAnalysis());
    }

    @Test
    public void testGetSponsorshipMatrix() throws Exception {
        List<Legislator> legislators = legislatorsMock(100);
        Iterator<Legislator> legislatorIterator = legislators.iterator();
        List<Sponsorship> sponsorships = sponsorsMock(legislators);

        EntitiesMongoRepo entitiesMongoRepo = mock(EntitiesMongoRepo.class);
        BillHadoopRepo billHadoopRepo = mock(BillHadoopRepo.class);

        when(entitiesMongoRepo.getLegislators(anyString(),anyInt(),anyInt())).thenReturn(legislatorIterator);
        when(billHadoopRepo.getSponsorships(anyString(),anyInt())).thenReturn(sponsorsMock(legislators));
        when(ideologyAnalysis,"getLegislatorIndexMap",anyListOf(Legislator.class).iterator(),anyString())
                .thenReturn(new HashMap<String,Integer>());

        setField(ideologyAnalysis, "entitiesMongoRepo", entitiesMongoRepo, EntitiesMongoRepo.class);
        setField(ideologyAnalysis, "billHadoopRepo", billHadoopRepo,BillHadoopRepo.class);

        ideologyAnalysis.getSponsorshipMatrix("chamber", 1);
    }

    private List<Legislator> legislatorsMock(int numLegislators) {
        List<Legislator> legislators = new ArrayList<>(numLegislators);

        for(int i=0;i<numLegislators;i++) {
            Legislator legislator = new Legislator();
            legislator.setId(new ObjectId().toString());
            legislators.add(legislator);
        }

        return legislators;
    }

    private List<Sponsorship> sponsorsMock(List<Legislator> legislators) {
        int dims = legislators.size();
        int size = dims*(dims/2);
        Random rand = new Random();

        List<Sponsorship> sponsorships = new ArrayList<>(size);

        int i = 0;
        int offset = 1;
        for(int j=0;j<size;j++) {

            if (i >= legislators.size()-1) {
                i=0;
                offset++;
            }
            else i++;

            Sponsorship sponsorship = new Sponsorship();
            sponsorship.setSponsor(legislators.get(i).getId());
            sponsorship.setCosponsor(legislators.get((i+offset)%dims).getId());
            sponsorship.setCount(rand.nextInt(50));

            sponsorships.add(sponsorship);
        }

        return sponsorships;
    }
}
