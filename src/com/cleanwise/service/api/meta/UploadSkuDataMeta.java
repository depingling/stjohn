
/* DO NOT EDIT - Generated code from XSL file ValueObject.xsl */

package com.cleanwise.service.api.meta;

/**
 * Title:        UploadSkuDataMeta
 * Description:  This is a meta class describing the object of database table  CLW_UPLOAD_SKU.
 * Purpose:
 * Copyright:    Copyright (c) 2009
 * Company:      CleanWise, Inc.
 * @author Generated Code from XSL file ValueObjectMeta.xsl
 */

import com.cleanwise.service.api.dao.UploadSkuDataAccess;
import com.cleanwise.service.api.value.TableObject;
import com.cleanwise.service.api.cachecos.TableObjectFieldMeta;
import com.cleanwise.service.api.cachecos.TableField;

/**
 * <code>UploadSkuDataMeta</code> is a meta class describing the database table object CLW_UPLOAD_SKU.
 */
public class UploadSkuDataMeta extends com.cleanwise.service.api.cachecos.TableObjectMeta {

    private static final long serialVersionUID = -1L; //ERROR. No Serial Version Provided
       
    /**
     * Constructor.
     */
    public UploadSkuDataMeta(TableObject pData) {
        
        super(UploadSkuDataAccess.CLW_UPLOAD_SKU);

        if (pData != null) {
            TableObjectFieldMeta fm = new TableObjectFieldMeta();
            fm.setField(UploadSkuDataAccess.UPLOAD_SKU_ID);
            fm.setValue(pData.getFieldValue(UploadSkuDataAccess.UPLOAD_SKU_ID));
            addField(fm);
        }
    
    }

    /**
     * Constructor.
     */
    public UploadSkuDataMeta(TableField... fields) {
        
        super(UploadSkuDataAccess.CLW_UPLOAD_SKU);

        for (TableField field : fields) {
            TableObjectFieldMeta fm = new TableObjectFieldMeta();
            fm.setField(field.getName());
            fm.setValue(field.getValue());
            addField(fm);
        }
    
    }

}
    
