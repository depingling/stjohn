
/* DO NOT EDIT - Generated code from XSL file ValueObject.xsl */

package com.cleanwise.service.api.meta;

/**
 * Title:        ReturnRequestDetailDataMeta
 * Description:  This is a meta class describing the object of database table  CLW_RETURN_REQUEST_DETAIL.
 * Purpose:
 * Copyright:    Copyright (c) 2009
 * Company:      CleanWise, Inc.
 * @author Generated Code from XSL file ValueObjectMeta.xsl
 */

import com.cleanwise.service.api.dao.ReturnRequestDetailDataAccess;
import com.cleanwise.service.api.value.TableObject;
import com.cleanwise.service.api.cachecos.TableObjectFieldMeta;
import com.cleanwise.service.api.cachecos.TableField;

/**
 * <code>ReturnRequestDetailDataMeta</code> is a meta class describing the database table object CLW_RETURN_REQUEST_DETAIL.
 */
public class ReturnRequestDetailDataMeta extends com.cleanwise.service.api.cachecos.TableObjectMeta {

    private static final long serialVersionUID = -1L; //ERROR. No Serial Version Provided
       
    /**
     * Constructor.
     */
    public ReturnRequestDetailDataMeta(TableObject pData) {
        
        super(ReturnRequestDetailDataAccess.CLW_RETURN_REQUEST_DETAIL);

        if (pData != null) {
            TableObjectFieldMeta fm = new TableObjectFieldMeta();
            fm.setField(ReturnRequestDetailDataAccess.RETURN_REQUEST_DETAIL_ID);
            fm.setValue(pData.getFieldValue(ReturnRequestDetailDataAccess.RETURN_REQUEST_DETAIL_ID));
            addField(fm);
        }
    
    }

    /**
     * Constructor.
     */
    public ReturnRequestDetailDataMeta(TableField... fields) {
        
        super(ReturnRequestDetailDataAccess.CLW_RETURN_REQUEST_DETAIL);

        for (TableField field : fields) {
            TableObjectFieldMeta fm = new TableObjectFieldMeta();
            fm.setField(field.getName());
            fm.setValue(field.getValue());
            addField(fm);
        }
    
    }

}
    
