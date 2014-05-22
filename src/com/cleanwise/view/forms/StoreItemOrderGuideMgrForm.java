/*
 * StoreItemOrderGuideMgrForm.java
 *
 * Created on September, 2008
 */

package com.cleanwise.view.forms;

import com.cleanwise.service.api.value.OrderGuideDescDataVector;


public class StoreItemOrderGuideMgrForm extends StoreItemCatalogMgrForm {

    private OrderGuideDescDataVector _orderGuideFilter = new OrderGuideDescDataVector();

    public OrderGuideDescDataVector getOrderGuideFilter() {
        return _orderGuideFilter;
    }

    public void setOrderGuideFilter(OrderGuideDescDataVector orderGuideFilter) {
        _orderGuideFilter = orderGuideFilter;
    }

}
