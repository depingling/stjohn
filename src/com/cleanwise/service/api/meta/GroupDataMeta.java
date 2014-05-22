
/* DO NOT EDIT - Generated code from XSL file ValueObject.xsl */

package com.cleanwise.service.api.meta;

/**
 * Title:        GroupDataMeta
 * Description:  This is a meta class describing the object of database table  CLW_GROUP.
 * Purpose:
 * Copyright:    Copyright (c) 2009
 * Company:      CleanWise, Inc.
 * @author Generated Code from XSL file ValueObjectMeta.xsl
 */

import com.cleanwise.service.api.dao.GroupDataAccess;
import com.cleanwise.service.api.value.TableObject;
import com.cleanwise.service.api.cachecos.TableObjectFieldMeta;
import com.cleanwise.service.api.cachecos.TableField;

/**
 * <code>GroupDataMeta</code> is a meta class describing the database table object CLW_GROUP.
 */
public class GroupDataMeta extends com.cleanwise.service.api.cachecos.TableObjectMeta {

    private static final long serialVersionUID = -1L; //ERROR. No Serial Version Provided
       
    /**
     * Constructor.
     */
    public GroupDataMeta(TableObject pData) {
        
        super(GroupDataAccess.CLW_GROUP);

        if (pData != null) {
            TableObjectFieldMeta fm = new TableObjectFieldMeta();
            fm.setField(GroupDataAccess.GROUP_ID);
            fm.setValue(pData.getFieldValue(GroupDataAccess.GROUP_ID));
            addField(fm);
        }
    
    }

    /**
     * Constructor.
     */
    public GroupDataMeta(TableField... fields) {
        
        super(GroupDataAccess.CLW_GROUP);

        for (TableField field : fields) {
            TableObjectFieldMeta fm = new TableObjectFieldMeta();
            fm.setField(field.getName());
            fm.setValue(field.getValue());
            addField(fm);
        }
    
    }

}
    
