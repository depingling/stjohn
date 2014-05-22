
/* DO NOT EDIT - Generated code from XSL file ValueObject.xsl */

package com.cleanwise.service.api.meta;

/**
 * Title:        EmailDataMeta
 * Description:  This is a meta class describing the object of database table  CLW_EMAIL.
 * Purpose:
 * Copyright:    Copyright (c) 2009
 * Company:      CleanWise, Inc.
 * @author Generated Code from XSL file ValueObjectMeta.xsl
 */

import com.cleanwise.service.api.dao.EmailDataAccess;
import com.cleanwise.service.api.value.TableObject;
import com.cleanwise.service.api.cachecos.TableObjectFieldMeta;
import com.cleanwise.service.api.cachecos.TableField;

/**
 * <code>EmailDataMeta</code> is a meta class describing the database table object CLW_EMAIL.
 */
public class EmailDataMeta extends com.cleanwise.service.api.cachecos.TableObjectMeta {

    private static final long serialVersionUID = -1L; //ERROR. No Serial Version Provided
       
    /**
     * Constructor.
     */
    public EmailDataMeta(TableObject pData) {
        
        super(EmailDataAccess.CLW_EMAIL);

        if (pData != null) {
            TableObjectFieldMeta fm = new TableObjectFieldMeta();
            fm.setField(EmailDataAccess.EMAIL_ID);
            fm.setValue(pData.getFieldValue(EmailDataAccess.EMAIL_ID));
            addField(fm);
        }
    
    }

    /**
     * Constructor.
     */
    public EmailDataMeta(TableField... fields) {
        
        super(EmailDataAccess.CLW_EMAIL);

        for (TableField field : fields) {
            TableObjectFieldMeta fm = new TableObjectFieldMeta();
            fm.setField(field.getName());
            fm.setValue(field.getValue());
            addField(fm);
        }
    
    }

}
    
