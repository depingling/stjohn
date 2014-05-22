
/* DO NOT EDIT - Generated code from XSL file ValueObject.xsl */

package com.cleanwise.service.api.meta;

/**
 * Title:        ProcessDataMeta
 * Description:  This is a meta class describing the object of database table  CLW_PROCESS.
 * Purpose:
 * Copyright:    Copyright (c) 2009
 * Company:      CleanWise, Inc.
 * @author Generated Code from XSL file ValueObjectMeta.xsl
 */

import com.cleanwise.service.api.dao.ProcessDataAccess;
import com.cleanwise.service.api.value.TableObject;
import com.cleanwise.service.api.cachecos.TableObjectFieldMeta;
import com.cleanwise.service.api.cachecos.TableField;

/**
 * <code>ProcessDataMeta</code> is a meta class describing the database table object CLW_PROCESS.
 */
public class ProcessDataMeta extends com.cleanwise.service.api.cachecos.TableObjectMeta {

    private static final long serialVersionUID = -1L; //ERROR. No Serial Version Provided
       
    /**
     * Constructor.
     */
    public ProcessDataMeta(TableObject pData) {
        
        super(ProcessDataAccess.CLW_PROCESS);

        if (pData != null) {
            TableObjectFieldMeta fm = new TableObjectFieldMeta();
            fm.setField(ProcessDataAccess.PROCESS_ID);
            fm.setValue(pData.getFieldValue(ProcessDataAccess.PROCESS_ID));
            addField(fm);
        }
    
    }

    /**
     * Constructor.
     */
    public ProcessDataMeta(TableField... fields) {
        
        super(ProcessDataAccess.CLW_PROCESS);

        for (TableField field : fields) {
            TableObjectFieldMeta fm = new TableObjectFieldMeta();
            fm.setField(field.getName());
            fm.setValue(field.getValue());
            addField(fm);
        }
    
    }

}
    
