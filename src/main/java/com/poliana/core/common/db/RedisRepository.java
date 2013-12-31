package com.poliana.core.common.db;

import java.util.List;

/**
 * @author David Gilmore
 * @date 12/29/13
 */
public interface RedisRepository<V> {

    void put(V obj);

    V get(V key);

    void delete(V key);

    List<V> getObjects();
}