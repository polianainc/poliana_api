package com.poliana.campaignFinance.repositories;

import com.poliana.campaignFinance.entities.IndToPolContrTotals;
import com.poliana.config.StandaloneConfig;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.AnnotationConfigContextLoader;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author David Gilmore
 * @date 11/1/13
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes=StandaloneConfig.class, loader=AnnotationConfigContextLoader.class)
@ActiveProfiles("dev")
public class ContributionRepoTest {

    @Resource
    private ContributionRepo contributionRepo;

}
