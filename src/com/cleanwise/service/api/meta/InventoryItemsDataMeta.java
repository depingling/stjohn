
/* DO NOT EDIT - Generated code from XSL file ValueObject.xsl */

package com.cleanwise.service.api.meta;

/**
 * Title:        InventoryItemsDataMeta
 * Description:  This is a meta class describing the object of database table  CLW_INVENTORY_ITEMS.
 * Purpose:
 * Copyright:    Copyright (c) 2009
 * Company:      CleanWise, Inc.
 * @author Generated Code from XSL file ValueObjectMeta.xsl
 */

import com.cleanwise.service.api.dao.InventoryItemsDataAccess;
import com.cleanwise.service.api.value.TableObject;
import com.cleanwise.service.api.cachecos.TableObjectFieldMeta;
import com.cleanwise.service.api.cachecos.TableField;

/**
 * <code>InventoryItemsDataMeta</code> is a meta class describing the database table object CLW_INVENTORY_ITEMS.
 */
public class InventoryItemsDataMeta extends com.cleanwise.service.api.cachecos.TableObjectMeta {

    private static final long serialVersionUID = -1L; //ERROR. No Serial Version Provided
       
    /**
     * Constructor.
     */
    public InventoryItemsDataMeta(TableObject pData) {
        
        super(InventoryItemsDataAccess.CLW_INVENTORY_ITEMS);

        if (pData != null) {
            TableObjectFieldMeta fm = new TableObjectFieldMeta();
            fm.setField(InventoryItemsDataAccess.INVENTORY_ITEMS_ID);
            fm.setValue(pData.getFieldValue(InventoryItemsDataAccess.INVENTORY_ITEMS_ID));
            addField(fm);
        }
    
    }

    /**
     * Constructor.
     */
    public InventoryItemsDataMeta(TableField... fields) {
        
        super(InventoryItemsDataAccess.CLW_INVENTORY_ITEMS);

        for (TableField field : fields) {
            TableObjectFieldMeta fm = new TableObjectFieldMeta();
            fm.setField(field.getName());
            fm.setValue(field.getValue());
            addField(fm);
        }
    
    }

}
    
