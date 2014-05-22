
/* DO NOT EDIT - Generated code from XSL file ValueObject.xsl */

package com.cleanwise.service.api.meta;

/**
 * Title:        OrderScheduleDataMeta
 * Description:  This is a meta class describing the object of database table  CLW_ORDER_SCHEDULE.
 * Purpose:
 * Copyright:    Copyright (c) 2009
 * Company:      CleanWise, Inc.
 * @author Generated Code from XSL file ValueObjectMeta.xsl
 */

import com.cleanwise.service.api.dao.OrderScheduleDataAccess;
import com.cleanwise.service.api.value.TableObject;
import com.cleanwise.service.api.cachecos.TableObjectFieldMeta;
import com.cleanwise.service.api.cachecos.TableField;

/**
 * <code>OrderScheduleDataMeta</code> is a meta class describing the database table object CLW_ORDER_SCHEDULE.
 */
public class OrderScheduleDataMeta extends com.cleanwise.service.api.cachecos.TableObjectMeta {

    private static final long serialVersionUID = -1L; //ERROR. No Serial Version Provided
       
    /**
     * Constructor.
     */
    public OrderScheduleDataMeta(TableObject pData) {
        
        super(OrderScheduleDataAccess.CLW_ORDER_SCHEDULE);

        if (pData != null) {
            TableObjectFieldMeta fm = new TableObjectFieldMeta();
            fm.setField(OrderScheduleDataAccess.ORDER_SCHEDULE_ID);
            fm.setValue(pData.getFieldValue(OrderScheduleDataAccess.ORDER_SCHEDULE_ID));
            addField(fm);
        }
    
    }

    /**
     * Constructor.
     */
    public OrderScheduleDataMeta(TableField... fields) {
        
        super(OrderScheduleDataAccess.CLW_ORDER_SCHEDULE);

        for (TableField field : fields) {
            TableObjectFieldMeta fm = new TableObjectFieldMeta();
            fm.setField(field.getName());
            fm.setValue(field.getValue());
            addField(fm);
        }
    
    }

}
    
