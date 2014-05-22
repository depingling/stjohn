
/* DO NOT EDIT - Generated code from XSL file ValueObject.xsl */

package com.cleanwise.service.api.meta;

/**
 * Title:        PreOrderDataMeta
 * Description:  This is a meta class describing the object of database table  CLW_PRE_ORDER.
 * Purpose:
 * Copyright:    Copyright (c) 2009
 * Company:      CleanWise, Inc.
 * @author Generated Code from XSL file ValueObjectMeta.xsl
 */

import com.cleanwise.service.api.dao.PreOrderDataAccess;
import com.cleanwise.service.api.value.TableObject;
import com.cleanwise.service.api.cachecos.TableObjectFieldMeta;
import com.cleanwise.service.api.cachecos.TableField;

/**
 * <code>PreOrderDataMeta</code> is a meta class describing the database table object CLW_PRE_ORDER.
 */
public class PreOrderDataMeta extends com.cleanwise.service.api.cachecos.TableObjectMeta {

    private static final long serialVersionUID = -1L; //ERROR. No Serial Version Provided
       
    /**
     * Constructor.
     */
    public PreOrderDataMeta(TableObject pData) {
        
        super(PreOrderDataAccess.CLW_PRE_ORDER);

        if (pData != null) {
            TableObjectFieldMeta fm = new TableObjectFieldMeta();
            fm.setField(PreOrderDataAccess.PRE_ORDER_ID);
            fm.setValue(pData.getFieldValue(PreOrderDataAccess.PRE_ORDER_ID));
            addField(fm);
        }
    
    }

    /**
     * Constructor.
     */
    public PreOrderDataMeta(TableField... fields) {
        
        super(PreOrderDataAccess.CLW_PRE_ORDER);

        for (TableField field : fields) {
            TableObjectFieldMeta fm = new TableObjectFieldMeta();
            fm.setField(field.getName());
            fm.setValue(field.getValue());
            addField(fm);
        }
    
    }

}
    
