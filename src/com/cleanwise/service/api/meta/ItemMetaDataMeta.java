
/* DO NOT EDIT - Generated code from XSL file ValueObject.xsl */

package com.cleanwise.service.api.meta;

/**
 * Title:        ItemMetaDataMeta
 * Description:  This is a meta class describing the object of database table  CLW_ITEM_META.
 * Purpose:
 * Copyright:    Copyright (c) 2009
 * Company:      CleanWise, Inc.
 * @author Generated Code from XSL file ValueObjectMeta.xsl
 */

import com.cleanwise.service.api.dao.ItemMetaDataAccess;
import com.cleanwise.service.api.value.TableObject;
import com.cleanwise.service.api.cachecos.TableObjectFieldMeta;
import com.cleanwise.service.api.cachecos.TableField;

/**
 * <code>ItemMetaDataMeta</code> is a meta class describing the database table object CLW_ITEM_META.
 */
public class ItemMetaDataMeta extends com.cleanwise.service.api.cachecos.TableObjectMeta {

    private static final long serialVersionUID = -1L; //ERROR. No Serial Version Provided
       
    /**
     * Constructor.
     */
    public ItemMetaDataMeta(TableObject pData) {
        
        super(ItemMetaDataAccess.CLW_ITEM_META);

        if (pData != null) {
            TableObjectFieldMeta fm = new TableObjectFieldMeta();
            fm.setField(ItemMetaDataAccess.ITEM_META_ID);
            fm.setValue(pData.getFieldValue(ItemMetaDataAccess.ITEM_META_ID));
            addField(fm);
        }
    
    }

    /**
     * Constructor.
     */
    public ItemMetaDataMeta(TableField... fields) {
        
        super(ItemMetaDataAccess.CLW_ITEM_META);

        for (TableField field : fields) {
            TableObjectFieldMeta fm = new TableObjectFieldMeta();
            fm.setField(field.getName());
            fm.setValue(field.getValue());
            addField(fm);
        }
    
    }

}
    
