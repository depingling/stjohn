package com.cleanwise.service.api.cachecos;

import java.util.Date;

public class CacheInformation {

    long cacheMemo;
    long descriptorMemo;
    long accessHistoryMemo;
    int size;
    int state;
    long requestTime;
    long lastAccessTime;
    private int accessCounterGet;
    private int accessCounterPut;
    private int accessCounterRemove;
    private long garbageCollectionInterval;
    private long objectLifetime;
    private Date lastGarbageCollectorRun;

    public long getCacheMemo() {
        return cacheMemo;
    }

    public void setCacheMemo(long cacheMemo) {
        this.cacheMemo = cacheMemo;
    }

    public long getDescriptorMemo() {
        return descriptorMemo;
    }

    public void setDescriptorMemo(long descriptorMemo) {
        this.descriptorMemo = descriptorMemo;
    }

    public long getAccessHistoryMemo() {
        return accessHistoryMemo;
    }

    public void setAccessHistoryMemo(long accessHistoryMemo) {
        this.accessHistoryMemo = accessHistoryMemo;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

    public long getRequestTime() {
        return requestTime;
    }

    public void setRequestTime(long requestTime) {
        this.requestTime = requestTime;
    }

    public long getLastAccessTime() {
        return lastAccessTime;
    }

    public void setLastAccessTime(long lasstAccessTime) {
        this.lastAccessTime = lasstAccessTime;
    }

    public void setAccessCounterGet(int accessCounterGet) {
        this.accessCounterGet = accessCounterGet;
    }

    public void setAccessCounterPut(int accessCounterPut) {
        this.accessCounterPut = accessCounterPut;
    }

    public void setAccessCounterRemove(int accessCounterRemove) {
        this.accessCounterRemove = accessCounterRemove;
    }

    public int getAccessCounterGet() {
        return accessCounterGet;
    }

    public int getAccessCounterPut() {
        return accessCounterPut;
    }

    public int getAccessCounterRemove() {
        return accessCounterRemove;
    }

    public void setGarbageCollectionInterval(long garbageCollectionInterval) {
        this.garbageCollectionInterval = garbageCollectionInterval;
    }

    public void setObjectLifetime(long objectLifetime) {
        this.objectLifetime = objectLifetime;
    }

    public long getGarbageCollectionInterval() {
        return garbageCollectionInterval;
    }

    public long getObjectLifetime() {
        return objectLifetime;
    }

    public void setLastGarbageCollectorRun(Date lastGarbageCollectorRun) {
        this.lastGarbageCollectorRun = lastGarbageCollectorRun;
    }

    public Date getLastGarbageCollectorRun() {
        return lastGarbageCollectorRun;
    }
}
