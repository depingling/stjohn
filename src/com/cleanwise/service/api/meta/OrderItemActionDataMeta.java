
/* DO NOT EDIT - Generated code from XSL file ValueObject.xsl */

package com.cleanwise.service.api.meta;

/**
 * Title:        OrderItemActionDataMeta
 * Description:  This is a meta class describing the object of database table  CLW_ORDER_ITEM_ACTION.
 * Purpose:
 * Copyright:    Copyright (c) 2009
 * Company:      CleanWise, Inc.
 * @author Generated Code from XSL file ValueObjectMeta.xsl
 */

import com.cleanwise.service.api.dao.OrderItemActionDataAccess;
import com.cleanwise.service.api.value.TableObject;
import com.cleanwise.service.api.cachecos.TableObjectFieldMeta;
import com.cleanwise.service.api.cachecos.TableField;

/**
 * <code>OrderItemActionDataMeta</code> is a meta class describing the database table object CLW_ORDER_ITEM_ACTION.
 */
public class OrderItemActionDataMeta extends com.cleanwise.service.api.cachecos.TableObjectMeta {

    private static final long serialVersionUID = -1L; //ERROR. No Serial Version Provided
       
    /**
     * Constructor.
     */
    public OrderItemActionDataMeta(TableObject pData) {
        
        super(OrderItemActionDataAccess.CLW_ORDER_ITEM_ACTION);

        if (pData != null) {
            TableObjectFieldMeta fm = new TableObjectFieldMeta();
            fm.setField(OrderItemActionDataAccess.ORDER_ITEM_ACTION_ID);
            fm.setValue(pData.getFieldValue(OrderItemActionDataAccess.ORDER_ITEM_ACTION_ID));
            addField(fm);
        }
    
    }

    /**
     * Constructor.
     */
    public OrderItemActionDataMeta(TableField... fields) {
        
        super(OrderItemActionDataAccess.CLW_ORDER_ITEM_ACTION);

        for (TableField field : fields) {
            TableObjectFieldMeta fm = new TableObjectFieldMeta();
            fm.setField(field.getName());
            fm.setValue(field.getValue());
            addField(fm);
        }
    
    }

}
    
