package com.poliana.core.legislation.services;

import com.poliana.config.StandaloneConfig;
import com.poliana.core.entities.entities.Legislator;
import com.poliana.core.entities.repositories.EntitiesMongoRepo;
import com.poliana.core.legislation.entities.Sponsorship;
import com.poliana.core.legislation.repositories.BillHadoopRepo;
import org.apache.commons.math3.linear.MatrixUtils;
import org.apache.commons.math3.linear.SingularValueDecomposition;
import org.bson.types.ObjectId;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.AnnotationConfigContextLoader;

import java.util.*;

import static org.mockito.Matchers.*;
import static org.mockito.Mockito.mock;
import static org.powermock.api.mockito.PowerMockito.when;
import static org.springframework.test.util.ReflectionTestUtils.setField;

/**
 * @author David Gilmore
 * @date 12/2/13
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes=StandaloneConfig.class, loader=AnnotationConfigContextLoader.class)
public class IdeologyAnalysisTest {

    @Autowired
    private IdeologyAnalysis ideologyAnalysis;

    private List<Legislator> legislatorsList;
    private HashMap<String,Integer> legislatorIndices;
    private List<Sponsorship> sponsorships;
    private HashMap<String,String> bioguideMap;
    private double[][] sponsorshipMatrix;

    @Before
    public void setUp() {
        legislatorsList = legislatorsMockList(100);
        sponsorships = sponsorsMock(legislatorsList);
        bioguideMap = bioguideMapMock(legislatorsList);
        legislatorIndices = legislatorIndexMapMock(legislatorsList, "s");
        sponsorshipMatrix = MatrixUtils.createRealIdentityMatrix(10).getData();
    }

    @Test
    public void testGetSponsorshipMatrix() throws Exception {
        Iterator<Legislator> legislatorIterator = legislatorsList.iterator();

        EntitiesMongoRepo entitiesMongoRepo = mock(EntitiesMongoRepo.class);
        BillHadoopRepo billHadoopRepo = mock(BillHadoopRepo.class);

        ideologyAnalysis = mock(IdeologyAnalysis.class);

        when(entitiesMongoRepo.getLegislators(anyString(), anyInt(), anyInt())).thenReturn(legislatorIterator);
        when(billHadoopRepo.getSponsorships(anyString(),anyInt())).thenReturn(sponsorsMock(legislatorsList));

        when(ideologyAnalysis.getIdeologyMatrix("chamber",1)).thenCallRealMethod();
        when(ideologyAnalysis.getIdeologyMatrix(anyString(),anyInt(),anyInt())).thenCallRealMethod();
//        when(ideologyAnalysis.constructMatrix(anyList(),any(HashMap.class),any(HashMap.class)))
//                .thenReturn(sponsorshipMatrix);
        when(ideologyAnalysis.svdAnalysisCommons(sponsorshipMatrix)).thenCallRealMethod();
//        when(ideologyAnalysis.legislatorMap(any(HashMap.class),anyInt())).thenReturn(bioguideMap);
        when(ideologyAnalysis.legislatorIndices(anyList(),anyInt())).thenReturn(legislatorIndices);

        setField(ideologyAnalysis, "entitiesMongoRepo", entitiesMongoRepo, EntitiesMongoRepo.class);
        setField(ideologyAnalysis, "billHadoopRepo", billHadoopRepo,BillHadoopRepo.class);

        ideologyAnalysis.getIdeologyMatrix("chamber", 1);

    }

    @Test
    public void testContructMatrix() {
//        double[][] sponsorshipMatrix = ideologyAnalysis.constructMatrix(sponsorships,legislatorIndices,bioguideMap);
        assert (sponsorshipMatrix.length == legislatorsList.size());
        assert (sponsorshipMatrix[0].length == legislatorsList.size());

        SingularValueDecomposition factorized = ideologyAnalysis.svdAnalysisCommons(sponsorshipMatrix);
        double[][] s = factorized.getS().getData();
        double[][] u = factorized.getU().getData();
        double[][] ut = factorized.getUT().getData();
        double[][] v = factorized.getV().getData();
        double[][] vt = factorized.getVT().getData();
        assert (factorized != null);
    }

    @Test
    public void testGetLegislatorIndexMap() {
        HashMap<String,Integer> indexMap = ideologyAnalysis.getLegislatorIndexMap(legislatorsList);
        assert ( indexMap.size() == legislatorsList.size());
    }

    @Test
    public void testGetIdeologyScore() {

    }

/**********************************************************************************************************************/

/**********************************************************************************************************************/

    private List<Legislator> legislatorsMockList(int numLegislators) {
        List<Legislator> legislators = new ArrayList<>(numLegislators);

        int order = 0;
        String bioguideId = "";
        for(int i=0;i<numLegislators;i++) {
            Legislator legislator = new Legislator();
            legislator.setId(new ObjectId().toString());
            order = (i/10 == order) ? order : order++;
            bioguideId = String.format("A%0" + (6-order) + "d", i);
            legislator.setBioguideId(bioguideId);
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
            sponsorship.setSponsor(legislators.get(i).getBioguideId());
            sponsorship.setCosponsor(legislators.get((i+offset)%dims).getBioguideId());
            sponsorship.setCount(rand.nextInt(50));

            sponsorships.add(sponsorship);
        }

        return sponsorships;
    }

    private HashMap<String,String> bioguideMapMock(List<Legislator> legislators) {
        HashMap<String,String> legislatorMap = new HashMap<>(500);

        for (Legislator legislator: legislators) {
            legislatorMap.put(legislator.getBioguideId(), legislator.getId());
        }

        return legislatorMap;
    }

    public HashMap<String,Integer> legislatorIndexMapMock(List<Legislator> legislators, String chamber) {
        int mapSize;
        if (chamber.contains("h"))
            mapSize = 450;
        else
            mapSize = 115;

        HashMap<String,Integer> legislatorMap = new HashMap<>(mapSize);
        Integer index = new Integer(0);
        for (Legislator legislator: legislators) {
            legislatorMap.put(legislator.getId(),index.intValue());
            index++;
        }
        return legislatorMap;
    }
}
