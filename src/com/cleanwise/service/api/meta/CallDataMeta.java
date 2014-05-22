
/* DO NOT EDIT - Generated code from XSL file ValueObject.xsl */

package com.cleanwise.service.api.meta;

/**
 * Title:        CallDataMeta
 * Description:  This is a meta class describing the object of database table  CLW_CALL.
 * Purpose:
 * Copyright:    Copyright (c) 2009
 * Company:      CleanWise, Inc.
 * @author Generated Code from XSL file ValueObjectMeta.xsl
 */

import com.cleanwise.service.api.dao.CallDataAccess;
import com.cleanwise.service.api.value.TableObject;
import com.cleanwise.service.api.cachecos.TableObjectFieldMeta;
import com.cleanwise.service.api.cachecos.TableField;

/**
 * <code>CallDataMeta</code> is a meta class describing the database table object CLW_CALL.
 */
public class CallDataMeta extends com.cleanwise.service.api.cachecos.TableObjectMeta {

    private static final long serialVersionUID = -1L; //ERROR. No Serial Version Provided
       
    /**
     * Constructor.
     */
    public CallDataMeta(TableObject pData) {
        
        super(CallDataAccess.CLW_CALL);

        if (pData != null) {
            TableObjectFieldMeta fm = new TableObjectFieldMeta();
            fm.setField(CallDataAccess.CALL_ID);
            fm.setValue(pData.getFieldValue(CallDataAccess.CALL_ID));
            addField(fm);
        }
    
    }

    /**
     * Constructor.
     */
    public CallDataMeta(TableField... fields) {
        
        super(CallDataAccess.CLW_CALL);

        for (TableField field : fields) {
            TableObjectFieldMeta fm = new TableObjectFieldMeta();
            fm.setField(field.getName());
            fm.setValue(field.getValue());
            addField(fm);
        }
    
    }

}
    
