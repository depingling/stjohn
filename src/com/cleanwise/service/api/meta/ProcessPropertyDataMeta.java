
/* DO NOT EDIT - Generated code from XSL file ValueObject.xsl */

package com.cleanwise.service.api.meta;

/**
 * Title:        ProcessPropertyDataMeta
 * Description:  This is a meta class describing the object of database table  CLW_PROCESS_PROPERTY.
 * Purpose:
 * Copyright:    Copyright (c) 2009
 * Company:      CleanWise, Inc.
 * @author Generated Code from XSL file ValueObjectMeta.xsl
 */

import com.cleanwise.service.api.dao.ProcessPropertyDataAccess;
import com.cleanwise.service.api.value.TableObject;
import com.cleanwise.service.api.cachecos.TableObjectFieldMeta;
import com.cleanwise.service.api.cachecos.TableField;

/**
 * <code>ProcessPropertyDataMeta</code> is a meta class describing the database table object CLW_PROCESS_PROPERTY.
 */
public class ProcessPropertyDataMeta extends com.cleanwise.service.api.cachecos.TableObjectMeta {

    private static final long serialVersionUID = -1L; //ERROR. No Serial Version Provided
       
    /**
     * Constructor.
     */
    public ProcessPropertyDataMeta(TableObject pData) {
        
        super(ProcessPropertyDataAccess.CLW_PROCESS_PROPERTY);

        if (pData != null) {
            TableObjectFieldMeta fm = new TableObjectFieldMeta();
            fm.setField(ProcessPropertyDataAccess.PROCESS_PROPERTY_ID);
            fm.setValue(pData.getFieldValue(ProcessPropertyDataAccess.PROCESS_PROPERTY_ID));
            addField(fm);
        }
    
    }

    /**
     * Constructor.
     */
    public ProcessPropertyDataMeta(TableField... fields) {
        
        super(ProcessPropertyDataAccess.CLW_PROCESS_PROPERTY);

        for (TableField field : fields) {
            TableObjectFieldMeta fm = new TableObjectFieldMeta();
            fm.setField(field.getName());
            fm.setValue(field.getValue());
            addField(fm);
        }
    
    }

}
    
