package com.cleanwise.service.api.value;

import com.cleanwise.service.api.framework.*;
/**
This class holds the account to inventory item 
and item to product data. 
**/
public class InventoryItemDataJoin extends ValueObject  {
    
    private ProductData mPd;
    public boolean setProductData(ProductData pd) {
        if ( null == mIid ) return false;
        if (pd.getProductId() == mIid.getItemId() ) {
            mPd = pd;
            return true;
        }
        return false;
    }
    public ProductData getProductData() {
        return mPd;
    }

    private    InventoryItemsData mIid;
    public InventoryItemDataJoin(InventoryItemsData v) {
        setInventoryItemsData (v);
    }
    public void setInventoryItemsData(InventoryItemsData v) {
        mIid = v;
    }
    public InventoryItemsData getInventoryItemsData() {
        return mIid;
    }

}
