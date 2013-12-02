package com.poliana.core.legislation.services;

import com.poliana.config.StandaloneConfig;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.AnnotationConfigContextLoader;

/**
 * @author David Gilmore
 * @date 12/2/13
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes=StandaloneConfig.class, loader=AnnotationConfigContextLoader.class)
public class IdeologyAnalysisTest {
    @Test
    public void testGetSponsorshipMatrix() throws Exception {

    }
}
