
/* DO NOT EDIT - Generated code from XSL file ValueObject.xsl */

package com.cleanwise.service.api.meta;

/**
 * Title:        RemittancePropertyDataMeta
 * Description:  This is a meta class describing the object of database table  CLW_REMITTANCE_PROPERTY.
 * Purpose:
 * Copyright:    Copyright (c) 2009
 * Company:      CleanWise, Inc.
 * @author Generated Code from XSL file ValueObjectMeta.xsl
 */

import com.cleanwise.service.api.dao.RemittancePropertyDataAccess;
import com.cleanwise.service.api.value.TableObject;
import com.cleanwise.service.api.cachecos.TableObjectFieldMeta;
import com.cleanwise.service.api.cachecos.TableField;

/**
 * <code>RemittancePropertyDataMeta</code> is a meta class describing the database table object CLW_REMITTANCE_PROPERTY.
 */
public class RemittancePropertyDataMeta extends com.cleanwise.service.api.cachecos.TableObjectMeta {

    private static final long serialVersionUID = -1L; //ERROR. No Serial Version Provided
       
    /**
     * Constructor.
     */
    public RemittancePropertyDataMeta(TableObject pData) {
        
        super(RemittancePropertyDataAccess.CLW_REMITTANCE_PROPERTY);

        if (pData != null) {
            TableObjectFieldMeta fm = new TableObjectFieldMeta();
            fm.setField(RemittancePropertyDataAccess.REMITTANCE_PROPERTY_ID);
            fm.setValue(pData.getFieldValue(RemittancePropertyDataAccess.REMITTANCE_PROPERTY_ID));
            addField(fm);
        }
    
    }

    /**
     * Constructor.
     */
    public RemittancePropertyDataMeta(TableField... fields) {
        
        super(RemittancePropertyDataAccess.CLW_REMITTANCE_PROPERTY);

        for (TableField field : fields) {
            TableObjectFieldMeta fm = new TableObjectFieldMeta();
            fm.setField(field.getName());
            fm.setValue(field.getValue());
            addField(fm);
        }
    
    }

}
    
