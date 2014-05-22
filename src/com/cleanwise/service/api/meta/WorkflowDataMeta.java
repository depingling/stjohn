
/* DO NOT EDIT - Generated code from XSL file ValueObject.xsl */

package com.cleanwise.service.api.meta;

/**
 * Title:        WorkflowDataMeta
 * Description:  This is a meta class describing the object of database table  CLW_WORKFLOW.
 * Purpose:
 * Copyright:    Copyright (c) 2009
 * Company:      CleanWise, Inc.
 * @author Generated Code from XSL file ValueObjectMeta.xsl
 */

import com.cleanwise.service.api.dao.WorkflowDataAccess;
import com.cleanwise.service.api.value.TableObject;
import com.cleanwise.service.api.cachecos.TableObjectFieldMeta;
import com.cleanwise.service.api.cachecos.TableField;

/**
 * <code>WorkflowDataMeta</code> is a meta class describing the database table object CLW_WORKFLOW.
 */
public class WorkflowDataMeta extends com.cleanwise.service.api.cachecos.TableObjectMeta {

    private static final long serialVersionUID = -1L; //ERROR. No Serial Version Provided
       
    /**
     * Constructor.
     */
    public WorkflowDataMeta(TableObject pData) {
        
        super(WorkflowDataAccess.CLW_WORKFLOW);

        if (pData != null) {
            TableObjectFieldMeta fm = new TableObjectFieldMeta();
            fm.setField(WorkflowDataAccess.WORKFLOW_ID);
            fm.setValue(pData.getFieldValue(WorkflowDataAccess.WORKFLOW_ID));
            addField(fm);
        }
    
    }

    /**
     * Constructor.
     */
    public WorkflowDataMeta(TableField... fields) {
        
        super(WorkflowDataAccess.CLW_WORKFLOW);

        for (TableField field : fields) {
            TableObjectFieldMeta fm = new TableObjectFieldMeta();
            fm.setField(field.getName());
            fm.setValue(field.getValue());
            addField(fm);
        }
    
    }

}
    
