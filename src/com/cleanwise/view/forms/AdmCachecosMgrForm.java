package com.cleanwise.view.forms;

import com.cleanwise.service.api.cachecos.CacheInformation;
import org.apache.struts.action.ActionForm;

/**
 * Title:        AdmCachecosMgrForm
 * Description:  Form bean
 * Purpose:      Holds data
 * Copyright:    Copyright (c) 2009
 * Company:      CleanWise, Inc.
 * Date:         14.01.2009
 * Time:         19:29:21
 *
 * @author Alexander Chickin, TrinitySoft, Inc.
 */
public class AdmCachecosMgrForm extends ActionForm {
    public static String ADM_CACHECOS_MGR_FORM = "ADM_CACHECOS_MGR_FORM";

    private String cacheDescription;
    private CacheInformation cacheInformation;
    private String newGarbageCollectionInterval;
    private String newObjectLifetime;

    public void setCacheDescription(String cacheDescription) {
        this.cacheDescription = cacheDescription;
    }

    public String getCacheDescription() {
        return cacheDescription;
    }

    public void setCacheInformation(CacheInformation cacheInformation) {
        this.cacheInformation = cacheInformation;
    }

    public CacheInformation getCacheInformation() {
        return cacheInformation;
    }

    public String getNewGarbageCollectionInterval() {
        return newGarbageCollectionInterval;
    }

    public void setNewGarbageCollectionInterval(String newGarbageCollectionInterval) {
        this.newGarbageCollectionInterval = newGarbageCollectionInterval;
    }

    public String getNewObjectLifetime() {
        return newObjectLifetime;
    }

    public void setNewObjectLifetime(String newObjectLifetime) {
        this.newObjectLifetime = newObjectLifetime;
    }
}
