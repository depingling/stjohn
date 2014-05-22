
/* DO NOT EDIT - Generated code from XSL file ValueObject.xsl */

package com.cleanwise.service.api.meta;

/**
 * Title:        WorkOrderPropertyDataMeta
 * Description:  This is a meta class describing the object of database table  CLW_WORK_ORDER_PROPERTY.
 * Purpose:
 * Copyright:    Copyright (c) 2009
 * Company:      CleanWise, Inc.
 * @author Generated Code from XSL file ValueObjectMeta.xsl
 */

import com.cleanwise.service.api.dao.WorkOrderPropertyDataAccess;
import com.cleanwise.service.api.value.TableObject;
import com.cleanwise.service.api.cachecos.TableObjectFieldMeta;
import com.cleanwise.service.api.cachecos.TableField;

/**
 * <code>WorkOrderPropertyDataMeta</code> is a meta class describing the database table object CLW_WORK_ORDER_PROPERTY.
 */
public class WorkOrderPropertyDataMeta extends com.cleanwise.service.api.cachecos.TableObjectMeta {

    private static final long serialVersionUID = -1L; //ERROR. No Serial Version Provided
       
    /**
     * Constructor.
     */
    public WorkOrderPropertyDataMeta(TableObject pData) {
        
        super(WorkOrderPropertyDataAccess.CLW_WORK_ORDER_PROPERTY);

        if (pData != null) {
            TableObjectFieldMeta fm = new TableObjectFieldMeta();
            fm.setField(WorkOrderPropertyDataAccess.WORK_ORDER_PROPERTY_ID);
            fm.setValue(pData.getFieldValue(WorkOrderPropertyDataAccess.WORK_ORDER_PROPERTY_ID));
            addField(fm);
        }
    
    }

    /**
     * Constructor.
     */
    public WorkOrderPropertyDataMeta(TableField... fields) {
        
        super(WorkOrderPropertyDataAccess.CLW_WORK_ORDER_PROPERTY);

        for (TableField field : fields) {
            TableObjectFieldMeta fm = new TableObjectFieldMeta();
            fm.setField(field.getName());
            fm.setValue(field.getValue());
            addField(fm);
        }
    
    }

}
    
