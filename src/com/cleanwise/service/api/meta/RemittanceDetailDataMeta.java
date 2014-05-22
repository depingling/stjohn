
/* DO NOT EDIT - Generated code from XSL file ValueObject.xsl */

package com.cleanwise.service.api.meta;

/**
 * Title:        RemittanceDetailDataMeta
 * Description:  This is a meta class describing the object of database table  CLW_REMITTANCE_DETAIL.
 * Purpose:
 * Copyright:    Copyright (c) 2009
 * Company:      CleanWise, Inc.
 * @author Generated Code from XSL file ValueObjectMeta.xsl
 */

import com.cleanwise.service.api.dao.RemittanceDetailDataAccess;
import com.cleanwise.service.api.value.TableObject;
import com.cleanwise.service.api.cachecos.TableObjectFieldMeta;
import com.cleanwise.service.api.cachecos.TableField;

/**
 * <code>RemittanceDetailDataMeta</code> is a meta class describing the database table object CLW_REMITTANCE_DETAIL.
 */
public class RemittanceDetailDataMeta extends com.cleanwise.service.api.cachecos.TableObjectMeta {

    private static final long serialVersionUID = -1L; //ERROR. No Serial Version Provided
       
    /**
     * Constructor.
     */
    public RemittanceDetailDataMeta(TableObject pData) {
        
        super(RemittanceDetailDataAccess.CLW_REMITTANCE_DETAIL);

        if (pData != null) {
            TableObjectFieldMeta fm = new TableObjectFieldMeta();
            fm.setField(RemittanceDetailDataAccess.REMITTANCE_DETAIL_ID);
            fm.setValue(pData.getFieldValue(RemittanceDetailDataAccess.REMITTANCE_DETAIL_ID));
            addField(fm);
        }
    
    }

    /**
     * Constructor.
     */
    public RemittanceDetailDataMeta(TableField... fields) {
        
        super(RemittanceDetailDataAccess.CLW_REMITTANCE_DETAIL);

        for (TableField field : fields) {
            TableObjectFieldMeta fm = new TableObjectFieldMeta();
            fm.setField(field.getName());
            fm.setValue(field.getValue());
            addField(fm);
        }
    
    }

}
    
