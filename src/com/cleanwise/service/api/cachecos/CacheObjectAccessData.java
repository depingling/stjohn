package com.cleanwise.service.api.cachecos;

import java.io.Serializable;


public class CacheObjectAccessData implements Serializable {

    private long lastAccessTime;
    private int accessCounter;

    public CacheObjectAccessData() {
        this.lastAccessTime = -1;
        this.accessCounter = 0;
    }

    public int getAccessCounter() {
        return accessCounter;
    }

    public long getLastAccessTime() {
        return lastAccessTime;
    }

    public void addAccessData(long accessTime) {
        this.lastAccessTime = accessTime;
        this.accessCounter++;
    }


}

