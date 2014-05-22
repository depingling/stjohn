
/* DO NOT EDIT - Generated code from XSL file ValueObject.xsl */

package com.cleanwise.service.api.meta;

/**
 * Title:        UserNoteActivityDataMeta
 * Description:  This is a meta class describing the object of database table  CLW_USER_NOTE_ACTIVITY.
 * Purpose:
 * Copyright:    Copyright (c) 2009
 * Company:      CleanWise, Inc.
 * @author Generated Code from XSL file ValueObjectMeta.xsl
 */

import com.cleanwise.service.api.dao.UserNoteActivityDataAccess;
import com.cleanwise.service.api.value.TableObject;
import com.cleanwise.service.api.cachecos.TableObjectFieldMeta;
import com.cleanwise.service.api.cachecos.TableField;

/**
 * <code>UserNoteActivityDataMeta</code> is a meta class describing the database table object CLW_USER_NOTE_ACTIVITY.
 */
public class UserNoteActivityDataMeta extends com.cleanwise.service.api.cachecos.TableObjectMeta {

    private static final long serialVersionUID = -1L; //ERROR. No Serial Version Provided
       
    /**
     * Constructor.
     */
    public UserNoteActivityDataMeta(TableObject pData) {
        
        super(UserNoteActivityDataAccess.CLW_USER_NOTE_ACTIVITY);

        if (pData != null) {
            TableObjectFieldMeta fm = new TableObjectFieldMeta();
            fm.setField(UserNoteActivityDataAccess.USER_NOTE_ACTIVITY_ID);
            fm.setValue(pData.getFieldValue(UserNoteActivityDataAccess.USER_NOTE_ACTIVITY_ID));
            addField(fm);
        }
    
    }

    /**
     * Constructor.
     */
    public UserNoteActivityDataMeta(TableField... fields) {
        
        super(UserNoteActivityDataAccess.CLW_USER_NOTE_ACTIVITY);

        for (TableField field : fields) {
            TableObjectFieldMeta fm = new TableObjectFieldMeta();
            fm.setField(field.getName());
            fm.setValue(field.getValue());
            addField(fm);
        }
    
    }

}
    
