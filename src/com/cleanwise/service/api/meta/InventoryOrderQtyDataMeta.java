
/* DO NOT EDIT - Generated code from XSL file ValueObject.xsl */

package com.cleanwise.service.api.meta;

/**
 * Title:        InventoryOrderQtyDataMeta
 * Description:  This is a meta class describing the object of database table  CLW_INVENTORY_ORDER_QTY.
 * Purpose:
 * Copyright:    Copyright (c) 2009
 * Company:      CleanWise, Inc.
 * @author Generated Code from XSL file ValueObjectMeta.xsl
 */

import com.cleanwise.service.api.dao.InventoryOrderQtyDataAccess;
import com.cleanwise.service.api.value.TableObject;
import com.cleanwise.service.api.cachecos.TableObjectFieldMeta;
import com.cleanwise.service.api.cachecos.TableField;

/**
 * <code>InventoryOrderQtyDataMeta</code> is a meta class describing the database table object CLW_INVENTORY_ORDER_QTY.
 */
public class InventoryOrderQtyDataMeta extends com.cleanwise.service.api.cachecos.TableObjectMeta {

    private static final long serialVersionUID = -1L; //ERROR. No Serial Version Provided
       
    /**
     * Constructor.
     */
    public InventoryOrderQtyDataMeta(TableObject pData) {
        
        super(InventoryOrderQtyDataAccess.CLW_INVENTORY_ORDER_QTY);

        if (pData != null) {
            TableObjectFieldMeta fm = new TableObjectFieldMeta();
            fm.setField(InventoryOrderQtyDataAccess.INVENTORY_ORDER_QTY_ID);
            fm.setValue(pData.getFieldValue(InventoryOrderQtyDataAccess.INVENTORY_ORDER_QTY_ID));
            addField(fm);
        }
    
    }

    /**
     * Constructor.
     */
    public InventoryOrderQtyDataMeta(TableField... fields) {
        
        super(InventoryOrderQtyDataAccess.CLW_INVENTORY_ORDER_QTY);

        for (TableField field : fields) {
            TableObjectFieldMeta fm = new TableObjectFieldMeta();
            fm.setField(field.getName());
            fm.setValue(field.getValue());
            addField(fm);
        }
    
    }

}
    
