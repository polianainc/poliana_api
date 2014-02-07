package com.poliana.core.politicianFinance.security;

import com.poliana.core.shared.UnitTestSecurityConfig;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.AnnotationConfigContextLoader;

/**
 * @author David Gilmore
 * @date 2/5/14
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes=UnitTestSecurityConfig.class, loader=AnnotationConfigContextLoader.class)
public class PoliticianIndustryFinanceSecurityUnitTest {

    @Test
    public void testIndustryFinance() {

    }
}
