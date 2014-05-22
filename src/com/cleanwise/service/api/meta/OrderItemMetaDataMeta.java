
/* DO NOT EDIT - Generated code from XSL file ValueObject.xsl */

package com.cleanwise.service.api.meta;

/**
 * Title:        OrderItemMetaDataMeta
 * Description:  This is a meta class describing the object of database table  CLW_ORDER_ITEM_META.
 * Purpose:
 * Copyright:    Copyright (c) 2009
 * Company:      CleanWise, Inc.
 * @author Generated Code from XSL file ValueObjectMeta.xsl
 */

import com.cleanwise.service.api.dao.OrderItemMetaDataAccess;
import com.cleanwise.service.api.value.TableObject;
import com.cleanwise.service.api.cachecos.TableObjectFieldMeta;
import com.cleanwise.service.api.cachecos.TableField;

/**
 * <code>OrderItemMetaDataMeta</code> is a meta class describing the database table object CLW_ORDER_ITEM_META.
 */
public class OrderItemMetaDataMeta extends com.cleanwise.service.api.cachecos.TableObjectMeta {

    private static final long serialVersionUID = -1L; //ERROR. No Serial Version Provided
       
    /**
     * Constructor.
     */
    public OrderItemMetaDataMeta(TableObject pData) {
        
        super(OrderItemMetaDataAccess.CLW_ORDER_ITEM_META);

        if (pData != null) {
            TableObjectFieldMeta fm = new TableObjectFieldMeta();
            fm.setField(OrderItemMetaDataAccess.ORDER_ITEM_META_ID);
            fm.setValue(pData.getFieldValue(OrderItemMetaDataAccess.ORDER_ITEM_META_ID));
            addField(fm);
        }
    
    }

    /**
     * Constructor.
     */
    public OrderItemMetaDataMeta(TableField... fields) {
        
        super(OrderItemMetaDataAccess.CLW_ORDER_ITEM_META);

        for (TableField field : fields) {
            TableObjectFieldMeta fm = new TableObjectFieldMeta();
            fm.setField(field.getName());
            fm.setValue(field.getValue());
            addField(fm);
        }
    
    }

}
    
