
/* DO NOT EDIT - Generated code from XSL file ValueObject.xsl */

package com.cleanwise.service.api.meta;

/**
 * Title:        AddressDataMeta
 * Description:  This is a meta class describing the object of database table  CLW_ADDRESS.
 * Purpose:
 * Copyright:    Copyright (c) 2009
 * Company:      CleanWise, Inc.
 * @author Generated Code from XSL file ValueObjectMeta.xsl
 */

import com.cleanwise.service.api.dao.AddressDataAccess;
import com.cleanwise.service.api.value.TableObject;
import com.cleanwise.service.api.cachecos.TableObjectFieldMeta;
import com.cleanwise.service.api.cachecos.TableField;

/**
 * <code>AddressDataMeta</code> is a meta class describing the database table object CLW_ADDRESS.
 */
public class AddressDataMeta extends com.cleanwise.service.api.cachecos.TableObjectMeta {

    private static final long serialVersionUID = -1L; //ERROR. No Serial Version Provided
       
    /**
     * Constructor.
     */
    public AddressDataMeta(TableObject pData) {
        
        super(AddressDataAccess.CLW_ADDRESS);

        if (pData != null) {
            TableObjectFieldMeta fm = new TableObjectFieldMeta();
            fm.setField(AddressDataAccess.ADDRESS_ID);
            fm.setValue(pData.getFieldValue(AddressDataAccess.ADDRESS_ID));
            addField(fm);
        }
    
    }

    /**
     * Constructor.
     */
    public AddressDataMeta(TableField... fields) {
        
        super(AddressDataAccess.CLW_ADDRESS);

        for (TableField field : fields) {
            TableObjectFieldMeta fm = new TableObjectFieldMeta();
            fm.setField(field.getName());
            fm.setValue(field.getValue());
            addField(fm);
        }
    
    }

}
    
