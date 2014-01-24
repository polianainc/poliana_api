package com.poliana.core.ideology;

import com.poliana.core.time.CongressTimestamps;
import com.poliana.core.time.TimeService;
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

    private IdeologyRepo ideologyRepo;
    private LegislatorService legislatorService;
    private SponsorshipService sponsorshipService;
    private TimeService timeService;

    private static final Logger logger = Logger.getLogger(IdeologyService.class);


    /**
     * Get an getAllPacContributions score for a legislator during a specified congress. If no score is found, return -1.
     * @param bioguideId
     * @param congress
     * @return
     */
    public double getIdeologyScore(String bioguideId, int congress) {

        LegislatorIdeology ideology = getLegislatorIdeology(bioguideId, congress);

        if (ideology != null)
            return ideology.getIdeology();

        return -1;
    }

    /**
     * Get a LegislatorIdeology object
     * @param bioguideId
     * @param congress
     * @return
     */
    public LegislatorIdeology getLegislatorIdeology(String bioguideId, int congress) {

        //Look in MongoDB cache for the LegislatorIdeology object we need
        LegislatorIdeology ret = ideologyRepo.getLegislatorIdeology(bioguideId, congress);

        //If the object was cached, return it
        if (ret!= null)
            return ret;

        //If no return, get an IdeologyMatrix and find the correct LegislatorIdeology in it.
        //We don't know what term it is, query for the correct term using the average of the congress timestamps.
        CongressTimestamps timestamps = timeService.getCongressTimestamps(congress);
        int avg = (int) ((timestamps.getBegin() + timestamps.getEnd()) / 2);

        Legislator legislator = legislatorService.getCachedLegislatorByIdTimestamp(bioguideId, avg);

        //Avoid null pointer exception
        if (legislator != null) {

            //Get the correct chamber from the legislator term
            String termType = legislator.getTermType().substring(0, 1);

            //Query for the getAllPacContributions matrix
            IdeologyMatrix ideologyMatrix = getIdeologyMatrix(termType, congress);

            if (ideologyMatrix != null) {
                //Iterate through the returned objects getAllPacContributions list. If the bioguide IDs match, keep it as the return.
                for (LegislatorIdeology ideology : ideologyMatrix.getIdeologies()) {
                    ret = bioguideId.equalsIgnoreCase(ideology.getBioguideId()) ? ideology : null;
                }
            }

        }

        return ret;
    }

    /**
     * For a given congressional cycle and chamber, return a collective analysis of sponsorship trends using
     * Singular Value Decomposition.
     * @param chamber
     * @param congress
     * @return
     */
    public IdeologyMatrix getIdeologyMatrix(String chamber, int congress) {

        //Consult MongoDB for the getAllPacContributions matrix first
        IdeologyMatrix ideologyMatrix = ideologyRepo.getIdeologyMatrix(chamber, congress);

        if (ideologyMatrix != null)
            return ideologyMatrix;

        SponsorshipMatrix sponsorshipMatrix = sponsorshipService.getSponsorshipMatrix(chamber, congress);
        SingularValueDecomposition svd = svdAnalysisCommons(sponsorshipMatrix.getMatrix());
        ideologyMatrix = new IdeologyMatrix();

        //Use the normalized second dimension of the SVD output as getAllPacContributions score
        double[] ideologyValues = normalizeEigenvector(svd.getVT().getRow(1));
        ideologyMatrix.setIdeologyValues(ideologyValues);

        ideologyMatrix.setChamber(chamber);
        ideologyMatrix.setCongress(congress);

        List<LegislatorIdeology> ideologies =
                getLegislatorIdeologies(sponsorshipMatrix, ideologyValues, chamber, congress);

        ideologyMatrix.setIdeologies(ideologies);

        //Cache results to MongoDB
        ideologyRepo.saveIdeologyMatrix(sortIdeologyScores(ideologyMatrix)); //Sort scores first
        ideologyRepo.saveLegislatorIdeologies(ideologyMatrix.getIdeologies());

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

        //Use the normalized second dimension of the SVD output as getAllPacContributions score
        double[] ideologyValues = normalizeEigenvector(svd.getVT().getRow(1));
        ideologyMatrix.setIdeologyValues(ideologyValues);

        ideologyMatrix.setChamber(chamber);
        ideologyMatrix.setBeginTimestamp(beginTimestamp);
        ideologyMatrix.setEndTimestamp(endTimestamp);

        List<LegislatorIdeology> ideologies =
                getLegislatorIdeologies(sponsorshipMatrix, ideologyValues, chamber, beginTimestamp, endTimestamp);

        ideologyMatrix.setIdeologies(ideologies);

        //Cache results to MongoDB
        ideologyRepo.saveIdeologyMatrix(sortIdeologyScores(ideologyMatrix));
        ideologyRepo.saveLegislatorIdeologies(ideologyMatrix.getIdeologies());

        return ideologyMatrix;
    }

    private IdeologyMatrix sortIdeologyScores(IdeologyMatrix ideologyMatrix) {

        class LegislatorIdeologyComparator implements Comparator<LegislatorIdeology> {
            public int compare(LegislatorIdeology leg1, LegislatorIdeology leg2) {
                return (int) (leg1.getIdeology() - leg2.getIdeology());
            }
        }

        Collections.sort(ideologyMatrix.getIdeologies(), new LegislatorIdeologyComparator());

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
            e[ei] = (( (e[ei] * -1 ) - emin) / (emax - emin)) * 100;
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
            ideology.setName(legislator.getFirstName() + " " + legislator.getLastName());
            ideology.setParty(legislator.getParty());
            ideology.setReligion(legislator.getReligion());
            ideology.setIndex(legislator.getIndex());
            ideology.setMetric("sponsorship");
            ideology.setIdeology(ideologyValues[legislator.getIndex()]);
            legislatorIdeologies.add(ideology);
        }

        return legislatorIdeologies;
    }

    @Autowired
    public void setIdeologyRepo(IdeologyRepo ideologyRepo) {
        this.ideologyRepo = ideologyRepo;
    }

    @Autowired
    public void setLegislatorService(LegislatorService legislatorService) {
        this.legislatorService = legislatorService;
    }

    @Autowired
    public void setSponsorshipService(SponsorshipService sponsorshipService) {
        this.sponsorshipService = sponsorshipService;
    }

    @Autowired
    public void setTimeService(TimeService timeService) {
        this.timeService = timeService;
    }
}
