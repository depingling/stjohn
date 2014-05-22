
/* DO NOT EDIT - Generated code from XSL file ValueObject.xsl */

package com.cleanwise.service.api.meta;

/**
 * Title:        TaskDataMeta
 * Description:  This is a meta class describing the object of database table  CLW_TASK.
 * Purpose:
 * Copyright:    Copyright (c) 2009
 * Company:      CleanWise, Inc.
 * @author Generated Code from XSL file ValueObjectMeta.xsl
 */

import com.cleanwise.service.api.dao.TaskDataAccess;
import com.cleanwise.service.api.value.TableObject;
import com.cleanwise.service.api.cachecos.TableObjectFieldMeta;
import com.cleanwise.service.api.cachecos.TableField;

/**
 * <code>TaskDataMeta</code> is a meta class describing the database table object CLW_TASK.
 */
public class TaskDataMeta extends com.cleanwise.service.api.cachecos.TableObjectMeta {

    private static final long serialVersionUID = -1L; //ERROR. No Serial Version Provided
       
    /**
     * Constructor.
     */
    public TaskDataMeta(TableObject pData) {
        
        super(TaskDataAccess.CLW_TASK);

        if (pData != null) {
            TableObjectFieldMeta fm = new TableObjectFieldMeta();
            fm.setField(TaskDataAccess.TASK_ID);
            fm.setValue(pData.getFieldValue(TaskDataAccess.TASK_ID));
            addField(fm);
        }
    
    }

    /**
     * Constructor.
     */
    public TaskDataMeta(TableField... fields) {
        
        super(TaskDataAccess.CLW_TASK);

        for (TableField field : fields) {
            TableObjectFieldMeta fm = new TableObjectFieldMeta();
            fm.setField(field.getName());
            fm.setValue(field.getValue());
            addField(fm);
        }
    
    }

}
    
