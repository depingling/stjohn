
/* DO NOT EDIT - Generated code from XSL file ValueObject.xsl */

package com.cleanwise.service.api.meta;

/**
 * Title:        OrderFreightDataMeta
 * Description:  This is a meta class describing the object of database table  CLW_ORDER_FREIGHT.
 * Purpose:
 * Copyright:    Copyright (c) 2009
 * Company:      CleanWise, Inc.
 * @author Generated Code from XSL file ValueObjectMeta.xsl
 */

import com.cleanwise.service.api.dao.OrderFreightDataAccess;
import com.cleanwise.service.api.value.TableObject;
import com.cleanwise.service.api.cachecos.TableObjectFieldMeta;
import com.cleanwise.service.api.cachecos.TableField;

/**
 * <code>OrderFreightDataMeta</code> is a meta class describing the database table object CLW_ORDER_FREIGHT.
 */
public class OrderFreightDataMeta extends com.cleanwise.service.api.cachecos.TableObjectMeta {

    private static final long serialVersionUID = -1L; //ERROR. No Serial Version Provided
       
    /**
     * Constructor.
     */
    public OrderFreightDataMeta(TableObject pData) {
        
        super(OrderFreightDataAccess.CLW_ORDER_FREIGHT);

        if (pData != null) {
            TableObjectFieldMeta fm = new TableObjectFieldMeta();
            fm.setField(OrderFreightDataAccess.ORDER_FREIGHT_ID);
            fm.setValue(pData.getFieldValue(OrderFreightDataAccess.ORDER_FREIGHT_ID));
            addField(fm);
        }
    
    }

    /**
     * Constructor.
     */
    public OrderFreightDataMeta(TableField... fields) {
        
        super(OrderFreightDataAccess.CLW_ORDER_FREIGHT);

        for (TableField field : fields) {
            TableObjectFieldMeta fm = new TableObjectFieldMeta();
            fm.setField(field.getName());
            fm.setValue(field.getValue());
            addField(fm);
        }
    
    }

}
    
