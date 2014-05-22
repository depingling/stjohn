package com.cleanwise.service.apps.dataexchange.xpedx;


public class ItemReference extends Reference {

    public ItemReference(String pDistributor, String pDistSKU) {
        create(pDistributor, pDistSKU);
    }

    public String getDistributor() {
        return (String) get(0);
    }

    public String getDistSKU() {
        return (String) get(1);
    }
}
