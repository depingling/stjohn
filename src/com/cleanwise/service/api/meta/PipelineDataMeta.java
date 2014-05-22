
/* DO NOT EDIT - Generated code from XSL file ValueObject.xsl */

package com.cleanwise.service.api.meta;

/**
 * Title:        PipelineDataMeta
 * Description:  This is a meta class describing the object of database table  CLW_PIPELINE.
 * Purpose:
 * Copyright:    Copyright (c) 2009
 * Company:      CleanWise, Inc.
 * @author Generated Code from XSL file ValueObjectMeta.xsl
 */

import com.cleanwise.service.api.dao.PipelineDataAccess;
import com.cleanwise.service.api.value.TableObject;
import com.cleanwise.service.api.cachecos.TableObjectFieldMeta;
import com.cleanwise.service.api.cachecos.TableField;

/**
 * <code>PipelineDataMeta</code> is a meta class describing the database table object CLW_PIPELINE.
 */
public class PipelineDataMeta extends com.cleanwise.service.api.cachecos.TableObjectMeta {

    private static final long serialVersionUID = -1L; //ERROR. No Serial Version Provided
       
    /**
     * Constructor.
     */
    public PipelineDataMeta(TableObject pData) {
        
        super(PipelineDataAccess.CLW_PIPELINE);

        if (pData != null) {
            TableObjectFieldMeta fm = new TableObjectFieldMeta();
            fm.setField(PipelineDataAccess.PIPELINE_ID);
            fm.setValue(pData.getFieldValue(PipelineDataAccess.PIPELINE_ID));
            addField(fm);
        }
    
    }

    /**
     * Constructor.
     */
    public PipelineDataMeta(TableField... fields) {
        
        super(PipelineDataAccess.CLW_PIPELINE);

        for (TableField field : fields) {
            TableObjectFieldMeta fm = new TableObjectFieldMeta();
            fm.setField(field.getName());
            fm.setValue(field.getValue());
            addField(fm);
        }
    
    }

}
    
