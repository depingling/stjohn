
/* DO NOT EDIT - Generated code from XSL file ValueObject.xsl */

package com.cleanwise.service.api.meta;

/**
 * Title:        ElectronicTransactionDataMeta
 * Description:  This is a meta class describing the object of database table  CLW_ELECTRONIC_TRANSACTION.
 * Purpose:
 * Copyright:    Copyright (c) 2009
 * Company:      CleanWise, Inc.
 * @author Generated Code from XSL file ValueObjectMeta.xsl
 */

import com.cleanwise.service.api.dao.ElectronicTransactionDataAccess;
import com.cleanwise.service.api.value.TableObject;
import com.cleanwise.service.api.cachecos.TableObjectFieldMeta;
import com.cleanwise.service.api.cachecos.TableField;

/**
 * <code>ElectronicTransactionDataMeta</code> is a meta class describing the database table object CLW_ELECTRONIC_TRANSACTION.
 */
public class ElectronicTransactionDataMeta extends com.cleanwise.service.api.cachecos.TableObjectMeta {

    private static final long serialVersionUID = -1L; //ERROR. No Serial Version Provided
       
    /**
     * Constructor.
     */
    public ElectronicTransactionDataMeta(TableObject pData) {
        
        super(ElectronicTransactionDataAccess.CLW_ELECTRONIC_TRANSACTION);

        if (pData != null) {
            TableObjectFieldMeta fm = new TableObjectFieldMeta();
            fm.setField(ElectronicTransactionDataAccess.ELECTRONIC_TRANSACTION_ID);
            fm.setValue(pData.getFieldValue(ElectronicTransactionDataAccess.ELECTRONIC_TRANSACTION_ID));
            addField(fm);
        }
    
    }

    /**
     * Constructor.
     */
    public ElectronicTransactionDataMeta(TableField... fields) {
        
        super(ElectronicTransactionDataAccess.CLW_ELECTRONIC_TRANSACTION);

        for (TableField field : fields) {
            TableObjectFieldMeta fm = new TableObjectFieldMeta();
            fm.setField(field.getName());
            fm.setValue(field.getValue());
            addField(fm);
        }
    
    }

}
    
