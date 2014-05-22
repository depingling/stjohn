
/* DO NOT EDIT - Generated code from XSL file ValueObject.xsl */

package com.cleanwise.service.api.meta;

/**
 * Title:        ContractItemSubstDataMeta
 * Description:  This is a meta class describing the object of database table  CLW_CONTRACT_ITEM_SUBST.
 * Purpose:
 * Copyright:    Copyright (c) 2009
 * Company:      CleanWise, Inc.
 * @author Generated Code from XSL file ValueObjectMeta.xsl
 */

import com.cleanwise.service.api.dao.ContractItemSubstDataAccess;
import com.cleanwise.service.api.value.TableObject;
import com.cleanwise.service.api.cachecos.TableObjectFieldMeta;
import com.cleanwise.service.api.cachecos.TableField;

/**
 * <code>ContractItemSubstDataMeta</code> is a meta class describing the database table object CLW_CONTRACT_ITEM_SUBST.
 */
public class ContractItemSubstDataMeta extends com.cleanwise.service.api.cachecos.TableObjectMeta {

    private static final long serialVersionUID = -1L; //ERROR. No Serial Version Provided
       
    /**
     * Constructor.
     */
    public ContractItemSubstDataMeta(TableObject pData) {
        
        super(ContractItemSubstDataAccess.CLW_CONTRACT_ITEM_SUBST);

        if (pData != null) {
            TableObjectFieldMeta fm = new TableObjectFieldMeta();
            fm.setField(ContractItemSubstDataAccess.CONTRACT_ITEM_SUBST_ID);
            fm.setValue(pData.getFieldValue(ContractItemSubstDataAccess.CONTRACT_ITEM_SUBST_ID));
            addField(fm);
        }
    
    }

    /**
     * Constructor.
     */
    public ContractItemSubstDataMeta(TableField... fields) {
        
        super(ContractItemSubstDataAccess.CLW_CONTRACT_ITEM_SUBST);

        for (TableField field : fields) {
            TableObjectFieldMeta fm = new TableObjectFieldMeta();
            fm.setField(field.getName());
            fm.setValue(field.getValue());
            addField(fm);
        }
    
    }

}
    
