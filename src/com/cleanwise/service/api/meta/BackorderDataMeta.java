
/* DO NOT EDIT - Generated code from XSL file ValueObject.xsl */

package com.cleanwise.service.api.meta;

/**
 * Title:        BackorderDataMeta
 * Description:  This is a meta class describing the object of database table  CLW_BACKORDER.
 * Purpose:
 * Copyright:    Copyright (c) 2009
 * Company:      CleanWise, Inc.
 * @author Generated Code from XSL file ValueObjectMeta.xsl
 */

import com.cleanwise.service.api.dao.BackorderDataAccess;
import com.cleanwise.service.api.value.TableObject;
import com.cleanwise.service.api.cachecos.TableObjectFieldMeta;
import com.cleanwise.service.api.cachecos.TableField;

/**
 * <code>BackorderDataMeta</code> is a meta class describing the database table object CLW_BACKORDER.
 */
public class BackorderDataMeta extends com.cleanwise.service.api.cachecos.TableObjectMeta {

    private static final long serialVersionUID = -1L; //ERROR. No Serial Version Provided
       
    /**
     * Constructor.
     */
    public BackorderDataMeta(TableObject pData) {
        
        super(BackorderDataAccess.CLW_BACKORDER);

        if (pData != null) {
            TableObjectFieldMeta fm = new TableObjectFieldMeta();
            fm.setField(BackorderDataAccess.BACKORDER_ID);
            fm.setValue(pData.getFieldValue(BackorderDataAccess.BACKORDER_ID));
            addField(fm);
        }
    
    }

    /**
     * Constructor.
     */
    public BackorderDataMeta(TableField... fields) {
        
        super(BackorderDataAccess.CLW_BACKORDER);

        for (TableField field : fields) {
            TableObjectFieldMeta fm = new TableObjectFieldMeta();
            fm.setField(field.getName());
            fm.setValue(field.getValue());
            addField(fm);
        }
    
    }

}
    
