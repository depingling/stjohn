package com.cleanwise.service.api.util;

import com.cleanwise.service.api.value.PriceValue;
import com.cleanwise.service.api.value.SkuValue;

public interface ProductBundle {

    public PriceValue getPriceValue(int pProductId);

    public SkuValue getSkuValue(int pProductId);

}
