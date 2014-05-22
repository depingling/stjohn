
/* DO NOT EDIT - Generated code from XSL file ValueObject.xsl */

package com.cleanwise.service.api.meta;

/**
 * Title:        SiteWorkflowDataMeta
 * Description:  This is a meta class describing the object of database table  CLW_SITE_WORKFLOW.
 * Purpose:
 * Copyright:    Copyright (c) 2009
 * Company:      CleanWise, Inc.
 * @author Generated Code from XSL file ValueObjectMeta.xsl
 */

import com.cleanwise.service.api.dao.SiteWorkflowDataAccess;
import com.cleanwise.service.api.value.TableObject;
import com.cleanwise.service.api.cachecos.TableObjectFieldMeta;
import com.cleanwise.service.api.cachecos.TableField;

/**
 * <code>SiteWorkflowDataMeta</code> is a meta class describing the database table object CLW_SITE_WORKFLOW.
 */
public class SiteWorkflowDataMeta extends com.cleanwise.service.api.cachecos.TableObjectMeta {

    private static final long serialVersionUID = -1L; //ERROR. No Serial Version Provided
       
    /**
     * Constructor.
     */
    public SiteWorkflowDataMeta(TableObject pData) {
        
        super(SiteWorkflowDataAccess.CLW_SITE_WORKFLOW);

        if (pData != null) {
            TableObjectFieldMeta fm = new TableObjectFieldMeta();
            fm.setField(SiteWorkflowDataAccess.SITE_WORKFLOW_ID);
            fm.setValue(pData.getFieldValue(SiteWorkflowDataAccess.SITE_WORKFLOW_ID));
            addField(fm);
        }
    
    }

    /**
     * Constructor.
     */
    public SiteWorkflowDataMeta(TableField... fields) {
        
        super(SiteWorkflowDataAccess.CLW_SITE_WORKFLOW);

        for (TableField field : fields) {
            TableObjectFieldMeta fm = new TableObjectFieldMeta();
            fm.setField(field.getName());
            fm.setValue(field.getValue());
            addField(fm);
        }
    
    }

}
    
