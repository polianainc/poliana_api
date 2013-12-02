package com.poliana.core.campaignFinance.repositories;

import com.poliana.config.StandaloneConfig;
import org.junit.runner.RunWith;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.AnnotationConfigContextLoader;

import javax.annotation.Resource;

/**
 * @author David Gilmore
 * @date 11/1/13
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes=StandaloneConfig.class, loader=AnnotationConfigContextLoader.class)
@ActiveProfiles("dev")
public class ContributionHadoopRepoTest {

    @Resource
    private ContributionHadoopRepo contributionHadoopRepo;

}
