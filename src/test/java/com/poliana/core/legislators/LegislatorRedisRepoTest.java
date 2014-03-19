package com.poliana.core.legislators;

import org.easymock.IMocksControl;
import org.junit.Before;
import org.junit.Test;
import org.msgpack.MessagePack;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

import static org.easymock.EasyMock.createStrictControl;
import static org.easymock.EasyMock.expect;

/**
 * @author graysoncarroll
 * @date 3/19/14
 */
public class LegislatorRedisRepoTest {

    private LegislatorRedisRepo legislatorRedisRepo;

    private Jedis jedis;
    private JedisPool jedisPoolMock;

    private IMocksControl control;

    @Before
    public void setUp() throws Exception {

        this.control = createStrictControl();

        this.jedisPoolMock = this.control.createMock(JedisPool.class);
        this.jedis = this.control.createMock(Jedis.class);

        this.legislatorRedisRepo = new LegislatorRedisRepo();

        this.legislatorRedisRepo.setJedisPool(this.jedisPoolMock);
    }

    @Test
    public void testGetLegislatorCondensed() throws Exception {

        //TODO: Finish test
        LegislatorCondensed legislatorCondensed = new LegislatorCondensed();

        MessagePack messagePack = new MessagePack();

        byte[] legislatorCondensedBytes = messagePack.write(legislatorCondensed);

//        expect(this.jedisPoolMock.getResource()).andReturn(this.jedis);
//        expect(this.jedis.get("O000167".getBytes())).andReturn(legislatorCondensedBytes);

//        this.jedisPoolMock.returnResource(this.jedis);

        this.control.replay();

//        this.legislatorRedisRepo.getCondensedLegislator("O000167");

        this.control.verify();
    }

    @Test
    public void testStoreOrUpdateCondensedLegislator() throws Exception {

    }

    @Test
    public void testSetCondensedLegislatorsCached() throws Exception {

    }

    @Test
    public void testGetCondensedLegislatorsCached() throws Exception {

    }

    @Test
    public void testGetCondensedLegislators() throws Exception {

    }
}
