package com.cleanwise.service.api.wrapper;

import com.cleanwise.service.api.value.SiteInventoryConfigView;

import java.util.HashMap;

public class SiteInventoryConfigViewWrapper extends SiteInventoryConfigView {

    private SiteInventoryConfigView mSiteInventoryConfigView;

    public SiteInventoryConfigViewWrapper(SiteInventoryConfigView pSiteInventoryConfigView) {
        this.mSiteInventoryConfigView = pSiteInventoryConfigView;
    }

    public SiteInventoryConfigView getSiteInventoryConfigView() {
        return mSiteInventoryConfigView;
    }

    public void setSiteInventoryConfigView(SiteInventoryConfigView mSiteInventoryConfigView) {
        this.mSiteInventoryConfigView = mSiteInventoryConfigView;
    }

    public Integer getParValue(int pPeriod) {
        if (mSiteInventoryConfigView.getParValues() != null) {
            return (Integer) mSiteInventoryConfigView.getParValues().get(pPeriod);
        } else {
            return null;
        }
    }

    public void setParValue(int pPeriod, Integer pValue) {
        if (mSiteInventoryConfigView.getParValues() != null) {
            mSiteInventoryConfigView.getParValues().put(pPeriod, pValue);
        } else {
            mSiteInventoryConfigView.setParValues(new HashMap());
            mSiteInventoryConfigView.getParValues().put(pPeriod, pValue);
        }
    }
}
