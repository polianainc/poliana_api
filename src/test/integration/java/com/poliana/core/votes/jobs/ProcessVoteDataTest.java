package com.poliana.core.votes.jobs;

import com.poliana.config.ApplicationConfig;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.AnnotationConfigContextLoader;

/**
 * @author David Gilmore
 * @date 1/22/14
 */
@Profile("integration_test")
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes=ApplicationConfig.class, loader=AnnotationConfigContextLoader.class)
public class ProcessVoteDataTest {

    private ProcessVoteData processVoteData;

    @Test
    public void testProcessVoteData() throws Exception {

        processVoteData.processVoteData();
    }

    @Autowired
    public void setProcessVoteData(ProcessVoteData processVoteData) {
        this.processVoteData = processVoteData;
    }
}
