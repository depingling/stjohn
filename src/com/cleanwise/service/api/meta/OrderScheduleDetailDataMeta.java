
/* DO NOT EDIT - Generated code from XSL file ValueObject.xsl */

package com.cleanwise.service.api.meta;

/**
 * Title:        OrderScheduleDetailDataMeta
 * Description:  This is a meta class describing the object of database table  CLW_ORDER_SCHEDULE_DETAIL.
 * Purpose:
 * Copyright:    Copyright (c) 2009
 * Company:      CleanWise, Inc.
 * @author Generated Code from XSL file ValueObjectMeta.xsl
 */

import com.cleanwise.service.api.dao.OrderScheduleDetailDataAccess;
import com.cleanwise.service.api.value.TableObject;
import com.cleanwise.service.api.cachecos.TableObjectFieldMeta;
import com.cleanwise.service.api.cachecos.TableField;

/**
 * <code>OrderScheduleDetailDataMeta</code> is a meta class describing the database table object CLW_ORDER_SCHEDULE_DETAIL.
 */
public class OrderScheduleDetailDataMeta extends com.cleanwise.service.api.cachecos.TableObjectMeta {

    private static final long serialVersionUID = -1L; //ERROR. No Serial Version Provided
       
    /**
     * Constructor.
     */
    public OrderScheduleDetailDataMeta(TableObject pData) {
        
        super(OrderScheduleDetailDataAccess.CLW_ORDER_SCHEDULE_DETAIL);

        if (pData != null) {
            TableObjectFieldMeta fm = new TableObjectFieldMeta();
            fm.setField(OrderScheduleDetailDataAccess.ORDER_SCHEDULE_DETAIL_ID);
            fm.setValue(pData.getFieldValue(OrderScheduleDetailDataAccess.ORDER_SCHEDULE_DETAIL_ID));
            addField(fm);
        }
    
    }

    /**
     * Constructor.
     */
    public OrderScheduleDetailDataMeta(TableField... fields) {
        
        super(OrderScheduleDetailDataAccess.CLW_ORDER_SCHEDULE_DETAIL);

        for (TableField field : fields) {
            TableObjectFieldMeta fm = new TableObjectFieldMeta();
            fm.setField(field.getName());
            fm.setValue(field.getValue());
            addField(fm);
        }
    
    }

}
    
