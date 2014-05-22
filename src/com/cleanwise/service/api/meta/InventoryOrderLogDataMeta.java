
/* DO NOT EDIT - Generated code from XSL file ValueObject.xsl */

package com.cleanwise.service.api.meta;

/**
 * Title:        InventoryOrderLogDataMeta
 * Description:  This is a meta class describing the object of database table  CLW_INVENTORY_ORDER_LOG.
 * Purpose:
 * Copyright:    Copyright (c) 2009
 * Company:      CleanWise, Inc.
 * @author Generated Code from XSL file ValueObjectMeta.xsl
 */

import com.cleanwise.service.api.dao.InventoryOrderLogDataAccess;
import com.cleanwise.service.api.value.TableObject;
import com.cleanwise.service.api.cachecos.TableObjectFieldMeta;
import com.cleanwise.service.api.cachecos.TableField;

/**
 * <code>InventoryOrderLogDataMeta</code> is a meta class describing the database table object CLW_INVENTORY_ORDER_LOG.
 */
public class InventoryOrderLogDataMeta extends com.cleanwise.service.api.cachecos.TableObjectMeta {

    private static final long serialVersionUID = -1L; //ERROR. No Serial Version Provided
       
    /**
     * Constructor.
     */
    public InventoryOrderLogDataMeta(TableObject pData) {
        
        super(InventoryOrderLogDataAccess.CLW_INVENTORY_ORDER_LOG);

        if (pData != null) {
            TableObjectFieldMeta fm = new TableObjectFieldMeta();
            fm.setField(InventoryOrderLogDataAccess.INVENTORY_ORDER_LOG_ID);
            fm.setValue(pData.getFieldValue(InventoryOrderLogDataAccess.INVENTORY_ORDER_LOG_ID));
            addField(fm);
        }
    
    }

    /**
     * Constructor.
     */
    public InventoryOrderLogDataMeta(TableField... fields) {
        
        super(InventoryOrderLogDataAccess.CLW_INVENTORY_ORDER_LOG);

        for (TableField field : fields) {
            TableObjectFieldMeta fm = new TableObjectFieldMeta();
            fm.setField(field.getName());
            fm.setValue(field.getValue());
            addField(fm);
        }
    
    }

}
    
