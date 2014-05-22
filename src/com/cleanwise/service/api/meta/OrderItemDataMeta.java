
/* DO NOT EDIT - Generated code from XSL file ValueObject.xsl */

package com.cleanwise.service.api.meta;

/**
 * Title:        OrderItemDataMeta
 * Description:  This is a meta class describing the object of database table  CLW_ORDER_ITEM.
 * Purpose:
 * Copyright:    Copyright (c) 2009
 * Company:      CleanWise, Inc.
 * @author Generated Code from XSL file ValueObjectMeta.xsl
 */

import com.cleanwise.service.api.dao.OrderItemDataAccess;
import com.cleanwise.service.api.value.TableObject;
import com.cleanwise.service.api.cachecos.TableObjectFieldMeta;
import com.cleanwise.service.api.cachecos.TableField;

/**
 * <code>OrderItemDataMeta</code> is a meta class describing the database table object CLW_ORDER_ITEM.
 */
public class OrderItemDataMeta extends com.cleanwise.service.api.cachecos.TableObjectMeta {

    private static final long serialVersionUID = -1L; //ERROR. No Serial Version Provided
       
    /**
     * Constructor.
     */
    public OrderItemDataMeta(TableObject pData) {
        
        super(OrderItemDataAccess.CLW_ORDER_ITEM);

        if (pData != null) {
            TableObjectFieldMeta fm = new TableObjectFieldMeta();
            fm.setField(OrderItemDataAccess.ORDER_ITEM_ID);
            fm.setValue(pData.getFieldValue(OrderItemDataAccess.ORDER_ITEM_ID));
            addField(fm);
        }
    
    }

    /**
     * Constructor.
     */
    public OrderItemDataMeta(TableField... fields) {
        
        super(OrderItemDataAccess.CLW_ORDER_ITEM);

        for (TableField field : fields) {
            TableObjectFieldMeta fm = new TableObjectFieldMeta();
            fm.setField(field.getName());
            fm.setValue(field.getValue());
            addField(fm);
        }
    
    }

}
    
