package com.cleanwise.service.api.value;

import java.io.Serializable;
import java.util.List;

/**
 * Title:       InventoryLevelSearchCriteria
 * Description:  search criteria
 * Copyright:    Copyright (c) 2009
 * Company:      CleanWise, Inc.
 * Date:         22.10.2009
 * Time:         9:52:25
 *
 * @author Alexander Chickin, TrinitySoft, Inc.
 */
public class InventoryLevelSearchCriteria implements Serializable {

    private List mSiteIds;
    private Integer mNumPeriods;
    private IdVector mItems;

    public IdVector getItems() {
        return mItems;
    }

    public void setItems(IdVector mItems) {
        this.mItems = mItems;
    }

    public Integer getNumPeriods() {
        return mNumPeriods;
    }

    public void setNumPeriods(Integer mNumPeriods) {
        this.mNumPeriods = mNumPeriods;
    }

    public List getSiteIds() {
        return mSiteIds;
    }

    public void setSiteIds(List pSiteIds) {
        this.mSiteIds = pSiteIds;
    }

}
