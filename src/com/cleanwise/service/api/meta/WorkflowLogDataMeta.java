
/* DO NOT EDIT - Generated code from XSL file ValueObject.xsl */

package com.cleanwise.service.api.meta;

/**
 * Title:        WorkflowLogDataMeta
 * Description:  This is a meta class describing the object of database table  CLW_WORKFLOW_LOG.
 * Purpose:
 * Copyright:    Copyright (c) 2009
 * Company:      CleanWise, Inc.
 * @author Generated Code from XSL file ValueObjectMeta.xsl
 */

import com.cleanwise.service.api.dao.WorkflowLogDataAccess;
import com.cleanwise.service.api.value.TableObject;
import com.cleanwise.service.api.cachecos.TableObjectFieldMeta;
import com.cleanwise.service.api.cachecos.TableField;

/**
 * <code>WorkflowLogDataMeta</code> is a meta class describing the database table object CLW_WORKFLOW_LOG.
 */
public class WorkflowLogDataMeta extends com.cleanwise.service.api.cachecos.TableObjectMeta {

    private static final long serialVersionUID = -1L; //ERROR. No Serial Version Provided
       
    /**
     * Constructor.
     */
    public WorkflowLogDataMeta(TableObject pData) {
        
        super(WorkflowLogDataAccess.CLW_WORKFLOW_LOG);

        if (pData != null) {
            TableObjectFieldMeta fm = new TableObjectFieldMeta();
            fm.setField(WorkflowLogDataAccess.WORKFLOW_LOG_ID);
            fm.setValue(pData.getFieldValue(WorkflowLogDataAccess.WORKFLOW_LOG_ID));
            addField(fm);
        }
    
    }

    /**
     * Constructor.
     */
    public WorkflowLogDataMeta(TableField... fields) {
        
        super(WorkflowLogDataAccess.CLW_WORKFLOW_LOG);

        for (TableField field : fields) {
            TableObjectFieldMeta fm = new TableObjectFieldMeta();
            fm.setField(field.getName());
            fm.setValue(field.getValue());
            addField(fm);
        }
    
    }

}
    
