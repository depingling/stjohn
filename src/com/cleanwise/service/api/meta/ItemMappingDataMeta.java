
/* DO NOT EDIT - Generated code from XSL file ValueObject.xsl */

package com.cleanwise.service.api.meta;

/**
 * Title:        ItemMappingDataMeta
 * Description:  This is a meta class describing the object of database table  CLW_ITEM_MAPPING.
 * Purpose:
 * Copyright:    Copyright (c) 2009
 * Company:      CleanWise, Inc.
 * @author Generated Code from XSL file ValueObjectMeta.xsl
 */

import com.cleanwise.service.api.dao.ItemMappingDataAccess;
import com.cleanwise.service.api.value.TableObject;
import com.cleanwise.service.api.cachecos.TableObjectFieldMeta;
import com.cleanwise.service.api.cachecos.TableField;

/**
 * <code>ItemMappingDataMeta</code> is a meta class describing the database table object CLW_ITEM_MAPPING.
 */
public class ItemMappingDataMeta extends com.cleanwise.service.api.cachecos.TableObjectMeta {

    private static final long serialVersionUID = -1L; //ERROR. No Serial Version Provided
       
    /**
     * Constructor.
     */
    public ItemMappingDataMeta(TableObject pData) {
        
        super(ItemMappingDataAccess.CLW_ITEM_MAPPING);

        if (pData != null) {
            TableObjectFieldMeta fm = new TableObjectFieldMeta();
            fm.setField(ItemMappingDataAccess.ITEM_MAPPING_ID);
            fm.setValue(pData.getFieldValue(ItemMappingDataAccess.ITEM_MAPPING_ID));
            addField(fm);
        }
    
    }

    /**
     * Constructor.
     */
    public ItemMappingDataMeta(TableField... fields) {
        
        super(ItemMappingDataAccess.CLW_ITEM_MAPPING);

        for (TableField field : fields) {
            TableObjectFieldMeta fm = new TableObjectFieldMeta();
            fm.setField(field.getName());
            fm.setValue(field.getValue());
            addField(fm);
        }
    
    }

}
    
