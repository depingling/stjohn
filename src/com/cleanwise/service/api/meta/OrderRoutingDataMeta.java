
/* DO NOT EDIT - Generated code from XSL file ValueObject.xsl */

package com.cleanwise.service.api.meta;

/**
 * Title:        OrderRoutingDataMeta
 * Description:  This is a meta class describing the object of database table  CLW_ORDER_ROUTING.
 * Purpose:
 * Copyright:    Copyright (c) 2009
 * Company:      CleanWise, Inc.
 * @author Generated Code from XSL file ValueObjectMeta.xsl
 */

import com.cleanwise.service.api.dao.OrderRoutingDataAccess;
import com.cleanwise.service.api.value.TableObject;
import com.cleanwise.service.api.cachecos.TableObjectFieldMeta;
import com.cleanwise.service.api.cachecos.TableField;

/**
 * <code>OrderRoutingDataMeta</code> is a meta class describing the database table object CLW_ORDER_ROUTING.
 */
public class OrderRoutingDataMeta extends com.cleanwise.service.api.cachecos.TableObjectMeta {

    private static final long serialVersionUID = -1L; //ERROR. No Serial Version Provided
       
    /**
     * Constructor.
     */
    public OrderRoutingDataMeta(TableObject pData) {
        
        super(OrderRoutingDataAccess.CLW_ORDER_ROUTING);

        if (pData != null) {
            TableObjectFieldMeta fm = new TableObjectFieldMeta();
            fm.setField(OrderRoutingDataAccess.ORDER_ROUTING_ID);
            fm.setValue(pData.getFieldValue(OrderRoutingDataAccess.ORDER_ROUTING_ID));
            addField(fm);
        }
    
    }

    /**
     * Constructor.
     */
    public OrderRoutingDataMeta(TableField... fields) {
        
        super(OrderRoutingDataAccess.CLW_ORDER_ROUTING);

        for (TableField field : fields) {
            TableObjectFieldMeta fm = new TableObjectFieldMeta();
            fm.setField(field.getName());
            fm.setValue(field.getValue());
            addField(fm);
        }
    
    }

}
    
