
/* DO NOT EDIT - Generated code from XSL file ValueObject.xsl */

package com.cleanwise.service.api.meta;

/**
 * Title:        WorkOrderStatusHistDataMeta
 * Description:  This is a meta class describing the object of database table  CLW_WORK_ORDER_STATUS_HIST.
 * Purpose:
 * Copyright:    Copyright (c) 2009
 * Company:      CleanWise, Inc.
 * @author Generated Code from XSL file ValueObjectMeta.xsl
 */

import com.cleanwise.service.api.dao.WorkOrderStatusHistDataAccess;
import com.cleanwise.service.api.value.TableObject;
import com.cleanwise.service.api.cachecos.TableObjectFieldMeta;
import com.cleanwise.service.api.cachecos.TableField;

/**
 * <code>WorkOrderStatusHistDataMeta</code> is a meta class describing the database table object CLW_WORK_ORDER_STATUS_HIST.
 */
public class WorkOrderStatusHistDataMeta extends com.cleanwise.service.api.cachecos.TableObjectMeta {

    private static final long serialVersionUID = -1L; //ERROR. No Serial Version Provided
       
    /**
     * Constructor.
     */
    public WorkOrderStatusHistDataMeta(TableObject pData) {
        
        super(WorkOrderStatusHistDataAccess.CLW_WORK_ORDER_STATUS_HIST);

        if (pData != null) {
            TableObjectFieldMeta fm = new TableObjectFieldMeta();
            fm.setField(WorkOrderStatusHistDataAccess.WORK_ORDER_STATUS_HIST_ID);
            fm.setValue(pData.getFieldValue(WorkOrderStatusHistDataAccess.WORK_ORDER_STATUS_HIST_ID));
            addField(fm);
        }
    
    }

    /**
     * Constructor.
     */
    public WorkOrderStatusHistDataMeta(TableField... fields) {
        
        super(WorkOrderStatusHistDataAccess.CLW_WORK_ORDER_STATUS_HIST);

        for (TableField field : fields) {
            TableObjectFieldMeta fm = new TableObjectFieldMeta();
            fm.setField(field.getName());
            fm.setValue(field.getValue());
            addField(fm);
        }
    
    }

}
    
