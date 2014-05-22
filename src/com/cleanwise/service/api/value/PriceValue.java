package com.cleanwise.service.api.value;

import java.math.BigDecimal;


public class PriceValue {

    public interface PRICE_SOURCE_CODE {
        public static final String PRICE_LIST_RANK_1 = "PRICE_LIST_RANK_1";
        public static final String PRICE_LIST_RANK_2 = "PRICE_LIST_RANK_2";
        public static final String CONTRACT = "CONTRACT";
        public static final String META = "META";
    }

    private String mSource;
    private BigDecimal mValue;

    public PriceValue(BigDecimal pPrice, String pSource) {
        this.mValue= pPrice;
        this.mSource= pSource;
    }

    public String getSource() {
        return mSource;
    }

    public void setSource(String pSource) {
        this.mSource = pSource;
    }

    public BigDecimal getValue() {
        return mValue;
    }

    public void setValue(BigDecimal pValue) {
        this.mValue = pValue;
    }

    public String toString() {
        return "[mValue: " + this.mValue + ", mSource: " + mSource + "]";
    }
}
