
/* DO NOT EDIT - Generated code from XSL file ValueObject.xsl */

package com.cleanwise.service.api.meta;

/**
 * Title:        WorkOrderDataMeta
 * Description:  This is a meta class describing the object of database table  CLW_WORK_ORDER.
 * Purpose:
 * Copyright:    Copyright (c) 2009
 * Company:      CleanWise, Inc.
 * @author Generated Code from XSL file ValueObjectMeta.xsl
 */

import com.cleanwise.service.api.dao.WorkOrderDataAccess;
import com.cleanwise.service.api.value.TableObject;
import com.cleanwise.service.api.cachecos.TableObjectFieldMeta;
import com.cleanwise.service.api.cachecos.TableField;

/**
 * <code>WorkOrderDataMeta</code> is a meta class describing the database table object CLW_WORK_ORDER.
 */
public class WorkOrderDataMeta extends com.cleanwise.service.api.cachecos.TableObjectMeta {

    private static final long serialVersionUID = -1L; //ERROR. No Serial Version Provided
       
    /**
     * Constructor.
     */
    public WorkOrderDataMeta(TableObject pData) {
        
        super(WorkOrderDataAccess.CLW_WORK_ORDER);

        if (pData != null) {
            TableObjectFieldMeta fm = new TableObjectFieldMeta();
            fm.setField(WorkOrderDataAccess.WORK_ORDER_ID);
            fm.setValue(pData.getFieldValue(WorkOrderDataAccess.WORK_ORDER_ID));
            addField(fm);
        }
    
    }

    /**
     * Constructor.
     */
    public WorkOrderDataMeta(TableField... fields) {
        
        super(WorkOrderDataAccess.CLW_WORK_ORDER);

        for (TableField field : fields) {
            TableObjectFieldMeta fm = new TableObjectFieldMeta();
            fm.setField(field.getName());
            fm.setValue(field.getValue());
            addField(fm);
        }
    
    }

}
    
