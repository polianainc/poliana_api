package com.poliana.core.common.streaming;

import org.easymock.IMocksControl;
import org.junit.Before;
import org.junit.Test;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

import java.net.URL;

import static org.easymock.EasyMock.createStrictControl;
import static org.easymock.EasyMock.expect;
import static org.easymock.EasyMock.anyObject;
/**
 * @author graysoncarroll
 * @date 2/1/14
 */
public class OpenSecretsSyncUnitTest {
    private OpenSecretsSync osSync;
    private JedisPool jedisPoolMock;
    private Jedis jedisMock;

    private IMocksControl control;

    @Before
    public void setUp() throws Exception {

        this.control = createStrictControl();

        this.osSync = new OpenSecretsSync();
        this.jedisPoolMock = this.control.createMock(JedisPool.class);
        this.jedisMock = this.control.createMock(Jedis.class);
        this.osSync.setJedisPool(jedisPoolMock);

        String path = OpenSecretsSyncUnitTest.class.getResource("").getPath();
        path += "odata_meta.xml";
        URL url= (new java.io.File(path)).toURI().toURL();
        this.osSync.setUrl(url);
    }

    @Test
    public void testSync__FilesDoNotExist() throws Exception {

        expect(this.jedisPoolMock.getResource()).andReturn(jedisMock);

        expect(this.jedisMock.exists("CampaignFin14")).andReturn(false);
        expect(this.jedisMock.exists("CampaignFin12")).andReturn(false);

        expect(this.jedisMock.set("CampaignFin14", "1383766050")).andReturn("");
        expect(this.jedisMock.set("CampaignFin12", "1370102184")).andReturn("");

        this.control.replay();
        this.osSync.sync();
        this.control.verify();
    }

}
