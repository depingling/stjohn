package com.cleanwise.service.api.cachecos;

import java.util.*;

public class CacheImpl extends HashMap<CacheKey, Object> implements Cache {

    public Set<CacheKey> keys() {
        return super.keySet();
    }

    public Object remove(CacheKey key) {
        return super.remove(key);
    }

    public Object get(CacheKey key) {
        return super.get(key);
    }

    public boolean containsKey(CacheKey key) {
        return super.containsKey(key);
    }

}


