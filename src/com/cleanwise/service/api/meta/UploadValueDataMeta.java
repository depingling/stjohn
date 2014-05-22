
/* DO NOT EDIT - Generated code from XSL file ValueObject.xsl */

package com.cleanwise.service.api.meta;

/**
 * Title:        UploadValueDataMeta
 * Description:  This is a meta class describing the object of database table  CLW_UPLOAD_VALUE.
 * Purpose:
 * Copyright:    Copyright (c) 2009
 * Company:      CleanWise, Inc.
 * @author Generated Code from XSL file ValueObjectMeta.xsl
 */

import com.cleanwise.service.api.dao.UploadValueDataAccess;
import com.cleanwise.service.api.value.TableObject;
import com.cleanwise.service.api.cachecos.TableObjectFieldMeta;
import com.cleanwise.service.api.cachecos.TableField;

/**
 * <code>UploadValueDataMeta</code> is a meta class describing the database table object CLW_UPLOAD_VALUE.
 */
public class UploadValueDataMeta extends com.cleanwise.service.api.cachecos.TableObjectMeta {

    private static final long serialVersionUID = -1L; //ERROR. No Serial Version Provided
       
    /**
     * Constructor.
     */
    public UploadValueDataMeta(TableObject pData) {
        
        super(UploadValueDataAccess.CLW_UPLOAD_VALUE);

        if (pData != null) {
            TableObjectFieldMeta fm = new TableObjectFieldMeta();
            fm.setField(UploadValueDataAccess.UPLOAD_VALUE_ID);
            fm.setValue(pData.getFieldValue(UploadValueDataAccess.UPLOAD_VALUE_ID));
            addField(fm);
        }
    
    }

    /**
     * Constructor.
     */
    public UploadValueDataMeta(TableField... fields) {
        
        super(UploadValueDataAccess.CLW_UPLOAD_VALUE);

        for (TableField field : fields) {
            TableObjectFieldMeta fm = new TableObjectFieldMeta();
            fm.setField(field.getName());
            fm.setValue(field.getValue());
            addField(fm);
        }
    
    }

}
    
