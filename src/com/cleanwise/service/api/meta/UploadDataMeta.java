
/* DO NOT EDIT - Generated code from XSL file ValueObject.xsl */

package com.cleanwise.service.api.meta;

/**
 * Title:        UploadDataMeta
 * Description:  This is a meta class describing the object of database table  CLW_UPLOAD.
 * Purpose:
 * Copyright:    Copyright (c) 2009
 * Company:      CleanWise, Inc.
 * @author Generated Code from XSL file ValueObjectMeta.xsl
 */

import com.cleanwise.service.api.dao.UploadDataAccess;
import com.cleanwise.service.api.value.TableObject;
import com.cleanwise.service.api.cachecos.TableObjectFieldMeta;
import com.cleanwise.service.api.cachecos.TableField;

/**
 * <code>UploadDataMeta</code> is a meta class describing the database table object CLW_UPLOAD.
 */
public class UploadDataMeta extends com.cleanwise.service.api.cachecos.TableObjectMeta {

    private static final long serialVersionUID = -1L; //ERROR. No Serial Version Provided
       
    /**
     * Constructor.
     */
    public UploadDataMeta(TableObject pData) {
        
        super(UploadDataAccess.CLW_UPLOAD);

        if (pData != null) {
            TableObjectFieldMeta fm = new TableObjectFieldMeta();
            fm.setField(UploadDataAccess.UPLOAD_ID);
            fm.setValue(pData.getFieldValue(UploadDataAccess.UPLOAD_ID));
            addField(fm);
        }
    
    }

    /**
     * Constructor.
     */
    public UploadDataMeta(TableField... fields) {
        
        super(UploadDataAccess.CLW_UPLOAD);

        for (TableField field : fields) {
            TableObjectFieldMeta fm = new TableObjectFieldMeta();
            fm.setField(field.getName());
            fm.setValue(field.getValue());
            addField(fm);
        }
    
    }

}
    
