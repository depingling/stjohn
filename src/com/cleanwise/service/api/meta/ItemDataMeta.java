
/* DO NOT EDIT - Generated code from XSL file ValueObject.xsl */

package com.cleanwise.service.api.meta;

/**
 * Title:        ItemDataMeta
 * Description:  This is a meta class describing the object of database table  CLW_ITEM.
 * Purpose:
 * Copyright:    Copyright (c) 2009
 * Company:      CleanWise, Inc.
 * @author Generated Code from XSL file ValueObjectMeta.xsl
 */

import com.cleanwise.service.api.dao.ItemDataAccess;
import com.cleanwise.service.api.value.TableObject;
import com.cleanwise.service.api.cachecos.TableObjectFieldMeta;
import com.cleanwise.service.api.cachecos.TableField;

/**
 * <code>ItemDataMeta</code> is a meta class describing the database table object CLW_ITEM.
 */
public class ItemDataMeta extends com.cleanwise.service.api.cachecos.TableObjectMeta {

    private static final long serialVersionUID = -1L; //ERROR. No Serial Version Provided
       
    /**
     * Constructor.
     */
    public ItemDataMeta(TableObject pData) {
        
        super(ItemDataAccess.CLW_ITEM);

        if (pData != null) {
            TableObjectFieldMeta fm = new TableObjectFieldMeta();
            fm.setField(ItemDataAccess.ITEM_ID);
            fm.setValue(pData.getFieldValue(ItemDataAccess.ITEM_ID));
            addField(fm);
        }
    
    }

    /**
     * Constructor.
     */
    public ItemDataMeta(TableField... fields) {
        
        super(ItemDataAccess.CLW_ITEM);

        for (TableField field : fields) {
            TableObjectFieldMeta fm = new TableObjectFieldMeta();
            fm.setField(field.getName());
            fm.setValue(field.getValue());
            addField(fm);
        }
    
    }

}
    
