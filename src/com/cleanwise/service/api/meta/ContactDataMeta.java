
/* DO NOT EDIT - Generated code from XSL file ValueObject.xsl */

package com.cleanwise.service.api.meta;

/**
 * Title:        ContactDataMeta
 * Description:  This is a meta class describing the object of database table  CLW_CONTACT.
 * Purpose:
 * Copyright:    Copyright (c) 2009
 * Company:      CleanWise, Inc.
 * @author Generated Code from XSL file ValueObjectMeta.xsl
 */

import com.cleanwise.service.api.dao.ContactDataAccess;
import com.cleanwise.service.api.value.TableObject;
import com.cleanwise.service.api.cachecos.TableObjectFieldMeta;
import com.cleanwise.service.api.cachecos.TableField;

/**
 * <code>ContactDataMeta</code> is a meta class describing the database table object CLW_CONTACT.
 */
public class ContactDataMeta extends com.cleanwise.service.api.cachecos.TableObjectMeta {

    private static final long serialVersionUID = -1L; //ERROR. No Serial Version Provided
       
    /**
     * Constructor.
     */
    public ContactDataMeta(TableObject pData) {
        
        super(ContactDataAccess.CLW_CONTACT);

        if (pData != null) {
            TableObjectFieldMeta fm = new TableObjectFieldMeta();
            fm.setField(ContactDataAccess.CONTACT_ID);
            fm.setValue(pData.getFieldValue(ContactDataAccess.CONTACT_ID));
            addField(fm);
        }
    
    }

    /**
     * Constructor.
     */
    public ContactDataMeta(TableField... fields) {
        
        super(ContactDataAccess.CLW_CONTACT);

        for (TableField field : fields) {
            TableObjectFieldMeta fm = new TableObjectFieldMeta();
            fm.setField(field.getName());
            fm.setValue(field.getValue());
            addField(fm);
        }
    
    }

}
    
