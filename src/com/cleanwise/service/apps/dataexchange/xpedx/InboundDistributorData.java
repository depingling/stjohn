package com.cleanwise.service.apps.dataexchange.xpedx;

import java.io.Serializable;

public class InboundDistributorData implements Serializable {

    private String mDistributorName;

    public String getDistributorName() {
        return mDistributorName;
    }

    public void setDistributorName(String pDistributorName) {
        this.mDistributorName = pDistributorName;
    }

}

