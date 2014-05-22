
/* DO NOT EDIT - Generated code from XSL file ValueObject.xsl */

package com.cleanwise.service.api.meta;

/**
 * Title:        CleaningSchedStructDataMeta
 * Description:  This is a meta class describing the object of database table  CLW_CLEANING_SCHED_STRUCT.
 * Purpose:
 * Copyright:    Copyright (c) 2009
 * Company:      CleanWise, Inc.
 * @author Generated Code from XSL file ValueObjectMeta.xsl
 */

import com.cleanwise.service.api.dao.CleaningSchedStructDataAccess;
import com.cleanwise.service.api.value.TableObject;
import com.cleanwise.service.api.cachecos.TableObjectFieldMeta;
import com.cleanwise.service.api.cachecos.TableField;

/**
 * <code>CleaningSchedStructDataMeta</code> is a meta class describing the database table object CLW_CLEANING_SCHED_STRUCT.
 */
public class CleaningSchedStructDataMeta extends com.cleanwise.service.api.cachecos.TableObjectMeta {

    private static final long serialVersionUID = -1L; //ERROR. No Serial Version Provided
       
    /**
     * Constructor.
     */
    public CleaningSchedStructDataMeta(TableObject pData) {
        
        super(CleaningSchedStructDataAccess.CLW_CLEANING_SCHED_STRUCT);

        if (pData != null) {
            TableObjectFieldMeta fm = new TableObjectFieldMeta();
            fm.setField(CleaningSchedStructDataAccess.CLEANING_SCHED_STRUCT_ID);
            fm.setValue(pData.getFieldValue(CleaningSchedStructDataAccess.CLEANING_SCHED_STRUCT_ID));
            addField(fm);
        }
    
    }

    /**
     * Constructor.
     */
    public CleaningSchedStructDataMeta(TableField... fields) {
        
        super(CleaningSchedStructDataAccess.CLW_CLEANING_SCHED_STRUCT);

        for (TableField field : fields) {
            TableObjectFieldMeta fm = new TableObjectFieldMeta();
            fm.setField(field.getName());
            fm.setValue(field.getValue());
            addField(fm);
        }
    
    }

}
    
