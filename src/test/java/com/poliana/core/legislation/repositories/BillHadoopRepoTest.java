package com.poliana.core.legislation.repositories;

import com.poliana.config.StandaloneConfig;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.AnnotationConfigContextLoader;

import java.util.List;

/**
* @author David Gilmore
* @date 10/22/13
*/
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes=StandaloneConfig.class, loader=AnnotationConfigContextLoader.class)
public class BillHadoopRepoTest {

    @Autowired
    private BillHadoopRepo billHadoopRepo;

    private List<String> billIds;
    private List<String> voteIds;

    @Before
    public void loadData() {
    }

    @Test
    public void testBillsWithVotesByBillId() throws Exception {
    }

    @Test
    public void testBillsWithVotesByCongress() throws Exception {
        assert true;
    }

    @Test
    public void testBillMetaByBillId() throws Exception {
    }

    @Test
    public void testBillMetaByCongress() throws Exception {
    }

    @Test
    public void testBillVotesByVoteId() throws Exception {
    }

    @Test
    public void testBillVotesByYear() throws Exception {
    }
}
