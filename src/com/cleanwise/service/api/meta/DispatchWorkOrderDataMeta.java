
/* DO NOT EDIT - Generated code from XSL file ValueObject.xsl */

package com.cleanwise.service.api.meta;

/**
 * Title:        DispatchWorkOrderDataMeta
 * Description:  This is a meta class describing the object of database table  CLW_DISPATCH_WORK_ORDER.
 * Purpose:
 * Copyright:    Copyright (c) 2009
 * Company:      CleanWise, Inc.
 * @author Generated Code from XSL file ValueObjectMeta.xsl
 */

import com.cleanwise.service.api.dao.DispatchWorkOrderDataAccess;
import com.cleanwise.service.api.value.TableObject;
import com.cleanwise.service.api.cachecos.TableObjectFieldMeta;
import com.cleanwise.service.api.cachecos.TableField;

/**
 * <code>DispatchWorkOrderDataMeta</code> is a meta class describing the database table object CLW_DISPATCH_WORK_ORDER.
 */
public class DispatchWorkOrderDataMeta extends com.cleanwise.service.api.cachecos.TableObjectMeta {

    private static final long serialVersionUID = -1L; //ERROR. No Serial Version Provided
       
    /**
     * Constructor.
     */
    public DispatchWorkOrderDataMeta(TableObject pData) {
        
        super(DispatchWorkOrderDataAccess.CLW_DISPATCH_WORK_ORDER);

        if (pData != null) {
            TableObjectFieldMeta fm = new TableObjectFieldMeta();
            fm.setField(DispatchWorkOrderDataAccess.DISPATCH_WORK_ORDER_ID);
            fm.setValue(pData.getFieldValue(DispatchWorkOrderDataAccess.DISPATCH_WORK_ORDER_ID));
            addField(fm);
        }
    
    }

    /**
     * Constructor.
     */
    public DispatchWorkOrderDataMeta(TableField... fields) {
        
        super(DispatchWorkOrderDataAccess.CLW_DISPATCH_WORK_ORDER);

        for (TableField field : fields) {
            TableObjectFieldMeta fm = new TableObjectFieldMeta();
            fm.setField(field.getName());
            fm.setValue(field.getValue());
            addField(fm);
        }
    
    }

}
    
