
/* DO NOT EDIT - Generated code from XSL file ValueObject.xsl */

package com.cleanwise.service.api.meta;

/**
 * Title:        ContractDataMeta
 * Description:  This is a meta class describing the object of database table  CLW_CONTRACT.
 * Purpose:
 * Copyright:    Copyright (c) 2009
 * Company:      CleanWise, Inc.
 * @author Generated Code from XSL file ValueObjectMeta.xsl
 */

import com.cleanwise.service.api.dao.ContractDataAccess;
import com.cleanwise.service.api.value.TableObject;
import com.cleanwise.service.api.cachecos.TableObjectFieldMeta;
import com.cleanwise.service.api.cachecos.TableField;

/**
 * <code>ContractDataMeta</code> is a meta class describing the database table object CLW_CONTRACT.
 */
public class ContractDataMeta extends com.cleanwise.service.api.cachecos.TableObjectMeta {

    private static final long serialVersionUID = -1L; //ERROR. No Serial Version Provided
       
    /**
     * Constructor.
     */
    public ContractDataMeta(TableObject pData) {
        
        super(ContractDataAccess.CLW_CONTRACT);

        if (pData != null) {
            TableObjectFieldMeta fm = new TableObjectFieldMeta();
            fm.setField(ContractDataAccess.CONTRACT_ID);
            fm.setValue(pData.getFieldValue(ContractDataAccess.CONTRACT_ID));
            addField(fm);
        }
    
    }

    /**
     * Constructor.
     */
    public ContractDataMeta(TableField... fields) {
        
        super(ContractDataAccess.CLW_CONTRACT);

        for (TableField field : fields) {
            TableObjectFieldMeta fm = new TableObjectFieldMeta();
            fm.setField(field.getName());
            fm.setValue(field.getValue());
            addField(fm);
        }
    
    }

}
    
