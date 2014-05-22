
/* DO NOT EDIT - Generated code from XSL file ValueObject.xsl */

package com.cleanwise.service.api.meta;

/**
 * Title:        OrderAddressDataMeta
 * Description:  This is a meta class describing the object of database table  CLW_ORDER_ADDRESS.
 * Purpose:
 * Copyright:    Copyright (c) 2009
 * Company:      CleanWise, Inc.
 * @author Generated Code from XSL file ValueObjectMeta.xsl
 */

import com.cleanwise.service.api.dao.OrderAddressDataAccess;
import com.cleanwise.service.api.value.TableObject;
import com.cleanwise.service.api.cachecos.TableObjectFieldMeta;
import com.cleanwise.service.api.cachecos.TableField;

/**
 * <code>OrderAddressDataMeta</code> is a meta class describing the database table object CLW_ORDER_ADDRESS.
 */
public class OrderAddressDataMeta extends com.cleanwise.service.api.cachecos.TableObjectMeta {

    private static final long serialVersionUID = -1L; //ERROR. No Serial Version Provided
       
    /**
     * Constructor.
     */
    public OrderAddressDataMeta(TableObject pData) {
        
        super(OrderAddressDataAccess.CLW_ORDER_ADDRESS);

        if (pData != null) {
            TableObjectFieldMeta fm = new TableObjectFieldMeta();
            fm.setField(OrderAddressDataAccess.ORDER_ADDRESS_ID);
            fm.setValue(pData.getFieldValue(OrderAddressDataAccess.ORDER_ADDRESS_ID));
            addField(fm);
        }
    
    }

    /**
     * Constructor.
     */
    public OrderAddressDataMeta(TableField... fields) {
        
        super(OrderAddressDataAccess.CLW_ORDER_ADDRESS);

        for (TableField field : fields) {
            TableObjectFieldMeta fm = new TableObjectFieldMeta();
            fm.setField(field.getName());
            fm.setValue(field.getValue());
            addField(fm);
        }
    
    }

}
    
