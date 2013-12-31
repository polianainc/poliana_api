package com.poliana.core.ideology;

import com.poliana.core.common.util.TimeUtils;
import com.poliana.core.legislators.Legislator;
import com.poliana.core.legislators.LegislatorService;
import com.poliana.core.sponsorship.SponsorshipMatrix;
import com.poliana.core.sponsorship.SponsorshipService;
import org.apache.commons.math3.linear.SingularValueDecomposition;
import org.apache.commons.math3.linear.BlockRealMatrix;
import org.apache.commons.math3.linear.RealMatrix;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * Ideology Analysis uses matrix decomposition to cluster legislators together based on their sponsorship actions.
 *
 * @author David Gilmore
 * @date 11/28/13
 */
@Service
public class IdeologyService {

    @Autowired
    private IdeologyRepo ideologyRepo;

    @Autowired
    private LegislatorService legislatorService;

    @Autowired
    private SponsorshipService sponsorshipService;

    private static final Logger logger = Logger.getLogger(IdeologyService.class);


    /**
     * Return an ideology score for a legislator during a specified congress. If no score is found, return -1.
     * @param bioguideId
     * @param congress
     * @return
     */
    public double getIdeologyScore(String bioguideId, int congress) {

        int[] timestamps = TimeUtils.yearTimestamps(congress);
        Legislator legislator = legislatorService.legislatorByIdTimestamp(bioguideId, timestamps[1]);

        if (legislator != null) {
            String termType = legislator.getTermType().substring(0,2);
            IdeologyMatrix ideologyMatrix = getIdeologyMatrix(termType, congress);
            double score = -1;
            for (LegislatorIdeology ideology : ideologyMatrix.getIdeologies())
                score = bioguideId.equalsIgnoreCase(ideology.getBioguideId()) ? ideology.getIdeology() : score;
            return score;
        }
        return -1;
    }

    /**
     * Return an ideology score for a legislator during a specified time range. If no score is found, return -1.
     * @param bioguideId
     * @param beginTimestamp
     * @param endTimestamp
     * @return
     */
    public double ideologyScore(String bioguideId, int beginTimestamp, int endTimestamp) {

        Legislator legislator = legislatorService.legislatorByIdTimestamp(bioguideId, beginTimestamp);

        if (legislator != null) {
            String termType = legislator.getTermType().substring(0,2);
            IdeologyMatrix ideologyMatrix = getIdeologyMatrix(termType, beginTimestamp, endTimestamp);
            double score = -1;
            for (LegislatorIdeology ideology : ideologyMatrix.getIdeologies())
                score = bioguideId.equalsIgnoreCase(ideology.getBioguideId()) ? ideology.getIdeology() : score;
            return score;
        }
        return -1;
    }

    /**
     * For a given congressional cycle and chamber, return a collective analysis of sponsorship trends using
     * Singular Value Decomposition.
     * @param chamber
     * @param congress
     * @return
     */
    public IdeologyMatrix getIdeologyMatrix(String chamber, int congress) {

        SponsorshipMatrix sponsorshipMatrix = sponsorshipService.getSponsorshipMatrix(chamber, congress);
        SingularValueDecomposition svd = svdAnalysisCommons(sponsorshipMatrix.getMatrix());
        IdeologyMatrix ideologyMatrix = new IdeologyMatrix();

        //Use the normalized second dimension of the SVD output as ideology score
        double[] ideologyValues = normalizeEigenvector(svd.getVT().getRow(1));
        ideologyMatrix.setIdeologyValues(ideologyValues);

        ideologyMatrix.setChamber(chamber);
        ideologyMatrix.setCongress(congress);

        List<LegislatorIdeology> ideologies =
                getLegislatorIdeologies(sponsorshipMatrix, ideologyValues, chamber, congress);

        ideologyMatrix.setIdeologies(ideologies);

        return ideologyMatrix;
    }

    /**
     * For a given time range and chamber, return a collective analysis of sponsorship trends using
     * Singular Value Decomposition.
     * @param chamber
     * @param beginTimestamp
     * @param endTimestamp
     * @return
     */
    public IdeologyMatrix getIdeologyMatrix(String chamber, int beginTimestamp, int endTimestamp) {

        SponsorshipMatrix sponsorshipMatrix = sponsorshipService.getSponsorshipMatrix(chamber, beginTimestamp, endTimestamp);
        SingularValueDecomposition svd = svdAnalysisCommons(sponsorshipMatrix.getMatrix());
        IdeologyMatrix ideologyMatrix = new IdeologyMatrix();

        //Use the normalized second dimension of the SVD output as ideology score
        double[] ideologyValues = normalizeEigenvector(svd.getVT().getRow(1));
        ideologyMatrix.setIdeologyValues(ideologyValues);

        ideologyMatrix.setChamber(chamber);
        ideologyMatrix.setBeginTimestamp(beginTimestamp);
        ideologyMatrix.setEndTimestamp(endTimestamp);

        List<LegislatorIdeology> ideologies =
                getLegislatorIdeologies(sponsorshipMatrix, ideologyValues, chamber, beginTimestamp, endTimestamp);

        ideologyMatrix.setIdeologies(ideologies);

        return ideologyMatrix;
    }

    /**
     * Perform a singular value decomposition of a sponsorship matrix.
     * @param sponsorshipMatrix
     * @return
     */
    public SingularValueDecomposition svdAnalysisCommons(double[][] sponsorshipMatrix) {

        RealMatrix m = new BlockRealMatrix(sponsorshipMatrix);
        SingularValueDecomposition svd = new SingularValueDecomposition(m);
        return svd;
    }

    /**
     * Take an eigen vector and return the same values normalized between 0 and 100.
     * @param e
     * @return
     */
    public double[] normalizeEigenvector(double[] e) {

        //iterate through eigenvalues to get Emax and Emin needed to calculate the normalized values.
        double emax = 0;
        double emin = 0;
        for (int ei = 0; ei<e.length; ei++) {
            emax = e[ei] > emax ? e[ei] : emax;
            emin = e[ei] < emin ? e[ei] : emin;
        }

        for (int ei = 0; ei<e.length; ei++) {
            e[ei] = ((e[ei] - emin) / (emax - emin)) * 100;
        }
        return e;
    }

    /**
     * Map Legislator objects to LegislatorIdeology objects. If the analysis is for a congressional session, pass a
     * congress for the timerange. Otherwise pass in the begin timestamp first and the end timestamp second.
     * @param ideologyValues
     * @param sponsorshipMatrix
     * @return
     */
    public List<LegislatorIdeology> getLegislatorIdeologies(SponsorshipMatrix sponsorshipMatrix, double[] ideologyValues,
                                                            String chamber, int... timerange) {

        List<LegislatorIdeology> legislatorIdeologies = new LinkedList<>();
        for (Legislator legislator : sponsorshipMatrix.getLegislatorList()) {
            LegislatorIdeology ideology = new LegislatorIdeology();
            ideology.setBioguideId(legislator.getBioguideId());

            if (timerange.length == 1)
                ideology.setCongress(timerange[0]);
            else {
                ideology.setBeginTimestamp(timerange[0]);
                ideology.setEndTimestamp(timerange[1]);
            }

            ideology.setChamber(chamber);
            ideology.setMetric("sponsorship");
            ideology.setIdeology(ideologyValues[legislator.getIndex()]);
            legislatorIdeologies.add(ideology);
        }
        return legislatorIdeologies;
    }
}
