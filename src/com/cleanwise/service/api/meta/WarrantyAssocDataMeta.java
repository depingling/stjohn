
/* DO NOT EDIT - Generated code from XSL file ValueObject.xsl */

package com.cleanwise.service.api.meta;

/**
 * Title:        WarrantyAssocDataMeta
 * Description:  This is a meta class describing the object of database table  CLW_WARRANTY_ASSOC.
 * Purpose:
 * Copyright:    Copyright (c) 2009
 * Company:      CleanWise, Inc.
 * @author Generated Code from XSL file ValueObjectMeta.xsl
 */

import com.cleanwise.service.api.dao.WarrantyAssocDataAccess;
import com.cleanwise.service.api.value.TableObject;
import com.cleanwise.service.api.cachecos.TableObjectFieldMeta;
import com.cleanwise.service.api.cachecos.TableField;

/**
 * <code>WarrantyAssocDataMeta</code> is a meta class describing the database table object CLW_WARRANTY_ASSOC.
 */
public class WarrantyAssocDataMeta extends com.cleanwise.service.api.cachecos.TableObjectMeta {

    private static final long serialVersionUID = -1L; //ERROR. No Serial Version Provided
       
    /**
     * Constructor.
     */
    public WarrantyAssocDataMeta(TableObject pData) {
        
        super(WarrantyAssocDataAccess.CLW_WARRANTY_ASSOC);

        if (pData != null) {
            TableObjectFieldMeta fm = new TableObjectFieldMeta();
            fm.setField(WarrantyAssocDataAccess.WARRANTY_ASSOC_ID);
            fm.setValue(pData.getFieldValue(WarrantyAssocDataAccess.WARRANTY_ASSOC_ID));
            addField(fm);
        }
    
    }

    /**
     * Constructor.
     */
    public WarrantyAssocDataMeta(TableField... fields) {
        
        super(WarrantyAssocDataAccess.CLW_WARRANTY_ASSOC);

        for (TableField field : fields) {
            TableObjectFieldMeta fm = new TableObjectFieldMeta();
            fm.setField(field.getName());
            fm.setValue(field.getValue());
            addField(fm);
        }
    
    }

}
    
