
/* DO NOT EDIT - Generated code from XSL file ValueObject.xsl */

package com.cleanwise.service.api.meta;

/**
 * Title:        PipelineLogDataMeta
 * Description:  This is a meta class describing the object of database table  CLW_PIPELINE_LOG.
 * Purpose:
 * Copyright:    Copyright (c) 2009
 * Company:      CleanWise, Inc.
 * @author Generated Code from XSL file ValueObjectMeta.xsl
 */

import com.cleanwise.service.api.dao.PipelineLogDataAccess;
import com.cleanwise.service.api.value.TableObject;
import com.cleanwise.service.api.cachecos.TableObjectFieldMeta;
import com.cleanwise.service.api.cachecos.TableField;

/**
 * <code>PipelineLogDataMeta</code> is a meta class describing the database table object CLW_PIPELINE_LOG.
 */
public class PipelineLogDataMeta extends com.cleanwise.service.api.cachecos.TableObjectMeta {

    private static final long serialVersionUID = -1L; //ERROR. No Serial Version Provided
       
    /**
     * Constructor.
     */
    public PipelineLogDataMeta(TableObject pData) {
        
        super(PipelineLogDataAccess.CLW_PIPELINE_LOG);

        if (pData != null) {
            TableObjectFieldMeta fm = new TableObjectFieldMeta();
            fm.setField(PipelineLogDataAccess.PIPELINE_LOG_ID);
            fm.setValue(pData.getFieldValue(PipelineLogDataAccess.PIPELINE_LOG_ID));
            addField(fm);
        }
    
    }

    /**
     * Constructor.
     */
    public PipelineLogDataMeta(TableField... fields) {
        
        super(PipelineLogDataAccess.CLW_PIPELINE_LOG);

        for (TableField field : fields) {
            TableObjectFieldMeta fm = new TableObjectFieldMeta();
            fm.setField(field.getName());
            fm.setValue(field.getValue());
            addField(fm);
        }
    
    }

}
    
