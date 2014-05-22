
/* DO NOT EDIT - Generated code from XSL file ValueObject.xsl */

package com.cleanwise.service.api.meta;

/**
 * Title:        OrderMetaDataMeta
 * Description:  This is a meta class describing the object of database table  CLW_ORDER_META.
 * Purpose:
 * Copyright:    Copyright (c) 2009
 * Company:      CleanWise, Inc.
 * @author Generated Code from XSL file ValueObjectMeta.xsl
 */

import com.cleanwise.service.api.dao.OrderMetaDataAccess;
import com.cleanwise.service.api.value.TableObject;
import com.cleanwise.service.api.cachecos.TableObjectFieldMeta;
import com.cleanwise.service.api.cachecos.TableField;

/**
 * <code>OrderMetaDataMeta</code> is a meta class describing the database table object CLW_ORDER_META.
 */
public class OrderMetaDataMeta extends com.cleanwise.service.api.cachecos.TableObjectMeta {

    private static final long serialVersionUID = -1L; //ERROR. No Serial Version Provided
       
    /**
     * Constructor.
     */
    public OrderMetaDataMeta(TableObject pData) {
        
        super(OrderMetaDataAccess.CLW_ORDER_META);

        if (pData != null) {
            TableObjectFieldMeta fm = new TableObjectFieldMeta();
            fm.setField(OrderMetaDataAccess.ORDER_META_ID);
            fm.setValue(pData.getFieldValue(OrderMetaDataAccess.ORDER_META_ID));
            addField(fm);
        }
    
    }

    /**
     * Constructor.
     */
    public OrderMetaDataMeta(TableField... fields) {
        
        super(OrderMetaDataAccess.CLW_ORDER_META);

        for (TableField field : fields) {
            TableObjectFieldMeta fm = new TableObjectFieldMeta();
            fm.setField(field.getName());
            fm.setValue(field.getValue());
            addField(fm);
        }
    
    }

}
    
