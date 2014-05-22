
/* DO NOT EDIT - Generated code from XSL file ValueObject.xsl */

package com.cleanwise.service.api.meta;

/**
 * Title:        ItemAssocDataMeta
 * Description:  This is a meta class describing the object of database table  CLW_ITEM_ASSOC.
 * Purpose:
 * Copyright:    Copyright (c) 2009
 * Company:      CleanWise, Inc.
 * @author Generated Code from XSL file ValueObjectMeta.xsl
 */

import com.cleanwise.service.api.dao.ItemAssocDataAccess;
import com.cleanwise.service.api.value.TableObject;
import com.cleanwise.service.api.cachecos.TableObjectFieldMeta;
import com.cleanwise.service.api.cachecos.TableField;

/**
 * <code>ItemAssocDataMeta</code> is a meta class describing the database table object CLW_ITEM_ASSOC.
 */
public class ItemAssocDataMeta extends com.cleanwise.service.api.cachecos.TableObjectMeta {

    private static final long serialVersionUID = -1L; //ERROR. No Serial Version Provided
       
    /**
     * Constructor.
     */
    public ItemAssocDataMeta(TableObject pData) {
        
        super(ItemAssocDataAccess.CLW_ITEM_ASSOC);

        if (pData != null) {
            TableObjectFieldMeta fm = new TableObjectFieldMeta();
            fm.setField(ItemAssocDataAccess.ITEM_ASSOC_ID);
            fm.setValue(pData.getFieldValue(ItemAssocDataAccess.ITEM_ASSOC_ID));
            addField(fm);
        }
    
    }

    /**
     * Constructor.
     */
    public ItemAssocDataMeta(TableField... fields) {
        
        super(ItemAssocDataAccess.CLW_ITEM_ASSOC);

        for (TableField field : fields) {
            TableObjectFieldMeta fm = new TableObjectFieldMeta();
            fm.setField(field.getName());
            fm.setValue(field.getValue());
            addField(fm);
        }
    
    }

}
    
