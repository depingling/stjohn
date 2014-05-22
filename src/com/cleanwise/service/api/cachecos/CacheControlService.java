package com.cleanwise.service.api.cachecos;

import org.jboss.system.ServiceMBeanSupport;
import org.jboss.naming.NonSerializableFactory;

import javax.naming.NamingException;
import javax.naming.InitialContext;
import javax.naming.Name;

/**
 * Title:        CacheControlService
 * Description:  JBoss specific MBean implementation for configuring, starting, and
 * binding to JNDI a Cache Control System Manageer.
 * Purpose:     Configuration, starting, and
 * binding to JNDI a Cache Control System Manager.
 * Copyright:    Copyright (c) 2009
 * Company:      CleanWise, Inc.
 * Date:         11.01.2009
 * Time:         13:21:32
 *
 * @author Alexander Chickin, TrinitySoft, Inc.
 */
public class CacheControlService extends ServiceMBeanSupport implements CacheControlServiceMBean {

    private CachecosManager cacheManager;
    private String jndiName;
    private Class<?> manager;
    private Class<?> cache;
    private int managerState;
    private long cacheObjectLifetime;
    private long garbageCollectionInterval;

    public void setJndiName(String jndiName) throws Exception {
        String oldName = this.jndiName;
        this.jndiName = jndiName;
        if (super.getState() == STARTED) {
            unbind(oldName);
            try {
                rebind();
            } catch (Exception e) {
                String message = "Failed to rebind Cachecos manager - ";
                log.error(message, e);
                throw new Exception(message, e);
            }
        }
    }

    public String getJndiName() {
        return this.jndiName;
    }

    public void setManager(String clazz) throws Exception {
        manager = Class.forName(clazz);
    }

    public void setCache(String clazz) throws Exception {
        cache = Class.forName(clazz);
    }

    public void setManagerState(int state) {
       this.managerState = state;
    }

    public void setCacheObjectLifetime(long time) {
        this.cacheObjectLifetime = time;
    }

    public void setGarbageCollectionInterval(long time) {
        this.garbageCollectionInterval = time;
    }

    protected void createService() throws Exception {
        log.info("createService => Create Cache Control System Service(CACHECOS)...");
        cacheManager = (CachecosManager) manager.newInstance();
        cacheManager.setManagedCache((Cache) cache.newInstance(),new CacheDescriptor(),new CacheAccessHistory());
        cacheManager.setObjectLifetime(cacheObjectLifetime);
        cacheManager.setGarbageCollectionInterval(garbageCollectionInterval);
        log.info("createService => Cachecos service created.");
    }

    public void stopService() throws Exception {
        log.info("stopService => Stop Cachecos service...");
        unbind(jndiName);
        log.info("stopService => Cachecos service stopped.");
    }

    public void destroyService() throws Exception {
        log.info("destroyService => Destroy Cachecos service...");
        manager = null;
        log.info("destroyService => Cachecos service destroyed.");
    }

    public void startService() throws Exception {

        log.info("startService => Start Cachecos...");

        log.debug("startService => manager: " + manager.getName());
        log.debug("startService => cache: " + cache.getName());
        log.debug("startService => cache object lifetime: " + cacheObjectLifetime+" msec.");
        log.debug("startService => garbage collection interval: " + garbageCollectionInterval +" msec.");

        if (cacheManager.isReadyToWork()) {
            log.info("startService => Manager is ready to work.");
            log.info("startService => Binding Cachecos manager...");
            try {
                rebind();
            } catch (NamingException ne) {
                log.error("Failed to rebind Cachecos manager", ne);
                throw new Exception("Failed to rebind Cachecos manager- ", ne);
            }
            log.info("startService => Ok!");

            log.info("startService => Start Cachecos manager...");
            switch (managerState) {
                case CachecosManager.STARTED:
                    cacheManager.start();
                    break;
                case CachecosManager.STOPPED:
                    cacheManager.stop();
                    break;
                default:
                    cacheManager.stop();
                    break;
            }
            log.info("startService => Cachecos manager state: "+cacheManager.getState());

            log.info("startService => Cachecos service started.");

        } else {
            log.info("startService => Manager is not ready to work.");
            log.info("startService => Skipping starting the Cachecos service.");
        }

    }

    private void rebind() throws NamingException {
        InitialContext rootCtx = null;
        try {
            rootCtx = new InitialContext();
            Name fullName = rootCtx.getNameParser("").parse(jndiName);
            NonSerializableFactory.rebind(fullName, cacheManager, true);
        } finally {
            closeCtx(rootCtx);
        }
    }

    private void unbind(String jndiName) {
        InitialContext rootCtx = null;
        try {
            rootCtx = new InitialContext();
            rootCtx.unbind(jndiName);
            NonSerializableFactory.unbind(jndiName);
        } catch (NamingException e) {
            String message = "Failed to unbind Cachecos manager with jndiName: " + jndiName;
            log.warn(message, e);
        } finally {
            closeCtx(rootCtx);
        }
    }

    private void closeCtx(InitialContext context) {
        if (context != null) {
            try {
                context.close();
            } catch (NamingException e) {
                log.warn(e);
            }
        }
    }
}
