package com.cleanwise.service.api.cachecos;

import java.util.*;
import java.io.Serializable;


public class CacheAccessHistory implements Serializable {

    private HashMap<CacheKey, CacheObjectAccessHistory> history;
    private long lastAccessTime;
    private CacheKey lastAccessKey;

    public CacheAccessHistory() {
        history = new HashMap<CacheKey, CacheObjectAccessHistory>();
    }

    public void add(CacheKey key, String accessCode, long time) {

        lastAccessTime = time;
        lastAccessKey = key;

        CacheObjectAccessData objHistoryData;
        if (history.containsKey(key)) {
            CacheObjectAccessHistory oHistory = history.get(key);
            objHistoryData = oHistory.get(accessCode);
            if (objHistoryData == null) {
                objHistoryData = new CacheObjectAccessData();
                oHistory.put(accessCode, objHistoryData);
            }
            objHistoryData.addAccessData(time);
        } else {
            CacheObjectAccessHistory oHistory = new CacheObjectAccessHistory();
            objHistoryData = new CacheObjectAccessData();
            objHistoryData.addAccessData(time);
            oHistory.put(accessCode, objHistoryData);
            history.put(key, oHistory);
        }
    }

    public void clear() {
        history.clear();
    }

    public HashMap<CacheKey, CacheObjectAccessHistory> getHistory() {
        return history;
    }

    public long getLastAccessTime() {
        return lastAccessTime;
    }

    public CacheKey getLastAccessKey() {
        return lastAccessKey;
    }
}
