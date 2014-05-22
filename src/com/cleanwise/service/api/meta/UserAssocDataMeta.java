
/* DO NOT EDIT - Generated code from XSL file ValueObject.xsl */

package com.cleanwise.service.api.meta;

/**
 * Title:        UserAssocDataMeta
 * Description:  This is a meta class describing the object of database table  CLW_USER_ASSOC.
 * Purpose:
 * Copyright:    Copyright (c) 2009
 * Company:      CleanWise, Inc.
 * @author Generated Code from XSL file ValueObjectMeta.xsl
 */

import com.cleanwise.service.api.dao.UserAssocDataAccess;
import com.cleanwise.service.api.value.TableObject;
import com.cleanwise.service.api.cachecos.TableObjectFieldMeta;
import com.cleanwise.service.api.cachecos.TableField;

/**
 * <code>UserAssocDataMeta</code> is a meta class describing the database table object CLW_USER_ASSOC.
 */
public class UserAssocDataMeta extends com.cleanwise.service.api.cachecos.TableObjectMeta {

    private static final long serialVersionUID = -1L; //ERROR. No Serial Version Provided
       
    /**
     * Constructor.
     */
    public UserAssocDataMeta(TableObject pData) {
        
        super(UserAssocDataAccess.CLW_USER_ASSOC);

        if (pData != null) {
            TableObjectFieldMeta fm = new TableObjectFieldMeta();
            fm.setField(UserAssocDataAccess.USER_ASSOC_ID);
            fm.setValue(pData.getFieldValue(UserAssocDataAccess.USER_ASSOC_ID));
            addField(fm);
        }
    
    }

    /**
     * Constructor.
     */
    public UserAssocDataMeta(TableField... fields) {
        
        super(UserAssocDataAccess.CLW_USER_ASSOC);

        for (TableField field : fields) {
            TableObjectFieldMeta fm = new TableObjectFieldMeta();
            fm.setField(field.getName());
            fm.setValue(field.getValue());
            addField(fm);
        }
    
    }

}
    
