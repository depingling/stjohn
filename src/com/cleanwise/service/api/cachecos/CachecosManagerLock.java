package com.cleanwise.service.api.cachecos;

import java.util.Collection;
import java.util.concurrent.locks.ReentrantLock;


public class CachecosManagerLock extends ReentrantLock {

    public CachecosManagerLock() {
    }

    public CachecosManagerLock(boolean b) {
        super(b);
    }

    protected Collection<Thread> getQueuedThreads() {
        return super.getQueuedThreads();
    }

    protected Thread getOwner() {
        return super.getOwner();
    }

}
