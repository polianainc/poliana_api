package com.poliana.core.sponsorship;

import com.poliana.core.time.CongressTimestamps;
import com.poliana.core.time.TimeService;
import com.poliana.core.legislators.Legislator;
import com.poliana.core.legislators.LegislatorRepo;
import com.poliana.core.legislators.LegislatorService;
import org.apache.commons.math3.linear.MatrixUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

/**
 * @author David Gilmore
 * @date 12/30/13
 */
@Service
public class SponsorshipService {

    private SponsorshipRepo sponsorshipRepo;
    private LegislatorRepo legislatorRepo;
    private LegislatorService legislatorService;
    private TimeService timeService;

    private static final Logger logger = Logger.getLogger(SponsorshipService.class);

    /**
     * Return a matrix of sponsorships for a given congressional cycle. The Ith dimension corresponds to bills
     * sponsored, the Jth corresponds to bills cosponsored.
     * @param chamber
     * @param congress
     * @return
     */
    public SponsorshipMatrix getSponsorshipMatrix(String chamber, int congress) {

        //Check MongoDB for the existence of this sponsorship matrix
        SponsorshipMatrix sponsorshipMatrix = sponsorshipRepo.getSponsorshipMatrix(chamber, congress);

        //If there was a matrix cached, return it
        if (sponsorshipMatrix != null)
            return sponsorshipMatrix;

        //Get all sponsorship counts for the given congress
        List<SponsorshipCount> sponsorships =
                sponsorshipRepo.getSponsorshipCounts(chamber, congress);

        //Get beginning and ending timestamps for the given congress to use for a legislator query
        CongressTimestamps timestamps = timeService.congressTimestamps(congress);

        //Get all legislators in the given chamber during the congress given
        Iterator<Legislator> legislatorIterator =
                legislatorRepo.getLegislators(chamber, timestamps.getBegin(), timestamps.getEnd());

        sponsorshipMatrix = getSponsorshipMatrix(chamber, sponsorships, legislatorIterator, timestamps.getBegin(), timestamps.getEnd());

        //Save it so we don't have to go through all of this again
        sponsorshipRepo.saveSponsorshipMatrix(sponsorshipMatrix);

        return sponsorshipMatrix;
    }

    /**
     * Return a matrix of sponsorships for a given time range. The Ith dimension corresponds to bills
     * sponsored, the Jth corresponds to bills cosponsored.
     * @param chamber
     * @param beginTimestamp
     * @param endTimestamp
     * @return
     */
    public SponsorshipMatrix getSponsorshipMatrix(String chamber, int beginTimestamp, int endTimestamp) {

        //TODO: Need to run a hive job to properly select by timestamps in the sponsorship repo
        List<SponsorshipCount> sponsorships =
                sponsorshipRepo.getSponsorshipCounts(chamber, TimeService.timestampToCongress(beginTimestamp));
        Iterator<Legislator> legislatorIterator = legislatorRepo.getLegislators(chamber, beginTimestamp, endTimestamp);

        return getSponsorshipMatrix(chamber, sponsorships, legislatorIterator, beginTimestamp, endTimestamp);
    }

    /**
     * Return a matrix of sponsorships for a given time period and list of legislators.
     * @param chamber
     * @param sponsorships
     * @param legislatorIterator
     * @param beginTimestamp
     * @param endTimestamp
     * @return
     */
    public SponsorshipMatrix getSponsorshipMatrix(String chamber, List<SponsorshipCount> sponsorships,
                                      Iterator<Legislator> legislatorIterator, long beginTimestamp, long endTimestamp) {

        SponsorshipMatrix sponsorshipMatrix = getUniqueLegislators(sponsorships,legislatorIterator,
                beginTimestamp,endTimestamp);

        sponsorshipMatrix = constructMatrix(sponsorships,sponsorshipMatrix);

        sponsorshipMatrix.setChamber(chamber);
        sponsorshipMatrix.setBeginTimestamp(beginTimestamp);
        sponsorshipMatrix.setEndTimestamp(endTimestamp);
        sponsorshipMatrix.setMatrix(sponsorshipMatrix.getMatrix());

        return sponsorshipMatrix;
    }

    /**
     * Using a list of of sponsorhsip counts, construct the matrix that represents the sponsorhsip trends.
     * @param sponsorships
     * @param sponsorshipMatrix
     * @return
     */
    public SponsorshipMatrix constructMatrix(List<SponsorshipCount> sponsorships, SponsorshipMatrix sponsorshipMatrix) {

        HashMap<String, Legislator> legislatorHashMap = sponsorshipMatrix.getLegislatorHashMap();
        int size = legislatorHashMap.size();

        HashMap<String,Integer> indices = new HashMap<>(size);
        double[][] matrix = MatrixUtils.createRealIdentityMatrix(size).getData();

        int i;
        int j;
        for (SponsorshipCount sponsorship: sponsorships) {
            try {
                Legislator sponsor = legislatorHashMap.get(sponsorship.getSponsor());
                i = sponsor.getIndex();
                j = legislatorHashMap.get(sponsorship.getCosponsor()).getIndex();
                indices.put(sponsorship.getSponsor(),i);
                matrix[i][j] = sponsorship.getCount();
            }
            catch (Exception e ) {}
        }

        sponsorshipMatrix.setMatrix(matrix);
        sponsorshipMatrix.setIndices(indices);

        return sponsorshipMatrix;
    }

    /**
     * Get a list of unique legislators returned in a sponsorship matrix.
     * @param sponsorships
     * @param legislatorIterator
     * @param beginTimestamp
     * @param endTimestamp
     * @return
     */
    protected SponsorshipMatrix getUniqueLegislators(List<SponsorshipCount> sponsorships,
                               Iterator<Legislator> legislatorIterator, long beginTimestamp, long endTimestamp) {

        SponsorshipMatrix sponsorshipMatrix = legislatorMap(legislatorIterator);
        HashMap<String, Legislator> legislatorHashMap = sponsorshipMatrix.getLegislatorHashMap();

        for (SponsorshipCount sponsorship: sponsorships) {
            updateLegislatorMap(sponsorship.getSponsor(),legislatorHashMap,beginTimestamp,endTimestamp);
            updateLegislatorMap(sponsorship.getCosponsor(),legislatorHashMap,beginTimestamp,endTimestamp);
        }

        return sponsorshipMatrix;
    }

    /**
     * Use a list of legislators to construct a map of bioguide IDs to legislator term objects.
     * @param legislatorIterator
     * @return
     */
    protected SponsorshipMatrix legislatorMap(Iterator<Legislator> legislatorIterator) {

        SponsorshipMatrix sponsorshipData = new SponsorshipMatrix();
        HashMap<String, Legislator> legislatorMap = new HashMap<>(500);

        int index = 0;
        while (legislatorIterator.hasNext()) {
            Legislator legislator = legislatorIterator.next();
            if(!legislatorMap.containsKey(legislator.getBioguideId())) {
                legislator.setIndex(index++);
                legislatorMap.put(legislator.getBioguideId(),legislator);
            }
        }
        sponsorshipData.setLegislatorHashMap(legislatorMap);
        sponsorshipData.setLegislatorList(new ArrayList(legislatorMap.values()));

        return sponsorshipData;
    }

    /**
     * Fetch a legislator using a bioguide ID if it is not already populated in the current legislator hash map.
     * @param bioguideId
     * @param legislatorHashMap
     * @param beginTimestamp
     * @param endTimestamp
     */
    protected void updateLegislatorMap(String bioguideId,
                               HashMap<String, Legislator> legislatorHashMap, long beginTimestamp, long endTimestamp) {

        if (!legislatorHashMap.containsKey(bioguideId)) {
            Legislator legislator = legislatorService.getLegislatorByIdTimestamp(bioguideId, beginTimestamp);
            if (legislator == null)
                legislator = legislatorService.getLegislatorByIdTimestamp(bioguideId, endTimestamp);
            if (legislator != null) {
                legislator.setIndex(legislatorHashMap.size()+1);
                legislatorHashMap.put(legislator.getBioguideId(),legislator);
            }
        }
    }

    @Autowired
    public void setSponsorshipRepo(SponsorshipRepo sponsorshipRepo) {
        this.sponsorshipRepo = sponsorshipRepo;
    }

    @Autowired
    public void setLegislatorRepo(LegislatorRepo legislatorRepo) {
        this.legislatorRepo = legislatorRepo;
    }

    @Autowired
    public void setLegislatorService(LegislatorService legislatorService) {
        this.legislatorService = legislatorService;
    }

    @Autowired
    public void setTimeService(TimeService timeService) {
        this.timeService = timeService;
    }
}
