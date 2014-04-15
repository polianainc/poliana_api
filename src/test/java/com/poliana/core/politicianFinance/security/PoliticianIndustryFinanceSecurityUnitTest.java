package com.poliana.core.politicianFinance.security;

import com.poliana.core.shared.UnitTestSecurityConfig;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.codec.Base64;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.AnnotationConfigContextLoader;

import java.util.Arrays;

/**
 * @author David Gilmore
 * @date 2/5/14
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes=UnitTestSecurityConfig.class, loader=AnnotationConfigContextLoader.class)
public class PoliticianIndustryFinanceSecurityUnitTest {

    static HttpHeaders getHeaders(String auth) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));

        byte[] encodedAuthorisation = Base64.encode(auth.getBytes());
        headers.add("Authorization", "Basic " + new String(encodedAuthorisation));

        return headers;
    }

    @Test
    public void testIndustryFinance() {

    }
}
