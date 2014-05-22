
/* DO NOT EDIT - Generated code from XSL file ValueObject.xsl */

package com.cleanwise.service.api.meta;

/**
 * Title:        WorkOrderItemDataMeta
 * Description:  This is a meta class describing the object of database table  CLW_WORK_ORDER_ITEM.
 * Purpose:
 * Copyright:    Copyright (c) 2009
 * Company:      CleanWise, Inc.
 * @author Generated Code from XSL file ValueObjectMeta.xsl
 */

import com.cleanwise.service.api.dao.WorkOrderItemDataAccess;
import com.cleanwise.service.api.value.TableObject;
import com.cleanwise.service.api.cachecos.TableObjectFieldMeta;
import com.cleanwise.service.api.cachecos.TableField;

/**
 * <code>WorkOrderItemDataMeta</code> is a meta class describing the database table object CLW_WORK_ORDER_ITEM.
 */
public class WorkOrderItemDataMeta extends com.cleanwise.service.api.cachecos.TableObjectMeta {

    private static final long serialVersionUID = -1L; //ERROR. No Serial Version Provided
       
    /**
     * Constructor.
     */
    public WorkOrderItemDataMeta(TableObject pData) {
        
        super(WorkOrderItemDataAccess.CLW_WORK_ORDER_ITEM);

        if (pData != null) {
            TableObjectFieldMeta fm = new TableObjectFieldMeta();
            fm.setField(WorkOrderItemDataAccess.WORK_ORDER_ITEM_ID);
            fm.setValue(pData.getFieldValue(WorkOrderItemDataAccess.WORK_ORDER_ITEM_ID));
            addField(fm);
        }
    
    }

    /**
     * Constructor.
     */
    public WorkOrderItemDataMeta(TableField... fields) {
        
        super(WorkOrderItemDataAccess.CLW_WORK_ORDER_ITEM);

        for (TableField field : fields) {
            TableObjectFieldMeta fm = new TableObjectFieldMeta();
            fm.setField(field.getName());
            fm.setValue(field.getValue());
            addField(fm);
        }
    
    }

}
    
