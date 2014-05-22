
/* DO NOT EDIT - Generated code from XSL file ValueObject.xsl */

package com.cleanwise.service.api.meta;

/**
 * Title:        WorkflowAssocDataMeta
 * Description:  This is a meta class describing the object of database table  CLW_WORKFLOW_ASSOC.
 * Purpose:
 * Copyright:    Copyright (c) 2009
 * Company:      CleanWise, Inc.
 * @author Generated Code from XSL file ValueObjectMeta.xsl
 */

import com.cleanwise.service.api.dao.WorkflowAssocDataAccess;
import com.cleanwise.service.api.value.TableObject;
import com.cleanwise.service.api.cachecos.TableObjectFieldMeta;
import com.cleanwise.service.api.cachecos.TableField;

/**
 * <code>WorkflowAssocDataMeta</code> is a meta class describing the database table object CLW_WORKFLOW_ASSOC.
 */
public class WorkflowAssocDataMeta extends com.cleanwise.service.api.cachecos.TableObjectMeta {

    private static final long serialVersionUID = -1L; //ERROR. No Serial Version Provided
       
    /**
     * Constructor.
     */
    public WorkflowAssocDataMeta(TableObject pData) {
        
        super(WorkflowAssocDataAccess.CLW_WORKFLOW_ASSOC);

        if (pData != null) {
            TableObjectFieldMeta fm = new TableObjectFieldMeta();
            fm.setField(WorkflowAssocDataAccess.WORKFLOW_ASSOC_ID);
            fm.setValue(pData.getFieldValue(WorkflowAssocDataAccess.WORKFLOW_ASSOC_ID));
            addField(fm);
        }
    
    }

    /**
     * Constructor.
     */
    public WorkflowAssocDataMeta(TableField... fields) {
        
        super(WorkflowAssocDataAccess.CLW_WORKFLOW_ASSOC);

        for (TableField field : fields) {
            TableObjectFieldMeta fm = new TableObjectFieldMeta();
            fm.setField(field.getName());
            fm.setValue(field.getValue());
            addField(fm);
        }
    
    }

}
    
