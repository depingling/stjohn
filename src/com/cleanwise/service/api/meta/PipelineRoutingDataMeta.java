
/* DO NOT EDIT - Generated code from XSL file ValueObject.xsl */

package com.cleanwise.service.api.meta;

/**
 * Title:        PipelineRoutingDataMeta
 * Description:  This is a meta class describing the object of database table  CLW_PIPELINE_ROUTING.
 * Purpose:
 * Copyright:    Copyright (c) 2009
 * Company:      CleanWise, Inc.
 * @author Generated Code from XSL file ValueObjectMeta.xsl
 */

import com.cleanwise.service.api.dao.PipelineRoutingDataAccess;
import com.cleanwise.service.api.value.TableObject;
import com.cleanwise.service.api.cachecos.TableObjectFieldMeta;
import com.cleanwise.service.api.cachecos.TableField;

/**
 * <code>PipelineRoutingDataMeta</code> is a meta class describing the database table object CLW_PIPELINE_ROUTING.
 */
public class PipelineRoutingDataMeta extends com.cleanwise.service.api.cachecos.TableObjectMeta {

    private static final long serialVersionUID = -1L; //ERROR. No Serial Version Provided
       
    /**
     * Constructor.
     */
    public PipelineRoutingDataMeta(TableObject pData) {
        
        super(PipelineRoutingDataAccess.CLW_PIPELINE_ROUTING);

        if (pData != null) {
            TableObjectFieldMeta fm = new TableObjectFieldMeta();
            fm.setField(PipelineRoutingDataAccess.PIPELINE_ROUTING_ID);
            fm.setValue(pData.getFieldValue(PipelineRoutingDataAccess.PIPELINE_ROUTING_ID));
            addField(fm);
        }
    
    }

    /**
     * Constructor.
     */
    public PipelineRoutingDataMeta(TableField... fields) {
        
        super(PipelineRoutingDataAccess.CLW_PIPELINE_ROUTING);

        for (TableField field : fields) {
            TableObjectFieldMeta fm = new TableObjectFieldMeta();
            fm.setField(field.getName());
            fm.setValue(field.getValue());
            addField(fm);
        }
    
    }

}
    
