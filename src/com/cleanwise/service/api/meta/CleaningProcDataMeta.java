
/* DO NOT EDIT - Generated code from XSL file ValueObject.xsl */

package com.cleanwise.service.api.meta;

/**
 * Title:        CleaningProcDataMeta
 * Description:  This is a meta class describing the object of database table  CLW_CLEANING_PROC.
 * Purpose:
 * Copyright:    Copyright (c) 2009
 * Company:      CleanWise, Inc.
 * @author Generated Code from XSL file ValueObjectMeta.xsl
 */

import com.cleanwise.service.api.dao.CleaningProcDataAccess;
import com.cleanwise.service.api.value.TableObject;
import com.cleanwise.service.api.cachecos.TableObjectFieldMeta;
import com.cleanwise.service.api.cachecos.TableField;

/**
 * <code>CleaningProcDataMeta</code> is a meta class describing the database table object CLW_CLEANING_PROC.
 */
public class CleaningProcDataMeta extends com.cleanwise.service.api.cachecos.TableObjectMeta {

    private static final long serialVersionUID = -1L; //ERROR. No Serial Version Provided
       
    /**
     * Constructor.
     */
    public CleaningProcDataMeta(TableObject pData) {
        
        super(CleaningProcDataAccess.CLW_CLEANING_PROC);

        if (pData != null) {
            TableObjectFieldMeta fm = new TableObjectFieldMeta();
            fm.setField(CleaningProcDataAccess.CLEANING_PROC_ID);
            fm.setValue(pData.getFieldValue(CleaningProcDataAccess.CLEANING_PROC_ID));
            addField(fm);
        }
    
    }

    /**
     * Constructor.
     */
    public CleaningProcDataMeta(TableField... fields) {
        
        super(CleaningProcDataAccess.CLW_CLEANING_PROC);

        for (TableField field : fields) {
            TableObjectFieldMeta fm = new TableObjectFieldMeta();
            fm.setField(field.getName());
            fm.setValue(field.getValue());
            addField(fm);
        }
    
    }

}
    
