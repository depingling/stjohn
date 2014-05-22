package com.cleanwise.service.api.cachecos;

import org.jboss.system.ServiceMBean;

/**
 * Title:        CacheControlServiceMBean
 * Description:  Interface for CACHECOS service.
 * Purpose:      Provides access to the configuring methods of service.
 * Copyright:    Copyright (c) 2009
 * Company:      CleanWise, Inc.
 * Date:         11.01.2009
 * Time:         13:11:06
 *
 * @author Alexander Chickin, TrinitySoft, Inc.
 */
public interface CacheControlServiceMBean extends ServiceMBean {

    public void setJndiName(String jndiName) throws Exception;

    public String getJndiName();

    public void setManager(String clazz) throws Exception;

    public void setCache(String clazz) throws Exception;

    public void setManagerState(int state);

    public void setCacheObjectLifetime(long time);

    public void setGarbageCollectionInterval(long time);
}
