
/* DO NOT EDIT - Generated code from XSL file ValueObject.xsl */

package com.cleanwise.service.api.meta;

/**
 * Title:        PreOrderItemDataMeta
 * Description:  This is a meta class describing the object of database table  CLW_PRE_ORDER_ITEM.
 * Purpose:
 * Copyright:    Copyright (c) 2009
 * Company:      CleanWise, Inc.
 * @author Generated Code from XSL file ValueObjectMeta.xsl
 */

import com.cleanwise.service.api.dao.PreOrderItemDataAccess;
import com.cleanwise.service.api.value.TableObject;
import com.cleanwise.service.api.cachecos.TableObjectFieldMeta;
import com.cleanwise.service.api.cachecos.TableField;

/**
 * <code>PreOrderItemDataMeta</code> is a meta class describing the database table object CLW_PRE_ORDER_ITEM.
 */
public class PreOrderItemDataMeta extends com.cleanwise.service.api.cachecos.TableObjectMeta {

    private static final long serialVersionUID = -1L; //ERROR. No Serial Version Provided
       
    /**
     * Constructor.
     */
    public PreOrderItemDataMeta(TableObject pData) {
        
        super(PreOrderItemDataAccess.CLW_PRE_ORDER_ITEM);

        if (pData != null) {
            TableObjectFieldMeta fm = new TableObjectFieldMeta();
            fm.setField(PreOrderItemDataAccess.PRE_ORDER_ITEM_ID);
            fm.setValue(pData.getFieldValue(PreOrderItemDataAccess.PRE_ORDER_ITEM_ID));
            addField(fm);
        }
    
    }

    /**
     * Constructor.
     */
    public PreOrderItemDataMeta(TableField... fields) {
        
        super(PreOrderItemDataAccess.CLW_PRE_ORDER_ITEM);

        for (TableField field : fields) {
            TableObjectFieldMeta fm = new TableObjectFieldMeta();
            fm.setField(field.getName());
            fm.setValue(field.getValue());
            addField(fm);
        }
    
    }

}
    
