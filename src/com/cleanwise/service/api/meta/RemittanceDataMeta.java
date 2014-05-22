
/* DO NOT EDIT - Generated code from XSL file ValueObject.xsl */

package com.cleanwise.service.api.meta;

/**
 * Title:        RemittanceDataMeta
 * Description:  This is a meta class describing the object of database table  CLW_REMITTANCE.
 * Purpose:
 * Copyright:    Copyright (c) 2009
 * Company:      CleanWise, Inc.
 * @author Generated Code from XSL file ValueObjectMeta.xsl
 */

import com.cleanwise.service.api.dao.RemittanceDataAccess;
import com.cleanwise.service.api.value.TableObject;
import com.cleanwise.service.api.cachecos.TableObjectFieldMeta;
import com.cleanwise.service.api.cachecos.TableField;

/**
 * <code>RemittanceDataMeta</code> is a meta class describing the database table object CLW_REMITTANCE.
 */
public class RemittanceDataMeta extends com.cleanwise.service.api.cachecos.TableObjectMeta {

    private static final long serialVersionUID = -1L; //ERROR. No Serial Version Provided
       
    /**
     * Constructor.
     */
    public RemittanceDataMeta(TableObject pData) {
        
        super(RemittanceDataAccess.CLW_REMITTANCE);

        if (pData != null) {
            TableObjectFieldMeta fm = new TableObjectFieldMeta();
            fm.setField(RemittanceDataAccess.REMITTANCE_ID);
            fm.setValue(pData.getFieldValue(RemittanceDataAccess.REMITTANCE_ID));
            addField(fm);
        }
    
    }

    /**
     * Constructor.
     */
    public RemittanceDataMeta(TableField... fields) {
        
        super(RemittanceDataAccess.CLW_REMITTANCE);

        for (TableField field : fields) {
            TableObjectFieldMeta fm = new TableObjectFieldMeta();
            fm.setField(field.getName());
            fm.setValue(field.getValue());
            addField(fm);
        }
    
    }

}
    
