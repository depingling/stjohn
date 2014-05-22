
/* DO NOT EDIT - Generated code from XSL file ValueObject.xsl */

package com.cleanwise.service.api.meta;

/**
 * Title:        GroupAssocDataMeta
 * Description:  This is a meta class describing the object of database table  CLW_GROUP_ASSOC.
 * Purpose:
 * Copyright:    Copyright (c) 2009
 * Company:      CleanWise, Inc.
 * @author Generated Code from XSL file ValueObjectMeta.xsl
 */

import com.cleanwise.service.api.dao.GroupAssocDataAccess;
import com.cleanwise.service.api.value.TableObject;
import com.cleanwise.service.api.cachecos.TableObjectFieldMeta;
import com.cleanwise.service.api.cachecos.TableField;

/**
 * <code>GroupAssocDataMeta</code> is a meta class describing the database table object CLW_GROUP_ASSOC.
 */
public class GroupAssocDataMeta extends com.cleanwise.service.api.cachecos.TableObjectMeta {

    private static final long serialVersionUID = -1L; //ERROR. No Serial Version Provided
       
    /**
     * Constructor.
     */
    public GroupAssocDataMeta(TableObject pData) {
        
        super(GroupAssocDataAccess.CLW_GROUP_ASSOC);

        if (pData != null) {
            TableObjectFieldMeta fm = new TableObjectFieldMeta();
            fm.setField(GroupAssocDataAccess.GROUP_ASSOC_ID);
            fm.setValue(pData.getFieldValue(GroupAssocDataAccess.GROUP_ASSOC_ID));
            addField(fm);
        }
    
    }

    /**
     * Constructor.
     */
    public GroupAssocDataMeta(TableField... fields) {
        
        super(GroupAssocDataAccess.CLW_GROUP_ASSOC);

        for (TableField field : fields) {
            TableObjectFieldMeta fm = new TableObjectFieldMeta();
            fm.setField(field.getName());
            fm.setValue(field.getValue());
            addField(fm);
        }
    
    }

}
    
