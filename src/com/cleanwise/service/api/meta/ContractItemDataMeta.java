
/* DO NOT EDIT - Generated code from XSL file ValueObject.xsl */

package com.cleanwise.service.api.meta;

/**
 * Title:        ContractItemDataMeta
 * Description:  This is a meta class describing the object of database table  CLW_CONTRACT_ITEM.
 * Purpose:
 * Copyright:    Copyright (c) 2009
 * Company:      CleanWise, Inc.
 * @author Generated Code from XSL file ValueObjectMeta.xsl
 */

import com.cleanwise.service.api.dao.ContractItemDataAccess;
import com.cleanwise.service.api.value.TableObject;
import com.cleanwise.service.api.cachecos.TableObjectFieldMeta;
import com.cleanwise.service.api.cachecos.TableField;

/**
 * <code>ContractItemDataMeta</code> is a meta class describing the database table object CLW_CONTRACT_ITEM.
 */
public class ContractItemDataMeta extends com.cleanwise.service.api.cachecos.TableObjectMeta {

    private static final long serialVersionUID = -1L; //ERROR. No Serial Version Provided
       
    /**
     * Constructor.
     */
    public ContractItemDataMeta(TableObject pData) {
        
        super(ContractItemDataAccess.CLW_CONTRACT_ITEM);

        if (pData != null) {
            TableObjectFieldMeta fm = new TableObjectFieldMeta();
            fm.setField(ContractItemDataAccess.CONTRACT_ITEM_ID);
            fm.setValue(pData.getFieldValue(ContractItemDataAccess.CONTRACT_ITEM_ID));
            addField(fm);
        }
    
    }

    /**
     * Constructor.
     */
    public ContractItemDataMeta(TableField... fields) {
        
        super(ContractItemDataAccess.CLW_CONTRACT_ITEM);

        for (TableField field : fields) {
            TableObjectFieldMeta fm = new TableObjectFieldMeta();
            fm.setField(field.getName());
            fm.setValue(field.getValue());
            addField(fm);
        }
    
    }

}
    
