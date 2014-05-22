package com.cleanwise.service.api.cachecos;

import com.cleanwise.service.api.value.TableObject;

import java.util.Set;

/**
 * Title:        CachecosManager
 * Description:  Interface for CACHECOS manager.
 * Purpose:      Provides access to the methods of manager.
 * Copyright:    Copyright (c) 2009
 * Company:      CleanWise, Inc.
 * Date:         12.01.2009
 * Time:         16:34:09
 *
 * @author Alexander Chickin, TrinitySoft, Inc.
 */
public interface CachecosManager {

    public static final int STARTED = 1;
    public static final int STOPPED = 0;
    public static final int BROKEN  = 2;

    public void remove(CacheKey o);

    public void remove(TableObject pData);

    public void removeAll(Set<CacheKey> keys);

    public void put(CacheKey key, Object data, ObjectMeta meta);

    public void put(CacheKey key, Object data);

    public boolean isReadyToWork();

    public Object get(CacheKey key);

    public void clear();

    public void setManagedCache(Cache cache,CacheDescriptor cacheDescriptor,CacheAccessHistory accessHistory);

    public boolean containsKey(CacheKey key);

    public String getCacheDescription();

    public CacheAccessHistory getAccessHistory();

    public boolean stop();

    public boolean start();

    public int getState();

    public boolean isStarted();

    public CacheInformation info();

    public void setObjectLifetime(long time);

    public void setGarbageCollectionInterval(long timeInterval);

    public void gc();

    public Set<CacheKey> getKeys();
}
