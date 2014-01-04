package com.poliana.core.shared;

import com.poliana.core.legislators.Legislator;
import com.poliana.core.sponsorship.SponsorshipCount;
import com.poliana.core.time.TimeService;
import org.bson.types.ObjectId;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * @author David Gilmore
 * @date 1/1/14
 */
public abstract class AbstractSponsorshipTest {

    protected TimeService timeService;

    public AbstractSponsorshipTest() {
        this.timeService = new TimeService();
    }

    protected List<Legislator> getLegislatorsMockData(int numLegislators) {

        List<Legislator> legislators = new ArrayList<>(numLegislators);

        int order = 0;
        String bioguideId;
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

    protected List<SponsorshipCount> getSponsorsMockData(List<Legislator> legislators) {

        int dims = legislators.size();
        int size = dims*(dims/2);
        Random rand = new Random();

        List<SponsorshipCount> sponsorships = new ArrayList<>(size);

        int i = 0;
        int offset = 1;
        for(int j=0;j<size;j++) {

            if (i >= legislators.size()-1) {
                i=0;
                offset++;
            }
            else i++;

            SponsorshipCount sponsorship = new SponsorshipCount();
            sponsorship.setSponsor(legislators.get(i).getBioguideId());
            sponsorship.setCosponsor(legislators.get((i+offset)%dims).getBioguideId());
            sponsorship.setCount(rand.nextInt(50));

            sponsorships.add(sponsorship);
        }

        return sponsorships;
    }
}
