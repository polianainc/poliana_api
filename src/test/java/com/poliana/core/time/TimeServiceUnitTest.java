package com.poliana.core.time;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * @author David Gilmore
 * @date 12/13/13
 */
public class TimeServiceUnitTest {

    private TimeService timeService;

    @Before
    public void setUp() throws Exception {

        this.timeService = new TimeService();
    }

    @Test
    public void testGetTimestamp() throws Exception {

    }

    @Test
    public void testCongressToYears() throws Exception {

        CongressYears testCongress = timeService.congressToYears(113);

        assertEquals(2013, testCongress.getYearOne());
        assertEquals(2014, testCongress.getYearTwo());
    }

    @Test
    public void testNearestTermStart() throws Exception {

    }

    @Test
    public void testOneYear() throws Exception {

    }

    @Test
    public void testTermBeginning() throws Exception {

    }

    @Test
    public void testTimestampToCongress() throws Exception {

    }

    @Test
    public void testYearTimestamps() throws Exception {

    }
}
