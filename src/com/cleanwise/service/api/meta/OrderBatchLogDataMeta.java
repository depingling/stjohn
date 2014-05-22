
/* DO NOT EDIT - Generated code from XSL file ValueObject.xsl */

package com.cleanwise.service.api.meta;

/**
 * Title:        OrderBatchLogDataMeta
 * Description:  This is a meta class describing the object of database table  CLW_ORDER_BATCH_LOG.
 * Purpose:
 * Copyright:    Copyright (c) 2009
 * Company:      CleanWise, Inc.
 * @author Generated Code from XSL file ValueObjectMeta.xsl
 */

import com.cleanwise.service.api.dao.OrderBatchLogDataAccess;
import com.cleanwise.service.api.value.TableObject;
import com.cleanwise.service.api.cachecos.TableObjectFieldMeta;
import com.cleanwise.service.api.cachecos.TableField;

/**
 * <code>OrderBatchLogDataMeta</code> is a meta class describing the database table object CLW_ORDER_BATCH_LOG.
 */
public class OrderBatchLogDataMeta extends com.cleanwise.service.api.cachecos.TableObjectMeta {

    private static final long serialVersionUID = -1L; //ERROR. No Serial Version Provided
       
    /**
     * Constructor.
     */
    public OrderBatchLogDataMeta(TableObject pData) {
        
        super(OrderBatchLogDataAccess.CLW_ORDER_BATCH_LOG);

        if (pData != null) {
            TableObjectFieldMeta fm = new TableObjectFieldMeta();
            fm.setField(OrderBatchLogDataAccess.ORDER_BATCH_LOG_ID);
            fm.setValue(pData.getFieldValue(OrderBatchLogDataAccess.ORDER_BATCH_LOG_ID));
            addField(fm);
        }
    
    }

    /**
     * Constructor.
     */
    public OrderBatchLogDataMeta(TableField... fields) {
        
        super(OrderBatchLogDataAccess.CLW_ORDER_BATCH_LOG);

        for (TableField field : fields) {
            TableObjectFieldMeta fm = new TableObjectFieldMeta();
            fm.setField(field.getName());
            fm.setValue(field.getValue());
            addField(fm);
        }
    
    }

}
    
