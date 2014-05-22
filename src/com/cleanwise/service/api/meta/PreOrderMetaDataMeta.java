
/* DO NOT EDIT - Generated code from XSL file ValueObject.xsl */

package com.cleanwise.service.api.meta;

/**
 * Title:        PreOrderMetaDataMeta
 * Description:  This is a meta class describing the object of database table  CLW_PRE_ORDER_META.
 * Purpose:
 * Copyright:    Copyright (c) 2009
 * Company:      CleanWise, Inc.
 * @author Generated Code from XSL file ValueObjectMeta.xsl
 */

import com.cleanwise.service.api.dao.PreOrderMetaDataAccess;
import com.cleanwise.service.api.value.TableObject;
import com.cleanwise.service.api.cachecos.TableObjectFieldMeta;
import com.cleanwise.service.api.cachecos.TableField;

/**
 * <code>PreOrderMetaDataMeta</code> is a meta class describing the database table object CLW_PRE_ORDER_META.
 */
public class PreOrderMetaDataMeta extends com.cleanwise.service.api.cachecos.TableObjectMeta {

    private static final long serialVersionUID = -1L; //ERROR. No Serial Version Provided
       
    /**
     * Constructor.
     */
    public PreOrderMetaDataMeta(TableObject pData) {
        
        super(PreOrderMetaDataAccess.CLW_PRE_ORDER_META);

        if (pData != null) {
            TableObjectFieldMeta fm = new TableObjectFieldMeta();
            fm.setField(PreOrderMetaDataAccess.PRE_ORDER_META_ID);
            fm.setValue(pData.getFieldValue(PreOrderMetaDataAccess.PRE_ORDER_META_ID));
            addField(fm);
        }
    
    }

    /**
     * Constructor.
     */
    public PreOrderMetaDataMeta(TableField... fields) {
        
        super(PreOrderMetaDataAccess.CLW_PRE_ORDER_META);

        for (TableField field : fields) {
            TableObjectFieldMeta fm = new TableObjectFieldMeta();
            fm.setField(field.getName());
            fm.setValue(field.getValue());
            addField(fm);
        }
    
    }

}
    
