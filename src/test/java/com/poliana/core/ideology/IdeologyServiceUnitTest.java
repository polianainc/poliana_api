package com.poliana.core.ideology;

import com.poliana.core.legislators.Legislator;
import com.poliana.core.legislators.LegislatorService;
import com.poliana.core.shared.AbstractSponsorshipTest;
import com.poliana.core.sponsorship.SponsorshipService;
import com.poliana.core.time.CongressTimestamps;
import com.poliana.core.time.TimeService;
import org.bson.types.ObjectId;
import org.easymock.IMocksControl;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import static org.easymock.EasyMock.createStrictControl;
import static org.easymock.EasyMock.expect;
import static org.junit.Assert.*;

/**
 * @author David Gilmore
 * @date 1/2/14
 */
public class IdeologyServiceUnitTest extends AbstractSponsorshipTest {

    private IdeologyService ideologyService;

    private IdeologyRepo ideologyRepoMock;
    private LegislatorService legislatorServiceMock;
    private SponsorshipService sponsorshipServiceMock;

    private IMocksControl control;


    @Before
    public void setUp() throws Exception {

        this.control = createStrictControl();

        this.ideologyRepoMock = this.control.createMock(IdeologyRepo.class);
        this.legislatorServiceMock = this.control.createMock(LegislatorService.class);
        this.sponsorshipServiceMock = this.control.createMock(SponsorshipService.class);

        this.ideologyService = new IdeologyService();

        this.ideologyService.setIdeologyRepo(this.ideologyRepoMock);
        this.ideologyService.setLegislatorService(this.legislatorServiceMock);
        this.ideologyService.setSponsorshipService(this.sponsorshipServiceMock);
        this.ideologyService.setTimeService(new TimeService());
    }

    @Test
    public void testGetLegislatorIdeology__not_null() throws Exception {

        LegislatorIdeology ideology = new LegislatorIdeology();

        ideology.setBioguideId("O000167");
        ideology.setCongress(110);

        expect(this.ideologyRepoMock.getLegislatorIdeology("O000167", 110)).andReturn(ideology);

        this.control.replay();

        LegislatorIdeology actual = this.ideologyService.getLegislatorIdeology("O000167", 110);

        assertEquals("O000167", actual.getBioguideId());
        assertEquals(110, actual.getCongress());
    }

    @Test
    public void testGetLegislatorIdeology__null_cache() throws Exception {

        IdeologyMatrix ideologyMatrix = getIdeologyMatrixMockData("s", 110);

        LegislatorIdeology ideology = null;
        Legislator legislator = new Legislator();

        String bioguideId = ideologyMatrix.getIdeologies().get(0).getBioguideId();
        legislator.setBioguideId(bioguideId);
        legislator.setTermType("sen");

        CongressTimestamps timestamps = timeService.getCongressTimestamps(110);
        int avg = (int) ((timestamps.getBegin() + timestamps.getEnd()) / 2);

        expect(this.ideologyRepoMock.getLegislatorIdeology(bioguideId, 110)).andReturn(ideology);
        expect(this.legislatorServiceMock.getLegislatorByIdTimestamp(bioguideId, avg)).andReturn(legislator);
        expect(this.ideologyRepoMock.getIdeologyMatrix("s", 110)).andReturn(ideologyMatrix);


    }

    private IdeologyMatrix getIdeologyMatrixMockData(String chamber, int congress) {

        CongressTimestamps timestamps = this.timeService.getCongressTimestamps(congress);

        IdeologyMatrix ideologyMatrix = new IdeologyMatrix();

        ideologyMatrix.setChamber(chamber);
        ideologyMatrix.setCongress(congress);
        ideologyMatrix.setBeginTimestamp(timestamps.getBegin());
        ideologyMatrix.setEndTimestamp(timestamps.getEnd());

        double[] values = randomDoubleArray(20);
        ideologyMatrix.setIdeologyValues(values);
        ideologyMatrix.setIdeologies(getLegislatorIdeologyMockData(chamber, values));

        return ideologyMatrix;
    }

    private List<LegislatorIdeology> getLegislatorIdeologyMockData(String chamber, double[] values) {

        List<LegislatorIdeology> ideologies = new ArrayList<>(values.length);

        int order = 0;
        String bioguideId;
        for(int i = 0; i < values.length; i++) {

            LegislatorIdeology ideology = new LegislatorIdeology();

            ideology.setId(new ObjectId().toString());

            order = (i/10 == order) ? order : order++;
            bioguideId = String.format("A%0" + (6-order) + "d", i);
            ideology.setBioguideId(bioguideId);

            ideology.setIdeology(values[i]);
            ideology.setReligion("Object Oriented");
            ideology.setChamber(chamber);

            ideologies.add(ideology);
        }

        return ideologies;
    }

    private double[] randomDoubleArray(int size) {

        Random rand = new Random();
        double[] ret = new double[size];

        for (int i = 0; i < ret.length; i++)
            ret[i] = rand.nextDouble();

        return ret;
    }
}
