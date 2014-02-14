package com.poliana.core.common.streaming;

import com.poliana.config.ApplicationConfig;
import com.poliana.core.shared.AbstractS3Test;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.context.annotation.Profile;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.AnnotationConfigContextLoader;

import java.util.LinkedList;
import java.util.List;

/**
 * @author graysoncarroll
 * @date 2/1/14
 */
@Profile("integration_test")
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes=ApplicationConfig.class, loader=AnnotationConfigContextLoader.class)
public class CrpStreamingServiceTest extends AbstractS3Test {


    @Before
    public void setup() {

        if (needsSync())
            syncS3TestData();

        osSync.setLocalTmp(localTmp);
        osSync.setS3Bucket(s3Bucket);
    }

    @Test
    public void testHandleFileVersionConflicts() {

        List<String> conflicts = new LinkedList<>();
        conflicts.add("CampaignFin12.zip");

        osSync.handleFileVersionConflicts(conflicts);
    }

    @Test
    public void testNotifyVersionConflict() {

        List<String> versionConflicts = new LinkedList<>();

        versionConflicts.add("test file 1");
        versionConflicts.add("test file 2");

        osSync.notifyVersionConflict(versionConflicts);
    }

    @Test
    public void testUnzip() {

        osSync.unzip("CampaignFin14.zip");
    }

    @Test
    public void testUpdate() {

        osSync.update();
    }
}
