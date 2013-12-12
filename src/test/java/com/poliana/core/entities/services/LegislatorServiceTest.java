package com.poliana.core.entities.services;

import com.poliana.config.StandaloneConfig;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.AnnotationConfigContextLoader;

/**
 * @author David Gilmore
 * @date 12/3/13
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes=StandaloneConfig.class, loader=AnnotationConfigContextLoader.class)
public class LegislatorServiceTest {
    @Test
    public void testLegislatorByIdTimestamp() throws Exception {

    }

    @Test
    public void testSetCacheLis() throws Exception {

    }

    @Test
    public void testSetCacheBioguide() throws Exception {

    }

    @Test
    public void testSetCacheThomas() throws Exception {

    }

    @Test
    public void testGetLegislatorBioguideMap() throws Exception {

    }
}
