
/* DO NOT EDIT - Generated code from XSL file ValueObject.xsl */

package com.cleanwise.service.api.meta;

/**
 * Title:        UserDataMeta
 * Description:  This is a meta class describing the object of database table  CLW_USER.
 * Purpose:
 * Copyright:    Copyright (c) 2009
 * Company:      CleanWise, Inc.
 * @author Generated Code from XSL file ValueObjectMeta.xsl
 */

import com.cleanwise.service.api.dao.UserDataAccess;
import com.cleanwise.service.api.value.TableObject;
import com.cleanwise.service.api.cachecos.TableObjectFieldMeta;
import com.cleanwise.service.api.cachecos.TableField;

/**
 * <code>UserDataMeta</code> is a meta class describing the database table object CLW_USER.
 */
public class UserDataMeta extends com.cleanwise.service.api.cachecos.TableObjectMeta {

    private static final long serialVersionUID = -1L; //ERROR. No Serial Version Provided
       
    /**
     * Constructor.
     */
    public UserDataMeta(TableObject pData) {
        
        super(UserDataAccess.CLW_USER);

        if (pData != null) {
            TableObjectFieldMeta fm = new TableObjectFieldMeta();
            fm.setField(UserDataAccess.USER_ID);
            fm.setValue(pData.getFieldValue(UserDataAccess.USER_ID));
            addField(fm);
        }
    
    }

    /**
     * Constructor.
     */
    public UserDataMeta(TableField... fields) {
        
        super(UserDataAccess.CLW_USER);

        for (TableField field : fields) {
            TableObjectFieldMeta fm = new TableObjectFieldMeta();
            fm.setField(field.getName());
            fm.setValue(field.getValue());
            addField(fm);
        }
    
    }

}
    
