
/* DO NOT EDIT - Generated code from XSL file ValueObject.xsl */

package com.cleanwise.service.api.meta;

/**
 * Title:        WarrantyDataMeta
 * Description:  This is a meta class describing the object of database table  CLW_WARRANTY.
 * Purpose:
 * Copyright:    Copyright (c) 2009
 * Company:      CleanWise, Inc.
 * @author Generated Code from XSL file ValueObjectMeta.xsl
 */

import com.cleanwise.service.api.dao.WarrantyDataAccess;
import com.cleanwise.service.api.value.TableObject;
import com.cleanwise.service.api.cachecos.TableObjectFieldMeta;
import com.cleanwise.service.api.cachecos.TableField;

/**
 * <code>WarrantyDataMeta</code> is a meta class describing the database table object CLW_WARRANTY.
 */
public class WarrantyDataMeta extends com.cleanwise.service.api.cachecos.TableObjectMeta {

    private static final long serialVersionUID = -1L; //ERROR. No Serial Version Provided
       
    /**
     * Constructor.
     */
    public WarrantyDataMeta(TableObject pData) {
        
        super(WarrantyDataAccess.CLW_WARRANTY);

        if (pData != null) {
            TableObjectFieldMeta fm = new TableObjectFieldMeta();
            fm.setField(WarrantyDataAccess.WARRANTY_ID);
            fm.setValue(pData.getFieldValue(WarrantyDataAccess.WARRANTY_ID));
            addField(fm);
        }
    
    }

    /**
     * Constructor.
     */
    public WarrantyDataMeta(TableField... fields) {
        
        super(WarrantyDataAccess.CLW_WARRANTY);

        for (TableField field : fields) {
            TableObjectFieldMeta fm = new TableObjectFieldMeta();
            fm.setField(field.getName());
            fm.setValue(field.getValue());
            addField(fm);
        }
    
    }

}
    
