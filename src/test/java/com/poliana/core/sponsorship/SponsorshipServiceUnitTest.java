package com.poliana.core.sponsorship;

import com.poliana.core.time.CongressTimestamps;
import com.poliana.core.legislators.Legislator;
import com.poliana.core.legislators.LegislatorRepo;
import com.poliana.core.legislators.LegislatorService;
import com.poliana.core.shared.AbstractSponsorshipTest;
import com.poliana.core.time.TimeService;
import org.easymock.IMocksControl;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

import static org.easymock.EasyMock.*;
import static org.junit.Assert.*;

/**
 * @author David Gilmore
 * @date 12/30/13
 */
public class SponsorshipServiceUnitTest extends AbstractSponsorshipTest {

    private SponsorshipService sponsorshipService;

    private LegislatorRepo legislatorRepoMock;
    private LegislatorService legislatorServiceMock;
    private SponsorshipRepo sponsorshipRepoMock;

    private IMocksControl control;

    @Before
    public void setUp() throws Exception {

        this.control = createStrictControl();

        this.legislatorRepoMock = this.control.createMock(LegislatorRepo.class);
        this.legislatorServiceMock = this.control.createMock(LegislatorService.class);
        this.sponsorshipRepoMock = this.control.createMock(SponsorshipRepo.class);

        this.sponsorshipService = new SponsorshipService();

        this.sponsorshipService.setLegislatorRepo(this.legislatorRepoMock);
        this.sponsorshipService.setLegislatorService(this.legislatorServiceMock);
        this.sponsorshipService.setSponsorshipRepo(this.sponsorshipRepoMock);
        this.sponsorshipService.setTimeService(new TimeService());
    }

    @Test
    public void testGetSponsorshipMatrix__congress() throws Exception {

        List<Legislator> legislatorListMock = getLegislatorsMockData(100);
        List<SponsorshipCount> sponsorshipCountsMock = getSponsorsMockData(legislatorListMock);
        CongressTimestamps timestamps = this.timeService.congressTimestamps(110);

        expect(this.sponsorshipRepoMock.getSponsorshipCounts("s", 110)).andReturn(sponsorshipCountsMock);
        expect(this.legislatorRepoMock.getLegislators("s", timestamps.getBegin(), timestamps.getEnd()))
                .andReturn(legislatorListMock.iterator());

        this.control.replay();

        SponsorshipMatrix sponsorshipMatrix = this.sponsorshipService.getSponsorshipMatrix("s", 110);

        this.control.verify();

        assertEquals("s", sponsorshipMatrix.getChamber());
        assertEquals(timestamps.getBegin(), sponsorshipMatrix.getBeginTimestamp());
        assertEquals(timestamps.getEnd(), sponsorshipMatrix.getEndTimestamp());
        //TODO: Assert More Dominance!
    }
}
