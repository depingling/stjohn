
/* DO NOT EDIT - Generated code from XSL file ValueObject.xsl */

package com.cleanwise.service.api.meta;

/**
 * Title:        OrderGuideStructDelDataMeta
 * Description:  This is a meta class describing the object of database table  CLW_ORDER_GUIDE_STRUCT_DEL.
 * Purpose:
 * Copyright:    Copyright (c) 2009
 * Company:      CleanWise, Inc.
 * @author Generated Code from XSL file ValueObjectMeta.xsl
 */

import com.cleanwise.service.api.dao.OrderGuideStructDelDataAccess;
import com.cleanwise.service.api.value.TableObject;
import com.cleanwise.service.api.cachecos.TableObjectFieldMeta;
import com.cleanwise.service.api.cachecos.TableField;

/**
 * <code>OrderGuideStructDelDataMeta</code> is a meta class describing the database table object CLW_ORDER_GUIDE_STRUCT_DEL.
 */
public class OrderGuideStructDelDataMeta extends com.cleanwise.service.api.cachecos.TableObjectMeta {

    private static final long serialVersionUID = -1L; //ERROR. No Serial Version Provided
       
    /**
     * Constructor.
     */
    public OrderGuideStructDelDataMeta(TableObject pData) {
        
        super(OrderGuideStructDelDataAccess.CLW_ORDER_GUIDE_STRUCT_DEL);

        if (pData != null) {
            TableObjectFieldMeta fm = new TableObjectFieldMeta();
            fm.setField(OrderGuideStructDelDataAccess.ORDER_GUIDE_STRUCT_DEL_ID);
            fm.setValue(pData.getFieldValue(OrderGuideStructDelDataAccess.ORDER_GUIDE_STRUCT_DEL_ID));
            addField(fm);
        }
    
    }

    /**
     * Constructor.
     */
    public OrderGuideStructDelDataMeta(TableField... fields) {
        
        super(OrderGuideStructDelDataAccess.CLW_ORDER_GUIDE_STRUCT_DEL);

        for (TableField field : fields) {
            TableObjectFieldMeta fm = new TableObjectFieldMeta();
            fm.setField(field.getName());
            fm.setValue(field.getValue());
            addField(fm);
        }
    
    }

}
    
