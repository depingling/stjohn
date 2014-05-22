
/* DO NOT EDIT - Generated code from XSL file ValueObject.xsl */

package com.cleanwise.service.api.meta;

/**
 * Title:        TaskPropertyDataMeta
 * Description:  This is a meta class describing the object of database table  CLW_TASK_PROPERTY.
 * Purpose:
 * Copyright:    Copyright (c) 2009
 * Company:      CleanWise, Inc.
 * @author Generated Code from XSL file ValueObjectMeta.xsl
 */

import com.cleanwise.service.api.dao.TaskPropertyDataAccess;
import com.cleanwise.service.api.value.TableObject;
import com.cleanwise.service.api.cachecos.TableObjectFieldMeta;
import com.cleanwise.service.api.cachecos.TableField;

/**
 * <code>TaskPropertyDataMeta</code> is a meta class describing the database table object CLW_TASK_PROPERTY.
 */
public class TaskPropertyDataMeta extends com.cleanwise.service.api.cachecos.TableObjectMeta {

    private static final long serialVersionUID = -1L; //ERROR. No Serial Version Provided
       
    /**
     * Constructor.
     */
    public TaskPropertyDataMeta(TableObject pData) {
        
        super(TaskPropertyDataAccess.CLW_TASK_PROPERTY);

        if (pData != null) {
            TableObjectFieldMeta fm = new TableObjectFieldMeta();
            fm.setField(TaskPropertyDataAccess.TASK_PROPERTY_ID);
            fm.setValue(pData.getFieldValue(TaskPropertyDataAccess.TASK_PROPERTY_ID));
            addField(fm);
        }
    
    }

    /**
     * Constructor.
     */
    public TaskPropertyDataMeta(TableField... fields) {
        
        super(TaskPropertyDataAccess.CLW_TASK_PROPERTY);

        for (TableField field : fields) {
            TableObjectFieldMeta fm = new TableObjectFieldMeta();
            fm.setField(field.getName());
            fm.setValue(field.getValue());
            addField(fm);
        }
    
    }

}
    
