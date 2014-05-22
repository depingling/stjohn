
/* DO NOT EDIT - Generated code from XSL file ValueObject.xsl */

package com.cleanwise.service.api.meta;

/**
 * Title:        InventoryLevelDataMeta
 * Description:  This is a meta class describing the object of database table  CLW_INVENTORY_LEVEL.
 * Purpose:
 * Copyright:    Copyright (c) 2009
 * Company:      CleanWise, Inc.
 * @author Generated Code from XSL file ValueObjectMeta.xsl
 */

import com.cleanwise.service.api.dao.InventoryLevelDataAccess;
import com.cleanwise.service.api.value.TableObject;
import com.cleanwise.service.api.cachecos.TableObjectFieldMeta;
import com.cleanwise.service.api.cachecos.TableField;

/**
 * <code>InventoryLevelDataMeta</code> is a meta class describing the database table object CLW_INVENTORY_LEVEL.
 */
public class InventoryLevelDataMeta extends com.cleanwise.service.api.cachecos.TableObjectMeta {

    private static final long serialVersionUID = -1L; //ERROR. No Serial Version Provided
       
    /**
     * Constructor.
     */
    public InventoryLevelDataMeta(TableObject pData) {
        
        super(InventoryLevelDataAccess.CLW_INVENTORY_LEVEL);

        if (pData != null) {
            TableObjectFieldMeta fm = new TableObjectFieldMeta();
            fm.setField(InventoryLevelDataAccess.INVENTORY_LEVEL_ID);
            fm.setValue(pData.getFieldValue(InventoryLevelDataAccess.INVENTORY_LEVEL_ID));
            addField(fm);
        }
    
    }

    /**
     * Constructor.
     */
    public InventoryLevelDataMeta(TableField... fields) {
        
        super(InventoryLevelDataAccess.CLW_INVENTORY_LEVEL);

        for (TableField field : fields) {
            TableObjectFieldMeta fm = new TableObjectFieldMeta();
            fm.setField(field.getName());
            fm.setValue(field.getValue());
            addField(fm);
        }
    
    }

}
    
