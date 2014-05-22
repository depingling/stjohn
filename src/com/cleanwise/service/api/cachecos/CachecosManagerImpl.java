package com.cleanwise.service.api.cachecos;

import com.cleanwise.service.api.value.TableObject;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.*;
import java.util.concurrent.TimeUnit;
import org.apache.log4j.Logger;

/**
 * Title:        CachecosManagerImpl
 * Description:  CACHECOS manager.
 * Purpose:      Provides access to cache.
 * Copyright:    Copyright (c) 2009
 * Company:      CleanWise, Inc.
 * Date:         11.01.2009
 * Time:         13:28:51
 *
 * @author Alexander Chickin, TrinitySoft, Inc.
 */
public class CachecosManagerImpl implements CachecosManager {

    private static final Logger log = Logger.getLogger(CachecosManagerImpl.class);

    private static final int WAITING_TIME_TO_STOP = 60; //seconds
    private static final int WAITING_TIME_TO_START = 60; //seconds

    Cache cache;
    CacheDescriptor cacheDescriptor;
    CacheAccessHistory cacheAccessHistory;

    private final CachecosManagerLock lock;
    private int state;

    private Thread garbageCollector;
    private long objectLifetime;
    private long garbageCollectionInterval;
    private Date lastGarbageCollectorRun;

    public CachecosManagerImpl() {
        lock = new CachecosManagerLock();
        state = STOPPED;
    }

    public void setManagedCache(Cache cache, CacheDescriptor descriptor, CacheAccessHistory accessHistory) {
        this.cache = cache;
        this.cacheDescriptor = descriptor;
        this.cacheAccessHistory = accessHistory;
    }

    public boolean containsKey(CacheKey key) {
        return isStarted() && cache.containsKey(key);
    }

    public void setObjectLifetime(long time) {
        this.objectLifetime = time;
    }

    public void setGarbageCollectionInterval(long timeInterval) {
        this.garbageCollectionInterval = timeInterval;
    }

    public String getCacheDescription() {
        try {
            lock.lockInterruptibly();
            try {
                if (isStarted()) {
                    return cacheDescriptor.toJson();
                }
            } finally {
                lock.unlock();
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return null;
    }

    public CacheAccessHistory getAccessHistory() {
        return cacheAccessHistory;
    }

    public boolean isReadyToWork() {
        return cache != null && cacheDescriptor != null && cacheAccessHistory != null;
    }

    public void put(CacheKey key, Object data) {
        put(key, data, null);
    }

    public Object get(CacheKey key) {
        if (isStarted()) {
            if(cache.containsKey(key)) {
                cacheAccessHistory.add(key, CacheAccessCode.GET, System.currentTimeMillis());
            }
            return cache.get(key);
        } else {
            return null;
        }
    }

    public void clear() {
        lock.lock();
        try {
            cacheDescriptor.clear();
            cacheAccessHistory.clear();
            cache.clear();
            System.gc();
        } finally {
            lock.unlock();
        }
    }

    public void put(CacheKey key, Object data, ObjectMeta meta) {
        try {
            lock.lockInterruptibly();
            try {
                if (isStarted()) {
                    if(containsKey(key)){
                        remove(key);
                    }
                    cache.put(key, data);
                    writeToCacheDescriptor(key, meta);
                    cacheAccessHistory.add(key, CacheAccessCode.PUT, System.currentTimeMillis());
                }
            } finally {
                lock.unlock();
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private void writeToCacheDescriptor(CacheKey key, ObjectMeta meta) {
        if (meta != null) {
            for (VarMeta varMeta : meta) {
                for (ObjectMeta oMeta : varMeta)
                    if (oMeta instanceof TableObjectMeta) {

                        String table = ((TableObjectMeta) oMeta).getTable();
                        ArrayList<TableObjectFieldMeta> tableFields = ((TableObjectMeta) oMeta).getFields();

                        CacheTableDescriptor tableDescriptor = cacheDescriptor.getTableDescriptor();
                        CacheTableLinkDescriptor removalReqPointer = cacheDescriptor.getTableLinkDescriptor();
                        if (tableFields != null) {

                            CacheTableFieldMap ctfMap = tableDescriptor.get(table);
                            if (ctfMap == null) {
                                ctfMap = new CacheTableFieldMap();
                            }

                            HashMap<CacheKey, HashSet<HashSet<String>>> rrPointer = removalReqPointer.get(table);
                            if (rrPointer == null) {
                                rrPointer = new HashMap<CacheKey, HashSet<HashSet<String>>>();
                            }

                            HashSet<HashSet<String>> fCriteries = rrPointer.get(key);
                            if (fCriteries == null) {
                                fCriteries = new HashSet<HashSet<String>>();
                            }

                            HashSet<String> fields = new HashSet<String>();
                            for (TableObjectFieldMeta tField : tableFields) {
                                fields.add(tField.getField());
                            }

                            if (!fields.isEmpty()) {
                                fCriteries.add(fields);
                            }

                            rrPointer.put(key, fCriteries);
                            removalReqPointer.put(table, rrPointer);

                            for (TableObjectFieldMeta tField : tableFields) {

                                String fieldName = tField.getField();
                                Object fieldVal = tField.getValue();

                                CacheFieldDescription cfDesc = ctfMap.get(fieldName);
                                if (cfDesc == null) {
                                    cfDesc = new CacheFieldDescription();
                                }

                                HashSet<CacheKey> cacheKeys = cfDesc.get(fieldVal);
                                if (cacheKeys == null) {
                                    cacheKeys = new HashSet<CacheKey>();
                                }

                                cacheKeys.add(key);

                                cfDesc.put(fieldVal, cacheKeys);
                                ctfMap.put(fieldName, cfDesc);

                                cacheDescriptor.getKeyDescriptor().add(key, table, fieldName, fieldVal);

                            }

                            tableDescriptor.put(table, ctfMap);

                        }
                    } else {
                        writeToCacheDescriptor(key, oMeta);
                    }
            }
        }
    }

    public void remove(TableObject pData) {

        try {

            lock.lockInterruptibly();

            try {

                if (isStarted()) {

                    String table = pData.getTable();
                    HashSet<CacheKey> keysToRemove = new HashSet<CacheKey>();
                    HashMap<String, HashSet<CacheKey>> matchFieldMap = new HashMap<String, HashSet<CacheKey>>();

                    CacheTableFieldMap ctfMap = cacheDescriptor.getTableDescriptor().get(table);
                    if (ctfMap == null) {
                        return;
                    }

                    Set<String> fields = ctfMap.keySet();
                    for (String field : fields) {

                        CacheFieldDescription cfDesc = ctfMap.get(field);
                        HashSet<CacheKey> keys = cfDesc.get(pData.getFieldValue(field));

                        matchFieldMap.put(field, keys);
                    }

                    CacheTableLinkDescriptor removalReqPointer = cacheDescriptor.getTableLinkDescriptor();
                    HashMap<CacheKey, HashSet<HashSet<String>>> rKeys = removalReqPointer.get(table);

                    for (CacheKey rKey : rKeys.keySet()) {
                        HashSet<HashSet<String>> rCriteries = rKeys.get(rKey);
                        int counter = 0;
                        for (HashSet<String> rCriteria : rCriteries) {
                            if (rCriteria != null && !rCriteria.isEmpty()) {
                                for (String field : rCriteria) {
                                    HashSet<CacheKey> mKeys = matchFieldMap.get(field);
                                    if (mKeys != null && mKeys.contains(rKey)) {
                                        counter++;
                                    }
                                    if (counter == rCriteria.size()) {
                                        keysToRemove.add(rKey);
                                        break;
                                    }
                                }
                            }
                        }
                    }

                    removeAll(keysToRemove);
                }
            } finally {
                lock.unlock();
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }

    public void remove(CacheKey o) {

        try {

            lock.lockInterruptibly();

            try {

                if (isStarted()) {

                    CacheKeyDescriptor keyDescriptor = cacheDescriptor.getKeyDescriptor();
                    CacheTableDescriptor tableDescriptor = cacheDescriptor.getTableDescriptor();
                    CacheTableLinkDescriptor removalReqPointer = cacheDescriptor.getTableLinkDescriptor();

                    HashMap<String, HashMap<String, HashSet<Object>>> rt = keyDescriptor.get(o);

                    if (rt != null) {
                        Set<String> rtKeys = rt.keySet();
                        for (Object oRtKey : rtKeys) {
                            String rtKey = (String) oRtKey;
                            CacheTableFieldMap table = tableDescriptor.get(rtKey);
                            HashMap<String, HashSet<Object>> fields = rt.get(rtKey);
                            Set<String> fieldsKey = fields.keySet();

                            for (String oFieldKey : fieldsKey) {

                                CacheFieldDescription field = table.get(oFieldKey);
                                HashSet<Object> values = fields.get(oFieldKey);

                                for (Object value : values) {

                                    HashSet<CacheKey> reqs = field.get(value);

                                    reqs.remove(o);

                                    if (reqs.isEmpty()) {
                                        field.remove(value);
                                    }

                                    if (field.isEmpty()) {
                                        table.remove(oFieldKey);
                                    }
                                    if (table.isEmpty()) {
                                        tableDescriptor.remove(rtKey);
                                    }

                                }

                                HashMap<CacheKey, HashSet<HashSet<String>>> keys = removalReqPointer.get(rtKey);
                                if (keys != null) {
                                    keys.remove(o);
                                    if (keys.isEmpty()) {
                                        removalReqPointer.remove(rtKey);
                                    }
                                }
                            }
                        }
                    }

                    keyDescriptor.remove(o);
                    cacheAccessHistory.add(o, CacheAccessCode.REMOVE, System.currentTimeMillis());
                    cache.remove(o);
                }
            } finally {
                lock.unlock();
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void removeAll(Set<CacheKey> keys) {
        try {
            lock.lockInterruptibly();
            try {
                if (isStarted()) {
                    for (CacheKey key : keys) {
                        remove(key);
                    }
                }
            } finally {
                lock.unlock();
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public CacheInformation info() {
        log.info("info[" + Thread.currentThread().getId() + "] => BEGIN. Lock: " + lock);
        lock.lock();
        try {

            CacheInformation cInfo = new CacheInformation();

            long cacheMemoryMb = memo(cache);
            long cacheMetaMb = memo(cacheDescriptor);
            long cacheAccessHistoryMb = memo(cacheAccessHistory);

            HashMap accessCounterMap = getAccessCounterMap();

            cInfo.setCacheMemo(cacheMemoryMb);
            cInfo.setDescriptorMemo(cacheMetaMb);
            cInfo.setAccessHistoryMemo(cacheAccessHistoryMb);
            cInfo.setSize(getSize());
            cInfo.setState(getState());
            cInfo.setRequestTime(System.currentTimeMillis());
            cInfo.setLastAccessTime(cacheAccessHistory.getLastAccessTime());

            cInfo.setAccessCounterGet((Integer)accessCounterMap.get(CacheAccessCode.GET));
            cInfo.setAccessCounterPut((Integer)accessCounterMap.get(CacheAccessCode.PUT));
            cInfo.setAccessCounterRemove((Integer)accessCounterMap.get(CacheAccessCode.REMOVE));
            cInfo.setGarbageCollectionInterval(garbageCollectionInterval);
            cInfo.setObjectLifetime(objectLifetime);
            cInfo.setLastGarbageCollectorRun(lastGarbageCollectorRun);

            return cInfo;

        } finally {
            lock.unlock();
            log.info("info[" + Thread.currentThread().getId() + "] => END. Lock: " + lock);
        }
    }

    protected long memo(Object o) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ObjectOutputStream oos = null;
        try {
            oos = new ObjectOutputStream(baos);
            oos.writeObject(o);
            return baos.toByteArray().length;
        } catch (IOException e) {
            e.printStackTrace();
        } catch (java.lang.OutOfMemoryError e) {
            e.printStackTrace();
        } finally {
            try {
                baos.close();
                oos.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            System.gc();
        }

        return 0;
    }

    public synchronized boolean stop() {

        log.info("stop[" + Thread.currentThread().getId() + "] => BEGIN. Current State: " + state);

        if (STOPPED == state) {
            return true;
        }

        if (BROKEN == state) {
            return false;
        }

        log.info("stop[" + Thread.currentThread().getId() + "] => Qwner: " + lock.getOwner());
        log.info("stop[" + Thread.currentThread().getId() + "] => Queue Length: " + lock.getQueueLength());

        try {
            if (lock.tryLock(WAITING_TIME_TO_STOP, TimeUnit.SECONDS)) {
                try {
                    try {
                        state = STOPPED;

                        Collection<Thread> threads = lock.getQueuedThreads();
                        for (Thread thread : threads) {
                            thread.interrupt();
                        }

                        if (garbageCollector != null) {
                            garbageCollector.interrupt();
                            garbageCollector = null;
                        }

                        clear();
                        log.info("stop[" + Thread.currentThread().getId() + "] => manager stopped.");
                        log.info("stop[" + Thread.currentThread().getId() + "] => END. State: " + state);
                        return true;
                    } catch (Exception e) {
                        state = BROKEN;
                        log.info("stop[" + Thread.currentThread().getId() + "] => manager broken.");
                        log.info("stop[" + Thread.currentThread().getId() + "] => END. Exception: "+e.getMessage()+", State " + state);
                        e.printStackTrace();
                        return false;
                    }
                } finally {
                    lock.unlock();
                }
            } else {
                log.info("stop[" + Thread.currentThread().getId() + "] => END. Waiting time to stop is over. State " + state);
                return false;
            }
        } catch (InterruptedException e) {
            log.info("stop[" + Thread.currentThread().getId() + "] => END. InterruptedException: "+e.getMessage()+", State " + state);
            e.printStackTrace();
            return false;
        }
    }

    public synchronized boolean start() {

        log.info("start[" + Thread.currentThread().getId() + "] => BEGIN. Current State: " + state);

        if (state == STARTED) {
            return true;
        }

        if (state == BROKEN) {
            return false;
        }

        try {

            log.info("start[" + Thread.currentThread().getId() + "] => Qwner: " + lock.getOwner());
            log.info("start[" + Thread.currentThread().getId() + "] => Queue Length: " + lock.getQueueLength());

            if (lock.tryLock(WAITING_TIME_TO_START, TimeUnit.SECONDS)) {
                try {
                    try {
                        if (cache.keys().isEmpty() && cacheDescriptor.isEmpty() && garbageCollector == null) {
                            if (garbageCollectionInterval > 0) {
                                garbageCollector = new Thread() {
                                    public void run() {
                                        while (!this.isInterrupted()) {
                                            try {
                                                Thread.sleep(garbageCollectionInterval);
                                            } catch (InterruptedException e) {
                                                break;
                                            }
                                            lastGarbageCollectorRun = new Date();
                                            gc();
                                        }
                                        log.info("garbageCollector[" + Thread.currentThread().getId() + "] => garbage collector stopped." );
                                    }
                                };
                                garbageCollector.start();
                                log.info("start[" + Thread.currentThread().getId() + "] => garbage collector started." );
                            }

                            state = STARTED;
                            log.info("start[" + Thread.currentThread().getId() + "] => manager started." );
                            return true;
                        }
                    } catch (Exception e) {
                        state = BROKEN;
                    }
                } finally {
                    lock.unlock();
                }
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally{
            log.info("start[" + Thread.currentThread().getId() + "] => END. Current State: " + state );
        }

        return false;

    }

    public boolean isStarted() {
        return (state == STARTED);
    }

    public boolean isStopped() {
        return (state == STOPPED);
    }

    public int getState() {
        return state;
    }

    public int getSize() {
        return cache.size();
    }

    public HashMap getAccessCounterMap() {

        HashMap<String, Integer> accessCounterMap = new HashMap<String, Integer>();

        accessCounterMap.put(CacheAccessCode.GET, 0);
        accessCounterMap.put(CacheAccessCode.REMOVE, 0);
        accessCounterMap.put(CacheAccessCode.PUT, 0);

        HashMap<CacheKey, CacheObjectAccessHistory> history = cacheAccessHistory.getHistory();
        Set<CacheKey> keys = history.keySet();
        for (CacheKey key : keys) {
            CacheObjectAccessHistory objAccessHistory = history.get(key);
            Set<String> accessCodes = objAccessHistory.keySet();
            for (String accessCode : accessCodes) {
                CacheObjectAccessData objAccessData  = objAccessHistory.get(accessCode);
                Integer counter = accessCounterMap.get(accessCode);
                counter += objAccessData.getAccessCounter();
                accessCounterMap.put(accessCode, counter);
            }
        }

        return accessCounterMap;
    }

    /**
     * Search keys of cached objects which are old
     * and execute commands of the CACHECOS manager for removing these keys
     * from the cache.
     */
    public void gc() {

        log.info("gc[" + Thread.currentThread().getId() + "] => BEGIN.");

        long time = System.currentTimeMillis();

        try {

            lock.lockInterruptibly();

            try {

                log.info("gc[" + Thread.currentThread().getId() + "] => Search keys of cached objects which are old...");

                HashSet<CacheKey> keysToRemove = new HashSet<CacheKey>();

                HashMap<CacheKey, CacheObjectAccessHistory> history = cacheAccessHistory.getHistory();
                Set<CacheKey> keys = history.keySet();
                for (CacheKey key : keys) {
                    if (containsKey(key)) {
                        CacheObjectAccessHistory objAccessHistory = history.get(key);
                        Set<String> accessCodes = objAccessHistory.keySet();
                        long lastAccessTime = 0;
                        for (String accessCode : accessCodes) {
                            CacheObjectAccessData objAccessData = objAccessHistory.get(accessCode);
                            if (objAccessData.getLastAccessTime() > lastAccessTime) {
                                lastAccessTime = objAccessData.getLastAccessTime();
                            }
                        }
                        if (time - lastAccessTime > objectLifetime) {
                            keysToRemove.add(key);
                        }
                    }
                }

                int keysSize = keysToRemove.size();
                log.info("gc[" + Thread.currentThread().getId() + "] => keys to remove : " + keysSize);
                if (keysSize > 0) {
                    removeAll(keysToRemove);
                    log.info("gc[" + Thread.currentThread().getId() + "] => keys is removed.");
                }
            } finally {
                lock.unlock();
            }

        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        log.info("gc[" + Thread.currentThread().getId() + "] => END.");

    }

    public Set<CacheKey> getKeys() {
        return cache.keys();
    }
}
