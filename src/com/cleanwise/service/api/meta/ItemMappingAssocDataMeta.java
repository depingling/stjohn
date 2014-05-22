
/* DO NOT EDIT - Generated code from XSL file ValueObject.xsl */

package com.cleanwise.service.api.meta;

/**
 * Title:        ItemMappingAssocDataMeta
 * Description:  This is a meta class describing the object of database table  CLW_ITEM_MAPPING_ASSOC.
 * Purpose:
 * Copyright:    Copyright (c) 2009
 * Company:      CleanWise, Inc.
 * @author Generated Code from XSL file ValueObjectMeta.xsl
 */

import com.cleanwise.service.api.dao.ItemMappingAssocDataAccess;
import com.cleanwise.service.api.value.TableObject;
import com.cleanwise.service.api.cachecos.TableObjectFieldMeta;
import com.cleanwise.service.api.cachecos.TableField;

/**
 * <code>ItemMappingAssocDataMeta</code> is a meta class describing the database table object CLW_ITEM_MAPPING_ASSOC.
 */
public class ItemMappingAssocDataMeta extends com.cleanwise.service.api.cachecos.TableObjectMeta {

    private static final long serialVersionUID = -1L; //ERROR. No Serial Version Provided
       
    /**
     * Constructor.
     */
    public ItemMappingAssocDataMeta(TableObject pData) {
        
        super(ItemMappingAssocDataAccess.CLW_ITEM_MAPPING_ASSOC);

        if (pData != null) {
            TableObjectFieldMeta fm = new TableObjectFieldMeta();
            fm.setField(ItemMappingAssocDataAccess.ITEM_MAPPING_ASSOC_ID);
            fm.setValue(pData.getFieldValue(ItemMappingAssocDataAccess.ITEM_MAPPING_ASSOC_ID));
            addField(fm);
        }
    
    }

    /**
     * Constructor.
     */
    public ItemMappingAssocDataMeta(TableField... fields) {
        
        super(ItemMappingAssocDataAccess.CLW_ITEM_MAPPING_ASSOC);

        for (TableField field : fields) {
            TableObjectFieldMeta fm = new TableObjectFieldMeta();
            fm.setField(field.getName());
            fm.setValue(field.getValue());
            addField(fm);
        }
    
    }

}
    
