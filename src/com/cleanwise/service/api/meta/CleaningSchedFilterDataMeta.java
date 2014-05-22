
/* DO NOT EDIT - Generated code from XSL file ValueObject.xsl */

package com.cleanwise.service.api.meta;

/**
 * Title:        CleaningSchedFilterDataMeta
 * Description:  This is a meta class describing the object of database table  CLW_CLEANING_SCHED_FILTER.
 * Purpose:
 * Copyright:    Copyright (c) 2009
 * Company:      CleanWise, Inc.
 * @author Generated Code from XSL file ValueObjectMeta.xsl
 */

import com.cleanwise.service.api.dao.CleaningSchedFilterDataAccess;
import com.cleanwise.service.api.value.TableObject;
import com.cleanwise.service.api.cachecos.TableObjectFieldMeta;
import com.cleanwise.service.api.cachecos.TableField;

/**
 * <code>CleaningSchedFilterDataMeta</code> is a meta class describing the database table object CLW_CLEANING_SCHED_FILTER.
 */
public class CleaningSchedFilterDataMeta extends com.cleanwise.service.api.cachecos.TableObjectMeta {

    private static final long serialVersionUID = -1L; //ERROR. No Serial Version Provided
       
    /**
     * Constructor.
     */
    public CleaningSchedFilterDataMeta(TableObject pData) {
        
        super(CleaningSchedFilterDataAccess.CLW_CLEANING_SCHED_FILTER);

        if (pData != null) {
            TableObjectFieldMeta fm = new TableObjectFieldMeta();
            fm.setField(CleaningSchedFilterDataAccess.CLEANING_SCHED_FILTER_ID);
            fm.setValue(pData.getFieldValue(CleaningSchedFilterDataAccess.CLEANING_SCHED_FILTER_ID));
            addField(fm);
        }
    
    }

    /**
     * Constructor.
     */
    public CleaningSchedFilterDataMeta(TableField... fields) {
        
        super(CleaningSchedFilterDataAccess.CLW_CLEANING_SCHED_FILTER);

        for (TableField field : fields) {
            TableObjectFieldMeta fm = new TableObjectFieldMeta();
            fm.setField(field.getName());
            fm.setValue(field.getValue());
            addField(fm);
        }
    
    }

}
    
