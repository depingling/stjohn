
/* DO NOT EDIT - Generated code from XSL file ValueObject.xsl */

package com.cleanwise.service.api.meta;

/**
 * Title:        DispatchDataMeta
 * Description:  This is a meta class describing the object of database table  CLW_DISPATCH.
 * Purpose:
 * Copyright:    Copyright (c) 2009
 * Company:      CleanWise, Inc.
 * @author Generated Code from XSL file ValueObjectMeta.xsl
 */

import com.cleanwise.service.api.dao.DispatchDataAccess;
import com.cleanwise.service.api.value.TableObject;
import com.cleanwise.service.api.cachecos.TableObjectFieldMeta;
import com.cleanwise.service.api.cachecos.TableField;

/**
 * <code>DispatchDataMeta</code> is a meta class describing the database table object CLW_DISPATCH.
 */
public class DispatchDataMeta extends com.cleanwise.service.api.cachecos.TableObjectMeta {

    private static final long serialVersionUID = -1L; //ERROR. No Serial Version Provided
       
    /**
     * Constructor.
     */
    public DispatchDataMeta(TableObject pData) {
        
        super(DispatchDataAccess.CLW_DISPATCH);

        if (pData != null) {
            TableObjectFieldMeta fm = new TableObjectFieldMeta();
            fm.setField(DispatchDataAccess.DISPATCH_ID);
            fm.setValue(pData.getFieldValue(DispatchDataAccess.DISPATCH_ID));
            addField(fm);
        }
    
    }

    /**
     * Constructor.
     */
    public DispatchDataMeta(TableField... fields) {
        
        super(DispatchDataAccess.CLW_DISPATCH);

        for (TableField field : fields) {
            TableObjectFieldMeta fm = new TableObjectFieldMeta();
            fm.setField(field.getName());
            fm.setValue(field.getValue());
            addField(fm);
        }
    
    }

}
    
