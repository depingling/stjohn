
/* DO NOT EDIT - Generated code from XSL file ValueObject.xsl */

package com.cleanwise.service.api.meta;

/**
 * Title:        PreOrderPropertyDataMeta
 * Description:  This is a meta class describing the object of database table  CLW_PRE_ORDER_PROPERTY.
 * Purpose:
 * Copyright:    Copyright (c) 2009
 * Company:      CleanWise, Inc.
 * @author Generated Code from XSL file ValueObjectMeta.xsl
 */

import com.cleanwise.service.api.dao.PreOrderPropertyDataAccess;
import com.cleanwise.service.api.value.TableObject;
import com.cleanwise.service.api.cachecos.TableObjectFieldMeta;
import com.cleanwise.service.api.cachecos.TableField;

/**
 * <code>PreOrderPropertyDataMeta</code> is a meta class describing the database table object CLW_PRE_ORDER_PROPERTY.
 */
public class PreOrderPropertyDataMeta extends com.cleanwise.service.api.cachecos.TableObjectMeta {

    private static final long serialVersionUID = -1L; //ERROR. No Serial Version Provided
       
    /**
     * Constructor.
     */
    public PreOrderPropertyDataMeta(TableObject pData) {
        
        super(PreOrderPropertyDataAccess.CLW_PRE_ORDER_PROPERTY);

        if (pData != null) {
            TableObjectFieldMeta fm = new TableObjectFieldMeta();
            fm.setField(PreOrderPropertyDataAccess.PRE_ORDER_PROPERTY_ID);
            fm.setValue(pData.getFieldValue(PreOrderPropertyDataAccess.PRE_ORDER_PROPERTY_ID));
            addField(fm);
        }
    
    }

    /**
     * Constructor.
     */
    public PreOrderPropertyDataMeta(TableField... fields) {
        
        super(PreOrderPropertyDataAccess.CLW_PRE_ORDER_PROPERTY);

        for (TableField field : fields) {
            TableObjectFieldMeta fm = new TableObjectFieldMeta();
            fm.setField(field.getName());
            fm.setValue(field.getValue());
            addField(fm);
        }
    
    }

}
    
