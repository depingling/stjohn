
/* DO NOT EDIT - Generated code from XSL file ValueObject.xsl */

package com.cleanwise.service.api.meta;

/**
 * Title:        PipelineRoutingLogDataMeta
 * Description:  This is a meta class describing the object of database table  CLW_PIPELINE_ROUTING_LOG.
 * Purpose:
 * Copyright:    Copyright (c) 2009
 * Company:      CleanWise, Inc.
 * @author Generated Code from XSL file ValueObjectMeta.xsl
 */

import com.cleanwise.service.api.dao.PipelineRoutingLogDataAccess;
import com.cleanwise.service.api.value.TableObject;
import com.cleanwise.service.api.cachecos.TableObjectFieldMeta;
import com.cleanwise.service.api.cachecos.TableField;

/**
 * <code>PipelineRoutingLogDataMeta</code> is a meta class describing the database table object CLW_PIPELINE_ROUTING_LOG.
 */
public class PipelineRoutingLogDataMeta extends com.cleanwise.service.api.cachecos.TableObjectMeta {

    private static final long serialVersionUID = -1L; //ERROR. No Serial Version Provided
       
    /**
     * Constructor.
     */
    public PipelineRoutingLogDataMeta(TableObject pData) {
        
        super(PipelineRoutingLogDataAccess.CLW_PIPELINE_ROUTING_LOG);

        if (pData != null) {
            TableObjectFieldMeta fm = new TableObjectFieldMeta();
            fm.setField(PipelineRoutingLogDataAccess.PIPELINE_ROUTING_LOG_ID);
            fm.setValue(pData.getFieldValue(PipelineRoutingLogDataAccess.PIPELINE_ROUTING_LOG_ID));
            addField(fm);
        }
    
    }

    /**
     * Constructor.
     */
    public PipelineRoutingLogDataMeta(TableField... fields) {
        
        super(PipelineRoutingLogDataAccess.CLW_PIPELINE_ROUTING_LOG);

        for (TableField field : fields) {
            TableObjectFieldMeta fm = new TableObjectFieldMeta();
            fm.setField(field.getName());
            fm.setValue(field.getValue());
            addField(fm);
        }
    
    }

}
    
