
/* DO NOT EDIT - Generated code from XSL file ValueObject.xsl */

package com.cleanwise.service.api.meta;

/**
 * Title:        WorkflowQueueDataMeta
 * Description:  This is a meta class describing the object of database table  CLW_WORKFLOW_QUEUE.
 * Purpose:
 * Copyright:    Copyright (c) 2009
 * Company:      CleanWise, Inc.
 * @author Generated Code from XSL file ValueObjectMeta.xsl
 */

import com.cleanwise.service.api.dao.WorkflowQueueDataAccess;
import com.cleanwise.service.api.value.TableObject;
import com.cleanwise.service.api.cachecos.TableObjectFieldMeta;
import com.cleanwise.service.api.cachecos.TableField;

/**
 * <code>WorkflowQueueDataMeta</code> is a meta class describing the database table object CLW_WORKFLOW_QUEUE.
 */
public class WorkflowQueueDataMeta extends com.cleanwise.service.api.cachecos.TableObjectMeta {

    private static final long serialVersionUID = -1L; //ERROR. No Serial Version Provided
       
    /**
     * Constructor.
     */
    public WorkflowQueueDataMeta(TableObject pData) {
        
        super(WorkflowQueueDataAccess.CLW_WORKFLOW_QUEUE);

        if (pData != null) {
            TableObjectFieldMeta fm = new TableObjectFieldMeta();
            fm.setField(WorkflowQueueDataAccess.WORKFLOW_QUEUE_ID);
            fm.setValue(pData.getFieldValue(WorkflowQueueDataAccess.WORKFLOW_QUEUE_ID));
            addField(fm);
        }
    
    }

    /**
     * Constructor.
     */
    public WorkflowQueueDataMeta(TableField... fields) {
        
        super(WorkflowQueueDataAccess.CLW_WORKFLOW_QUEUE);

        for (TableField field : fields) {
            TableObjectFieldMeta fm = new TableObjectFieldMeta();
            fm.setField(field.getName());
            fm.setValue(field.getValue());
            addField(fm);
        }
    
    }

}
    
