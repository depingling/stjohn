
/* DO NOT EDIT - Generated code from XSL file ValueObject.xsl */

package com.cleanwise.service.api.meta;

/**
 * Title:        WarrantyPropertyDataMeta
 * Description:  This is a meta class describing the object of database table  CLW_WARRANTY_PROPERTY.
 * Purpose:
 * Copyright:    Copyright (c) 2009
 * Company:      CleanWise, Inc.
 * @author Generated Code from XSL file ValueObjectMeta.xsl
 */

import com.cleanwise.service.api.dao.WarrantyPropertyDataAccess;
import com.cleanwise.service.api.value.TableObject;
import com.cleanwise.service.api.cachecos.TableObjectFieldMeta;
import com.cleanwise.service.api.cachecos.TableField;

/**
 * <code>WarrantyPropertyDataMeta</code> is a meta class describing the database table object CLW_WARRANTY_PROPERTY.
 */
public class WarrantyPropertyDataMeta extends com.cleanwise.service.api.cachecos.TableObjectMeta {

    private static final long serialVersionUID = -1L; //ERROR. No Serial Version Provided
       
    /**
     * Constructor.
     */
    public WarrantyPropertyDataMeta(TableObject pData) {
        
        super(WarrantyPropertyDataAccess.CLW_WARRANTY_PROPERTY);

        if (pData != null) {
            TableObjectFieldMeta fm = new TableObjectFieldMeta();
            fm.setField(WarrantyPropertyDataAccess.WARRANTY_PROPERTY_ID);
            fm.setValue(pData.getFieldValue(WarrantyPropertyDataAccess.WARRANTY_PROPERTY_ID));
            addField(fm);
        }
    
    }

    /**
     * Constructor.
     */
    public WarrantyPropertyDataMeta(TableField... fields) {
        
        super(WarrantyPropertyDataAccess.CLW_WARRANTY_PROPERTY);

        for (TableField field : fields) {
            TableObjectFieldMeta fm = new TableObjectFieldMeta();
            fm.setField(field.getName());
            fm.setValue(field.getValue());
            addField(fm);
        }
    
    }

}
    
