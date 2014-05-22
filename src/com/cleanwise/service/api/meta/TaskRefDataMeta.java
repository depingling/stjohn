
/* DO NOT EDIT - Generated code from XSL file ValueObject.xsl */

package com.cleanwise.service.api.meta;

/**
 * Title:        TaskRefDataMeta
 * Description:  This is a meta class describing the object of database table  CLW_TASK_REF.
 * Purpose:
 * Copyright:    Copyright (c) 2009
 * Company:      CleanWise, Inc.
 * @author Generated Code from XSL file ValueObjectMeta.xsl
 */

import com.cleanwise.service.api.dao.TaskRefDataAccess;
import com.cleanwise.service.api.value.TableObject;
import com.cleanwise.service.api.cachecos.TableObjectFieldMeta;
import com.cleanwise.service.api.cachecos.TableField;

/**
 * <code>TaskRefDataMeta</code> is a meta class describing the database table object CLW_TASK_REF.
 */
public class TaskRefDataMeta extends com.cleanwise.service.api.cachecos.TableObjectMeta {

    private static final long serialVersionUID = -1L; //ERROR. No Serial Version Provided
       
    /**
     * Constructor.
     */
    public TaskRefDataMeta(TableObject pData) {
        
        super(TaskRefDataAccess.CLW_TASK_REF);

        if (pData != null) {
            TableObjectFieldMeta fm = new TableObjectFieldMeta();
            fm.setField(TaskRefDataAccess.TASK_REF_ID);
            fm.setValue(pData.getFieldValue(TaskRefDataAccess.TASK_REF_ID));
            addField(fm);
        }
    
    }

    /**
     * Constructor.
     */
    public TaskRefDataMeta(TableField... fields) {
        
        super(TaskRefDataAccess.CLW_TASK_REF);

        for (TableField field : fields) {
            TableObjectFieldMeta fm = new TableObjectFieldMeta();
            fm.setField(field.getName());
            fm.setValue(field.getValue());
            addField(fm);
        }
    
    }

}
    
