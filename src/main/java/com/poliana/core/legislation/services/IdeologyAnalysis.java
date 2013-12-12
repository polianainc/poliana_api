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
        int[] timeRanges = TimeUtils.termRanges(congress);
        IdeologyMatrix ideologyMatrix = getIdeologyMatrix(chamber, timeRanges[0], timeRanges[1]);
        return ideologyMatrix;
    }

    /**
     *
     * @param chamber
     * @param beginDate
     * @param endDate
     * @return
     */
    public IdeologyMatrix getIdeologyMatrix(String chamber, int beginDate, int endDate) {
        IdeologyMatrix ideologyMatrix = new IdeologyMatrix();

        List<Sponsorship> sponsorships =
                billHadoopRepo.getSponsorships(chamber,TimeUtils.timestampToCongress(beginDate));

        HashMap<String,Integer> legislatorIndices = legislatorIndices(sponsorships,beginDate);
        HashMap<String,String> legislatorMap = legislatorMap(legislatorIndices,beginDate);

        double[][] matrix = constructMatrix(sponsorships,legislatorIndices,legislatorMap);

        ideologyMatrix.setChamber(chamber);
        ideologyMatrix.setBeginDate(beginDate);
        ideologyMatrix.setEndDate(endDate);
        ideologyMatrix.setSponsorshipMatrix(matrix);

        SingularValueDecomposition svd = svdAnalysisCommons(matrix);
        ideologyMatrix.setSvd(svd);
        ideologyMatrix.setIndices(legislatorIndices);
        ideologyMatrix.setU(svd.getU().getData());

        return ideologyMatrix;
    }

    /**
     *
     * @param sponsorships
     * @param legislatorIndices
     * @param legislatorMap
     * @return
     */
    public double[][] constructMatrix(List<Sponsorship> sponsorships, HashMap<String,Integer> legislatorIndices,
                                       HashMap<String,String> legislatorMap) {
        int size = legislatorIndices.size();
        double[][] matrix = MatrixUtils.createRealIdentityMatrix(size).getData();

        int i = 0;
        int j = 0;
        for (Sponsorship sponsorship: sponsorships) {
            try {
                i = legislatorIndices.get(sponsorship.getSponsor());
                j = legislatorIndices.get(sponsorship.getCosponsor());
                matrix[i][j] = sponsorship.getCount();
            }
            catch (Exception e ) {}
        }
        return matrix;
    }

    /**
     *
     * @param bioduideId
     * @param chamber
     * @param beginTimestamp
     * @param endTimestamp
     * @return
     */
    public double ideologyScore(String bioduideId, String chamber, int beginTimestamp, int endTimestamp) {
        IdeologyMatrix ideologyMatrix = getIdeologyMatrix(chamber, beginTimestamp, endTimestamp);
        int index = ideologyMatrix.getIndices().get(bioduideId);
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

    protected HashMap<String,String> legislatorMap(HashMap<String,Integer> legislatorIndices, int timestamp) {
        HashMap<String,String> legislatorMap = new HashMap<>(500);
        Iterator<String> bioguides = legislatorIndices.keySet().iterator();
        while (bioguides.hasNext()) {
            String bioguide = bioguides.next();
            Legislator legislator = legislatorService.legislatorByIdTimestamp(bioguide,timestamp);
            legislatorMap.put(bioguide,legislator.getId());
        }
        return legislatorMap;
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
