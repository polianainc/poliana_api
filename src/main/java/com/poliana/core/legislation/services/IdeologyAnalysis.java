package com.poliana.core.legislation.services;

import com.poliana.core.common.db.MahoutUtil;
import com.poliana.core.common.util.TimeUtils;
import com.poliana.core.entities.entities.Legislator;
import com.poliana.core.entities.repositories.EntitiesMongoRepo;
import com.poliana.core.entities.services.LegislatorService;
import com.poliana.core.legislation.entities.Sponsorship;
import com.poliana.core.legislation.models.IdeologyMatrix;
import com.poliana.core.legislation.repositories.BillHadoopRepo;
import org.apache.commons.math3.linear.SingularValueDecomposition;
import org.apache.commons.math3.linear.BlockRealMatrix;
import org.apache.commons.math3.linear.RealMatrix;
import org.apache.commons.math3.linear.MatrixUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * @author David Gilmore
 * @date 11/28/13
 */
@Service
public class IdeologyAnalysis {

    @Autowired
    private MahoutUtil mahoutUtil;
    @Autowired
    private BillHadoopRepo billHadoopRepo;
    @Autowired
    private EntitiesMongoRepo entitiesMongoRepo;
    @Autowired
    private LegislatorService legislatorService;
    @Autowired
    private LegislationService legislationService;

    /**
     *
     *
     * @param chamber
     * @param congress
     * @return
     */
    public IdeologyMatrix getIdeologyMatrix(String chamber, int congress) {

        int[] timeRanges = TimeUtils.yearTimestamps(congress);
        IdeologyMatrix ideologyMatrix = getIdeologyMatrix(chamber, timeRanges[0], timeRanges[1]);
        return ideologyMatrix;
    }

    /**
     *
     * @param chamber
     * @param beginTimestamp
     * @param endTimestamp
     * @return
     */
    public IdeologyMatrix getIdeologyMatrix(String chamber, int beginTimestamp, int endTimestamp) {

        IdeologyMatrix ideologyMatrix = getSponsorshipMatrix(chamber,beginTimestamp,endTimestamp);

        SingularValueDecomposition svd = svdAnalysisCommons(ideologyMatrix.getSponsorshipMatrix());
        ideologyMatrix.setSvd(svd);
        ideologyMatrix.setU(svd.getU().getData());
        ideologyMatrix.setVt(svd.getVT().getData());

        return ideologyMatrix;
    }

    /**
     *
     * @param chamber
     * @param beginTimestamp
     * @param endTimestamp
     * @return
     */
    public IdeologyMatrix getSponsorshipMatrix(String chamber, int beginTimestamp, int endTimestamp) {

        IdeologyMatrix ideologyMatrix = new IdeologyMatrix();

        List<Sponsorship> sponsorships =
                billHadoopRepo.getSponsorships(chamber,TimeUtils.timestampToCongress(beginTimestamp));
        Iterator<Legislator> legislatorIterator = entitiesMongoRepo.getLegislators(chamber,beginTimestamp,endTimestamp);

        SponsorshipData sponsorshipData = getUniqueLegislators(sponsorships,legislatorIterator,
                beginTimestamp,endTimestamp);
        HashMap<String,Legislator> legislatorHashMap = sponsorshipData.legislatorHashMap;

        sponsorshipData = constructMatrix(sponsorships,sponsorshipData);

        ideologyMatrix.setChamber(chamber);
        ideologyMatrix.setBeginDate(beginTimestamp);
        ideologyMatrix.setEndDate(endTimestamp);
        ideologyMatrix.setSponsorshipMatrix(sponsorshipData.matrix);
        ideologyMatrix.setIdToIndex(sponsorshipData.indices);
        ideologyMatrix.setLegislators(sponsorshipData.legislatorList);

        return ideologyMatrix;
    }

    /**
     *
     * @param sponsorships
     * @param sponsorshipData
     * @return
     */
    public SponsorshipData constructMatrix(List<Sponsorship> sponsorships, SponsorshipData sponsorshipData) {

        HashMap<String,Legislator> legislatorHashMap = sponsorshipData.legislatorHashMap;
        int size = legislatorHashMap.size();

        HashMap<String,Integer> indices = new HashMap<>(size);
        double[][] matrix = MatrixUtils.createRealIdentityMatrix(size).getData();

        int i;
        int j;
        for (Sponsorship sponsorship: sponsorships) {
            try {
                Legislator sponsor = legislatorHashMap.get(sponsorship.getSponsor());
                i = sponsor.getIndex();
                j = legislatorHashMap.get(sponsorship.getCosponsor()).getIndex();
                indices.put(sponsorship.getSponsor(),i);
                matrix[i][j] = sponsorship.getCount();
            }
            catch (Exception e ) {}
        }

        sponsorshipData.matrix = matrix;
        sponsorshipData.indices = indices;

        return sponsorshipData;
    }

    /**
     *
     * @param bioguideId
     * @param chamber
     * @param beginTimestamp
     * @param endTimestamp
     * @return
     */
    public double ideologyScore(String bioguideId, String chamber, int beginTimestamp, int endTimestamp) {

        IdeologyMatrix ideologyMatrix = getIdeologyMatrix(chamber, beginTimestamp, endTimestamp);
        int index = ideologyMatrix.getIdToIndex().get(bioguideId);
        return ideologyMatrix.getU()[1][index];
    }

    /**
     *
     * @param sponsorshipMatrix
     * @return
     */
    public SingularValueDecomposition svdAnalysisCommons(double[][] sponsorshipMatrix) {

        RealMatrix m = new BlockRealMatrix(sponsorshipMatrix);
        SingularValueDecomposition svd = new SingularValueDecomposition(m);
        return svd;
    }

    /**
     *
     * @param legislators
     * @return
     */
    public HashMap<String,Integer> getLegislatorIndexMap(List<Legislator> legislators) {

        return getLegislatorIndexMap(legislators, "h");
    }

    /**
     *
     * @param legislators
     * @param chamber
     * @return
     */
    public HashMap<String,Integer> getLegislatorIndexMap(List<Legislator> legislators, String chamber) {

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

/**********************************************************************************************************************/

/**********************************************************************************************************************/

    protected class SponsorshipData {

        HashMap<String,Integer> indices;
        HashMap<String,Legislator> legislatorHashMap;
        double[][] matrix;
        List<Legislator> legislatorList;
    }

    protected SponsorshipData getUniqueLegislators(List<Sponsorship> sponsorships,
            Iterator<Legislator> legislatorIterator, int beginTimestamp, int endTimestamp) {

        SponsorshipData sponsorshipData = legislatorMap(legislatorIterator);
        HashMap<String,Legislator> legislatorHashMap = sponsorshipData.legislatorHashMap;

        for (Sponsorship sponsorship: sponsorships) {
            updateLegislatorMap(sponsorship.getSponsor(),legislatorHashMap,beginTimestamp,endTimestamp);
            updateLegislatorMap(sponsorship.getCosponsor(),legislatorHashMap,beginTimestamp,endTimestamp);
        }

        return sponsorshipData;
    }

    protected void updateLegislatorMap(String bioguideId,
        HashMap<String,Legislator> legislatorHashMap, int beginTimestamp, int endTimestamp) {

        if (!legislatorHashMap.containsKey(bioguideId)) {
            Legislator legislator = legislatorService.
                    legislatorByIdTimestamp(bioguideId,beginTimestamp);
            if (legislator == null)
                legislator = legislatorService.
                        legislatorByIdTimestamp(bioguideId,endTimestamp);
            if (legislator != null) {
                legislator.setIndex(legislatorHashMap.size()+1);
                legislatorHashMap.put(legislator.getBioguideId(),legislator);
            }
        }
    }


    protected SponsorshipData legislatorMap(Iterator<Legislator> legislatorIterator) {

        SponsorshipData sponsorshipData = new SponsorshipData();
        HashMap<String,Legislator> legislatorMap = new HashMap<>(500);

        int index = 0;
        while (legislatorIterator.hasNext()) {
            Legislator legislator = legislatorIterator.next();
            if(!legislatorMap.containsKey(legislator.getBioguideId())) {
                legislator.setIndex(index++);
                legislatorMap.put(legislator.getBioguideId(),legislator);
            }
        }
        sponsorshipData.legislatorHashMap = legislatorMap;
        sponsorshipData.legislatorList = new ArrayList(legislatorMap.values());

        return sponsorshipData;
    }

    protected HashMap<String,Integer> legislatorIndices(List<Sponsorship> sponsorships, int timeStamp) {

        HashMap<String,Integer> legislatorIndices = new HashMap<>(500);
        int index = 0;
        for (Sponsorship sponsorship: sponsorships) {
            String sponsor = sponsorship.getSponsor();
            String cosponsor = sponsorship.getCosponsor();
            if (!legislatorIndices.containsKey(sponsor))
                legislatorIndices.put(sponsor,index++);
            if (!legislatorIndices.containsKey(cosponsor))
                legislatorIndices.put(cosponsor,index++);
        }
        return legislatorIndices;
    }
}
