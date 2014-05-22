
/* DO NOT EDIT - Generated code from XSL file ValueObject.xsl */

package com.cleanwise.service.api.meta;

/**
 * Title:        PhoneDataMeta
 * Description:  This is a meta class describing the object of database table  CLW_PHONE.
 * Purpose:
 * Copyright:    Copyright (c) 2009
 * Company:      CleanWise, Inc.
 * @author Generated Code from XSL file ValueObjectMeta.xsl
 */

import com.cleanwise.service.api.dao.PhoneDataAccess;
import com.cleanwise.service.api.value.TableObject;
import com.cleanwise.service.api.cachecos.TableObjectFieldMeta;
import com.cleanwise.service.api.cachecos.TableField;

/**
 * <code>PhoneDataMeta</code> is a meta class describing the database table object CLW_PHONE.
 */
public class PhoneDataMeta extends com.cleanwise.service.api.cachecos.TableObjectMeta {

    private static final long serialVersionUID = -1L; //ERROR. No Serial Version Provided
       
    /**
     * Constructor.
     */
    public PhoneDataMeta(TableObject pData) {
        
        super(PhoneDataAccess.CLW_PHONE);

        if (pData != null) {
            TableObjectFieldMeta fm = new TableObjectFieldMeta();
            fm.setField(PhoneDataAccess.PHONE_ID);
            fm.setValue(pData.getFieldValue(PhoneDataAccess.PHONE_ID));
            addField(fm);
        }
    
    }

    /**
     * Constructor.
     */
    public PhoneDataMeta(TableField... fields) {
        
        super(PhoneDataAccess.CLW_PHONE);

        for (TableField field : fields) {
            TableObjectFieldMeta fm = new TableObjectFieldMeta();
            fm.setField(field.getName());
            fm.setValue(field.getValue());
            addField(fm);
        }
    
    }

}
    
