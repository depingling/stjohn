
/* DO NOT EDIT - Generated code from XSL file ValueObject.xsl */

package com.cleanwise.service.api.meta;

/**
 * Title:        PreOrderAddressDataMeta
 * Description:  This is a meta class describing the object of database table  CLW_PRE_ORDER_ADDRESS.
 * Purpose:
 * Copyright:    Copyright (c) 2009
 * Company:      CleanWise, Inc.
 * @author Generated Code from XSL file ValueObjectMeta.xsl
 */

import com.cleanwise.service.api.dao.PreOrderAddressDataAccess;
import com.cleanwise.service.api.value.TableObject;
import com.cleanwise.service.api.cachecos.TableObjectFieldMeta;
import com.cleanwise.service.api.cachecos.TableField;

/**
 * <code>PreOrderAddressDataMeta</code> is a meta class describing the database table object CLW_PRE_ORDER_ADDRESS.
 */
public class PreOrderAddressDataMeta extends com.cleanwise.service.api.cachecos.TableObjectMeta {

    private static final long serialVersionUID = -1L; //ERROR. No Serial Version Provided
       
    /**
     * Constructor.
     */
    public PreOrderAddressDataMeta(TableObject pData) {
        
        super(PreOrderAddressDataAccess.CLW_PRE_ORDER_ADDRESS);

        if (pData != null) {
            TableObjectFieldMeta fm = new TableObjectFieldMeta();
            fm.setField(PreOrderAddressDataAccess.PRE_ORDER_ADDRESS_ID);
            fm.setValue(pData.getFieldValue(PreOrderAddressDataAccess.PRE_ORDER_ADDRESS_ID));
            addField(fm);
        }
    
    }

    /**
     * Constructor.
     */
    public PreOrderAddressDataMeta(TableField... fields) {
        
        super(PreOrderAddressDataAccess.CLW_PRE_ORDER_ADDRESS);

        for (TableField field : fields) {
            TableObjectFieldMeta fm = new TableObjectFieldMeta();
            fm.setField(field.getName());
            fm.setValue(field.getValue());
            addField(fm);
        }
    
    }

}
    
