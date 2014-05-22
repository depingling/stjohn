
/* DO NOT EDIT - Generated code from XSL file ValueObject.xsl */

package com.cleanwise.service.api.meta;

/**
 * Title:        ItemLogDataMeta
 * Description:  This is a meta class describing the object of database table  CLW_ITEM_LOG.
 * Purpose:
 * Copyright:    Copyright (c) 2009
 * Company:      CleanWise, Inc.
 * @author Generated Code from XSL file ValueObjectMeta.xsl
 */

import com.cleanwise.service.api.dao.ItemLogDataAccess;
import com.cleanwise.service.api.value.TableObject;
import com.cleanwise.service.api.cachecos.TableObjectFieldMeta;
import com.cleanwise.service.api.cachecos.TableField;

/**
 * <code>ItemLogDataMeta</code> is a meta class describing the database table object CLW_ITEM_LOG.
 */
public class ItemLogDataMeta extends com.cleanwise.service.api.cachecos.TableObjectMeta {

    private static final long serialVersionUID = -1L; //ERROR. No Serial Version Provided
       
    /**
     * Constructor.
     */
    public ItemLogDataMeta(TableObject pData) {
        
        super(ItemLogDataAccess.CLW_ITEM_LOG);

        if (pData != null) {
            TableObjectFieldMeta fm = new TableObjectFieldMeta();
            fm.setField(ItemLogDataAccess.ITEM_LOG_ID);
            fm.setValue(pData.getFieldValue(ItemLogDataAccess.ITEM_LOG_ID));
            addField(fm);
        }
    
    }

    /**
     * Constructor.
     */
    public ItemLogDataMeta(TableField... fields) {
        
        super(ItemLogDataAccess.CLW_ITEM_LOG);

        for (TableField field : fields) {
            TableObjectFieldMeta fm = new TableObjectFieldMeta();
            fm.setField(field.getName());
            fm.setValue(field.getValue());
            addField(fm);
        }
    
    }

}
    
