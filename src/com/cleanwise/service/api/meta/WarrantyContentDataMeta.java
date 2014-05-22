
/* DO NOT EDIT - Generated code from XSL file ValueObject.xsl */

package com.cleanwise.service.api.meta;

/**
 * Title:        WarrantyContentDataMeta
 * Description:  This is a meta class describing the object of database table  CLW_WARRANTY_CONTENT.
 * Purpose:
 * Copyright:    Copyright (c) 2009
 * Company:      CleanWise, Inc.
 * @author Generated Code from XSL file ValueObjectMeta.xsl
 */

import com.cleanwise.service.api.dao.WarrantyContentDataAccess;
import com.cleanwise.service.api.value.TableObject;
import com.cleanwise.service.api.cachecos.TableObjectFieldMeta;
import com.cleanwise.service.api.cachecos.TableField;

/**
 * <code>WarrantyContentDataMeta</code> is a meta class describing the database table object CLW_WARRANTY_CONTENT.
 */
public class WarrantyContentDataMeta extends com.cleanwise.service.api.cachecos.TableObjectMeta {

    private static final long serialVersionUID = -1L; //ERROR. No Serial Version Provided
       
    /**
     * Constructor.
     */
    public WarrantyContentDataMeta(TableObject pData) {
        
        super(WarrantyContentDataAccess.CLW_WARRANTY_CONTENT);

        if (pData != null) {
            TableObjectFieldMeta fm = new TableObjectFieldMeta();
            fm.setField(WarrantyContentDataAccess.WARRANTY_CONTENT_ID);
            fm.setValue(pData.getFieldValue(WarrantyContentDataAccess.WARRANTY_CONTENT_ID));
            addField(fm);
        }
    
    }

    /**
     * Constructor.
     */
    public WarrantyContentDataMeta(TableField... fields) {
        
        super(WarrantyContentDataAccess.CLW_WARRANTY_CONTENT);

        for (TableField field : fields) {
            TableObjectFieldMeta fm = new TableObjectFieldMeta();
            fm.setField(field.getName());
            fm.setValue(field.getValue());
            addField(fm);
        }
    
    }

}
    
