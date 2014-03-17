package com.poliana.core.legislators;

import com.poliana.core.shared.AbstractSponsorshipTest;
import org.easymock.IMocksControl;
import org.junit.Before;
import org.junit.Test;

import java.util.Iterator;
import java.util.List;

import static org.easymock.EasyMock.createStrictControl;
import static org.easymock.EasyMock.expect;


/**
 * @author Grayson Carroll
 * @date 3/17/14
 */
public class LegislatorCondenseServiceUnitTest extends AbstractSponsorshipTest {

    private LegislatorCondenseService legislatorCondenseService;

    private LegislatorMongoRepo legislatorMongoRepoMock;

    private IMocksControl control;


    @Before
    public void setUp() throws Exception {

        this.control = createStrictControl();

        this.legislatorMongoRepoMock = this.control.createMock(LegislatorMongoRepo.class);

        this.legislatorCondenseService = new LegislatorCondenseService();

        this.legislatorCondenseService.setLegislatorMongoRepo(legislatorMongoRepoMock);
    }

    @Test
    public void testGetLegislators__AllTime() throws Exception {

        List<Legislator> legislatorList = this.getLegislatorsFixture(10);
        Iterator<Legislator> legislatorIterator = legislatorList.iterator();
        expect(this.legislatorMongoRepoMock.getAllLegislators()).andReturn(legislatorIterator);

        this.control.replay();

        this.legislatorCondenseService.getCondensedLegislators();

        this.control.verify();

        //TODO: implement proper test logic
        assert false;
    }

    @Test
    public void testGetLegislators__TimeRange() throws Exception {

        List<Legislator> legislatorList = this.getLegislatorsFixture(10);
        Iterator<Legislator> legislatorIterator = legislatorList.iterator();
        expect(this.legislatorMongoRepoMock.getLegislators(0L, 1L)).andReturn(legislatorIterator);

        this.control.replay();

        this.legislatorCondenseService.getCondensedLegislators(0L, 1L);

        this.control.verify();

        //TODO: implement proper test logic
        assert false;
    }
}
