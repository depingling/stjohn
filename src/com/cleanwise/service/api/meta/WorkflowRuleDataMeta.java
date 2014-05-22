
/* DO NOT EDIT - Generated code from XSL file ValueObject.xsl */

package com.cleanwise.service.api.meta;

/**
 * Title:        WorkflowRuleDataMeta
 * Description:  This is a meta class describing the object of database table  CLW_WORKFLOW_RULE.
 * Purpose:
 * Copyright:    Copyright (c) 2009
 * Company:      CleanWise, Inc.
 * @author Generated Code from XSL file ValueObjectMeta.xsl
 */

import com.cleanwise.service.api.dao.WorkflowRuleDataAccess;
import com.cleanwise.service.api.value.TableObject;
import com.cleanwise.service.api.cachecos.TableObjectFieldMeta;
import com.cleanwise.service.api.cachecos.TableField;

/**
 * <code>WorkflowRuleDataMeta</code> is a meta class describing the database table object CLW_WORKFLOW_RULE.
 */
public class WorkflowRuleDataMeta extends com.cleanwise.service.api.cachecos.TableObjectMeta {

    private static final long serialVersionUID = -1L; //ERROR. No Serial Version Provided
       
    /**
     * Constructor.
     */
    public WorkflowRuleDataMeta(TableObject pData) {
        
        super(WorkflowRuleDataAccess.CLW_WORKFLOW_RULE);

        if (pData != null) {
            TableObjectFieldMeta fm = new TableObjectFieldMeta();
            fm.setField(WorkflowRuleDataAccess.WORKFLOW_RULE_ID);
            fm.setValue(pData.getFieldValue(WorkflowRuleDataAccess.WORKFLOW_RULE_ID));
            addField(fm);
        }
    
    }

    /**
     * Constructor.
     */
    public WorkflowRuleDataMeta(TableField... fields) {
        
        super(WorkflowRuleDataAccess.CLW_WORKFLOW_RULE);

        for (TableField field : fields) {
            TableObjectFieldMeta fm = new TableObjectFieldMeta();
            fm.setField(field.getName());
            fm.setValue(field.getValue());
            addField(fm);
        }
    
    }

}
    
