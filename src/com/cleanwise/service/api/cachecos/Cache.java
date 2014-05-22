package com.cleanwise.service.api.cachecos;

import java.io.Serializable;
import java.util.Set;
import java.util.Collection;

public interface Cache extends Serializable {

    public Set<CacheKey> keys();

    public Collection<Object> values();

    public Object put(CacheKey key, Object data);

    public Object remove(CacheKey key);

    public Object get(CacheKey key);

    public void clear();

    public int size();

    public boolean containsKey(CacheKey key);

}
