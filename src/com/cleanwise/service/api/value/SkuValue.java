package com.cleanwise.service.api.value;

public class SkuValue {

    public static int CATALOG_SKU = 1;
    public static int CLW_SKU = 2;
    public static int MANUFACTURER_SKU = 3;
    public static int DISTRIBUTOR_SKU = 4;
    public static int PRICE_LIST_RANK_1 = 5;
    public static int PRICE_LIST_RANK_2 = 6;

    private int mType;
    private String mValue;

    public SkuValue(String pPrice, int pType) {
        this.mValue= pPrice;
        this.mType = pType;
    }

    public int getType() {
        return mType;
    }

    public void setType(int pType) {
        this.mType = pType;
    }

    public String getValue() {
        return mValue;
    }

    public void setValue(String pValue) {
        this.mValue = pValue;
    }

    public String toString() {
        return "[mValue: "+mValue+", mType: "+mType+"]";
    }
}
