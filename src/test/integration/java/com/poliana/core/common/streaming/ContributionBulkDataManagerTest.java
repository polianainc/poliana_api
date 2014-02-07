package com.poliana.core.common.streaming;

import com.poliana.config.ApplicationConfig;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.AnnotationConfigContextLoader;

/**
 * @author graysoncarroll
 * @date 2/2/14
 */
@Profile("integration_test")
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes=ApplicationConfig.class, loader=AnnotationConfigContextLoader.class)

public class ContributionBulkDataManagerTest {

    private ContributionBulkDataManager dataManager;

    @Before
    public void setup() {

    }

    @Test
    public void testCreateTestEnvironment() throws Exception {
        dataManager.createTestEnvironment();
    }

    @Autowired
    public void setDataManager(ContributionBulkDataManager dataManager) {
        this.dataManager = dataManager;
    }
}
