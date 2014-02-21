package com.poliana.core.common.streaming;

import org.easymock.IMocksControl;
import org.junit.Before;
import org.junit.Test;
import org.springframework.core.io.ClassPathResource;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

import java.net.URL;

import static org.easymock.EasyMock.*;

/**
 * @author graysoncarroll
 * @date 2/1/14
 */

public class OpenSecretsSyncUnitTest {

    private CrpStreamingService osSync;
    private JedisPool jedisPoolMock;
    private Jedis jedisMock;
    private URL metadataUrlFixture;

    private IMocksControl control;

    @Before
    public void setUp() throws Exception {

        this.control = createStrictControl();

        this.jedisPoolMock = this.control.createMock(JedisPool.class);
        this.jedisMock = this.control.createMock(Jedis.class);

        ClassPathResource cpr = new ClassPathResource("fixtures/odata_meta.xml");
//        this.metadataUrlFixture = new URL();

        this.osSync = new CrpStreamingService();

        this.osSync.setJedisPool(jedisPoolMock);
        this.osSync.setMetadataUrl(cpr.getFile().toURL());
    }

    @Test
    public void testSync__FilesDoNotExist() throws Exception {

        expect(this.jedisPoolMock.getResource()).andReturn(jedisMock);

        expect(this.jedisMock.exists("CampaignFin14")).andReturn(false);
        expect(this.jedisMock.set("CampaignFin14", "1383766050")).andReturn("");
        this.jedisPoolMock.returnResource(jedisMock);

        expectLastCall();

        expect(this.jedisPoolMock.getResource()).andReturn(jedisMock);

        expect(this.jedisMock.exists("CampaignFin12")).andReturn(false);
        expect(this.jedisMock.set("CampaignFin12", "1370102184")).andReturn("");

        this.jedisPoolMock.returnResource(jedisMock);

        expectLastCall();

        this.control.replay();
        this.osSync.sync();
        this.control.verify();
    }


    @Test
    public void testSync__FilesDoesExistNoUpdate() throws Exception {

        expect(this.jedisPoolMock.getResource()).andReturn(jedisMock);

        expect(this.jedisMock.exists("CampaignFin14")).andReturn(true);
        expect(this.jedisMock.get("CampaignFin14")).andReturn("1383766050");

        this.jedisPoolMock.returnResource(jedisMock);

        expectLastCall();

        expect(this.jedisPoolMock.getResource()).andReturn(jedisMock);

        expect(this.jedisMock.exists("CampaignFin12")).andReturn(true);
        expect(this.jedisMock.get("CampaignFin12")).andReturn("1370102184");

        this.jedisPoolMock.returnResource(jedisMock);

        expectLastCall();

        this.control.replay();
        this.osSync.sync();
        this.control.verify();
    }

    @Test
    public void testSync__FilesDoesExistWithUpdate() throws Exception {
        expect(this.jedisPoolMock.getResource()).andReturn(jedisMock);

        expect(this.jedisMock.exists("CampaignFin14")).andReturn(true);
        expect(this.jedisMock.get("CampaignFin14")).andReturn("1383766000");
        expect(this.jedisMock.set("CampaignFin14","1383766050")).andReturn("");
        this.jedisPoolMock.returnResource(jedisMock);
        expectLastCall();

        expect(this.jedisPoolMock.getResource()).andReturn(jedisMock);

        expect(this.jedisMock.exists("CampaignFin12")).andReturn(true);
        expect(this.jedisMock.get("CampaignFin12")).andReturn("1370102000");
        expect(this.jedisMock.set("CampaignFin12", "1370102184")).andReturn("");
        this.jedisPoolMock.returnResource(jedisMock);
        expectLastCall();

        this.control.replay();
        this.osSync.sync();
        this.control.verify();
    }

}
