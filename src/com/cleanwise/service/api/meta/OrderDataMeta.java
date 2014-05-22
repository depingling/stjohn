
/* DO NOT EDIT - Generated code from XSL file ValueObject.xsl */

package com.cleanwise.service.api.meta;

/**
 * Title:        OrderDataMeta
 * Description:  This is a meta class describing the object of database table  CLW_ORDER.
 * Purpose:
 * Copyright:    Copyright (c) 2009
 * Company:      CleanWise, Inc.
 * @author Generated Code from XSL file ValueObjectMeta.xsl
 */

import com.cleanwise.service.api.dao.OrderDataAccess;
import com.cleanwise.service.api.value.TableObject;
import com.cleanwise.service.api.cachecos.TableObjectFieldMeta;
import com.cleanwise.service.api.cachecos.TableField;

/**
 * <code>OrderDataMeta</code> is a meta class describing the database table object CLW_ORDER.
 */
public class OrderDataMeta extends com.cleanwise.service.api.cachecos.TableObjectMeta {

    private static final long serialVersionUID = -1L; //ERROR. No Serial Version Provided
       
    /**
     * Constructor.
     */
    public OrderDataMeta(TableObject pData) {
        
        super(OrderDataAccess.CLW_ORDER);

        if (pData != null) {
            TableObjectFieldMeta fm = new TableObjectFieldMeta();
            fm.setField(OrderDataAccess.ORDER_ID);
            fm.setValue(pData.getFieldValue(OrderDataAccess.ORDER_ID));
            addField(fm);
        }
    
    }

    /**
     * Constructor.
     */
    public OrderDataMeta(TableField... fields) {
        
        super(OrderDataAccess.CLW_ORDER);

        for (TableField field : fields) {
            TableObjectFieldMeta fm = new TableObjectFieldMeta();
            fm.setField(field.getName());
            fm.setValue(field.getValue());
            addField(fm);
        }
    
    }

}
    
