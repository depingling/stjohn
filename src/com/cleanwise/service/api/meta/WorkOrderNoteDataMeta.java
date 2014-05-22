
/* DO NOT EDIT - Generated code from XSL file ValueObject.xsl */

package com.cleanwise.service.api.meta;

/**
 * Title:        WorkOrderNoteDataMeta
 * Description:  This is a meta class describing the object of database table  CLW_WORK_ORDER_NOTE.
 * Purpose:
 * Copyright:    Copyright (c) 2009
 * Company:      CleanWise, Inc.
 * @author Generated Code from XSL file ValueObjectMeta.xsl
 */

import com.cleanwise.service.api.dao.WorkOrderNoteDataAccess;
import com.cleanwise.service.api.value.TableObject;
import com.cleanwise.service.api.cachecos.TableObjectFieldMeta;
import com.cleanwise.service.api.cachecos.TableField;

/**
 * <code>WorkOrderNoteDataMeta</code> is a meta class describing the database table object CLW_WORK_ORDER_NOTE.
 */
public class WorkOrderNoteDataMeta extends com.cleanwise.service.api.cachecos.TableObjectMeta {

    private static final long serialVersionUID = -1L; //ERROR. No Serial Version Provided
       
    /**
     * Constructor.
     */
    public WorkOrderNoteDataMeta(TableObject pData) {
        
        super(WorkOrderNoteDataAccess.CLW_WORK_ORDER_NOTE);

        if (pData != null) {
            TableObjectFieldMeta fm = new TableObjectFieldMeta();
            fm.setField(WorkOrderNoteDataAccess.WORK_ORDER_NOTE_ID);
            fm.setValue(pData.getFieldValue(WorkOrderNoteDataAccess.WORK_ORDER_NOTE_ID));
            addField(fm);
        }
    
    }

    /**
     * Constructor.
     */
    public WorkOrderNoteDataMeta(TableField... fields) {
        
        super(WorkOrderNoteDataAccess.CLW_WORK_ORDER_NOTE);

        for (TableField field : fields) {
            TableObjectFieldMeta fm = new TableObjectFieldMeta();
            fm.setField(field.getName());
            fm.setValue(field.getValue());
            addField(fm);
        }
    
    }

}
    
