
/* DO NOT EDIT - Generated code from XSL file ValueObject.xsl */

package com.cleanwise.service.api.meta;

/**
 * Title:        WorkOrderAssocDataMeta
 * Description:  This is a meta class describing the object of database table  CLW_WORK_ORDER_ASSOC.
 * Purpose:
 * Copyright:    Copyright (c) 2009
 * Company:      CleanWise, Inc.
 * @author Generated Code from XSL file ValueObjectMeta.xsl
 */

import com.cleanwise.service.api.dao.WorkOrderAssocDataAccess;
import com.cleanwise.service.api.value.TableObject;
import com.cleanwise.service.api.cachecos.TableObjectFieldMeta;
import com.cleanwise.service.api.cachecos.TableField;

/**
 * <code>WorkOrderAssocDataMeta</code> is a meta class describing the database table object CLW_WORK_ORDER_ASSOC.
 */
public class WorkOrderAssocDataMeta extends com.cleanwise.service.api.cachecos.TableObjectMeta {

    private static final long serialVersionUID = -1L; //ERROR. No Serial Version Provided
       
    /**
     * Constructor.
     */
    public WorkOrderAssocDataMeta(TableObject pData) {
        
        super(WorkOrderAssocDataAccess.CLW_WORK_ORDER_ASSOC);

        if (pData != null) {
            TableObjectFieldMeta fm = new TableObjectFieldMeta();
            fm.setField(WorkOrderAssocDataAccess.WORK_ORDER_ASSOC_ID);
            fm.setValue(pData.getFieldValue(WorkOrderAssocDataAccess.WORK_ORDER_ASSOC_ID));
            addField(fm);
        }
    
    }

    /**
     * Constructor.
     */
    public WorkOrderAssocDataMeta(TableField... fields) {
        
        super(WorkOrderAssocDataAccess.CLW_WORK_ORDER_ASSOC);

        for (TableField field : fields) {
            TableObjectFieldMeta fm = new TableObjectFieldMeta();
            fm.setField(field.getName());
            fm.setValue(field.getValue());
            addField(fm);
        }
    
    }

}
    
