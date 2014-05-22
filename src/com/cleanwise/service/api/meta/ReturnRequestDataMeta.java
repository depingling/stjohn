
/* DO NOT EDIT - Generated code from XSL file ValueObject.xsl */

package com.cleanwise.service.api.meta;

/**
 * Title:        ReturnRequestDataMeta
 * Description:  This is a meta class describing the object of database table  CLW_RETURN_REQUEST.
 * Purpose:
 * Copyright:    Copyright (c) 2009
 * Company:      CleanWise, Inc.
 * @author Generated Code from XSL file ValueObjectMeta.xsl
 */

import com.cleanwise.service.api.dao.ReturnRequestDataAccess;
import com.cleanwise.service.api.value.TableObject;
import com.cleanwise.service.api.cachecos.TableObjectFieldMeta;
import com.cleanwise.service.api.cachecos.TableField;

/**
 * <code>ReturnRequestDataMeta</code> is a meta class describing the database table object CLW_RETURN_REQUEST.
 */
public class ReturnRequestDataMeta extends com.cleanwise.service.api.cachecos.TableObjectMeta {

    private static final long serialVersionUID = -1L; //ERROR. No Serial Version Provided
       
    /**
     * Constructor.
     */
    public ReturnRequestDataMeta(TableObject pData) {
        
        super(ReturnRequestDataAccess.CLW_RETURN_REQUEST);

        if (pData != null) {
            TableObjectFieldMeta fm = new TableObjectFieldMeta();
            fm.setField(ReturnRequestDataAccess.RETURN_REQUEST_ID);
            fm.setValue(pData.getFieldValue(ReturnRequestDataAccess.RETURN_REQUEST_ID));
            addField(fm);
        }
    
    }

    /**
     * Constructor.
     */
    public ReturnRequestDataMeta(TableField... fields) {
        
        super(ReturnRequestDataAccess.CLW_RETURN_REQUEST);

        for (TableField field : fields) {
            TableObjectFieldMeta fm = new TableObjectFieldMeta();
            fm.setField(field.getName());
            fm.setValue(field.getValue());
            addField(fm);
        }
    
    }

}
    
