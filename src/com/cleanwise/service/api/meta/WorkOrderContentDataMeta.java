
/* DO NOT EDIT - Generated code from XSL file ValueObject.xsl */

package com.cleanwise.service.api.meta;

/**
 * Title:        WorkOrderContentDataMeta
 * Description:  This is a meta class describing the object of database table  CLW_WORK_ORDER_CONTENT.
 * Purpose:
 * Copyright:    Copyright (c) 2009
 * Company:      CleanWise, Inc.
 * @author Generated Code from XSL file ValueObjectMeta.xsl
 */

import com.cleanwise.service.api.dao.WorkOrderContentDataAccess;
import com.cleanwise.service.api.value.TableObject;
import com.cleanwise.service.api.cachecos.TableObjectFieldMeta;
import com.cleanwise.service.api.cachecos.TableField;

/**
 * <code>WorkOrderContentDataMeta</code> is a meta class describing the database table object CLW_WORK_ORDER_CONTENT.
 */
public class WorkOrderContentDataMeta extends com.cleanwise.service.api.cachecos.TableObjectMeta {

    private static final long serialVersionUID = -1L; //ERROR. No Serial Version Provided
       
    /**
     * Constructor.
     */
    public WorkOrderContentDataMeta(TableObject pData) {
        
        super(WorkOrderContentDataAccess.CLW_WORK_ORDER_CONTENT);

        if (pData != null) {
            TableObjectFieldMeta fm = new TableObjectFieldMeta();
            fm.setField(WorkOrderContentDataAccess.WORK_ORDER_CONTENT_ID);
            fm.setValue(pData.getFieldValue(WorkOrderContentDataAccess.WORK_ORDER_CONTENT_ID));
            addField(fm);
        }
    
    }

    /**
     * Constructor.
     */
    public WorkOrderContentDataMeta(TableField... fields) {
        
        super(WorkOrderContentDataAccess.CLW_WORK_ORDER_CONTENT);

        for (TableField field : fields) {
            TableObjectFieldMeta fm = new TableObjectFieldMeta();
            fm.setField(field.getName());
            fm.setValue(field.getValue());
            addField(fm);
        }
    
    }

}
    
