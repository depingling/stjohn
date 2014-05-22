
/* DO NOT EDIT - Generated code from XSL file ValueObject.xsl */

package com.cleanwise.service.api.meta;

/**
 * Title:        OrderGuideDataMeta
 * Description:  This is a meta class describing the object of database table  CLW_ORDER_GUIDE.
 * Purpose:
 * Copyright:    Copyright (c) 2009
 * Company:      CleanWise, Inc.
 * @author Generated Code from XSL file ValueObjectMeta.xsl
 */

import com.cleanwise.service.api.dao.OrderGuideDataAccess;
import com.cleanwise.service.api.value.TableObject;
import com.cleanwise.service.api.cachecos.TableObjectFieldMeta;
import com.cleanwise.service.api.cachecos.TableField;

/**
 * <code>OrderGuideDataMeta</code> is a meta class describing the database table object CLW_ORDER_GUIDE.
 */
public class OrderGuideDataMeta extends com.cleanwise.service.api.cachecos.TableObjectMeta {

    private static final long serialVersionUID = -1L; //ERROR. No Serial Version Provided
       
    /**
     * Constructor.
     */
    public OrderGuideDataMeta(TableObject pData) {
        
        super(OrderGuideDataAccess.CLW_ORDER_GUIDE);

        if (pData != null) {
            TableObjectFieldMeta fm = new TableObjectFieldMeta();
            fm.setField(OrderGuideDataAccess.ORDER_GUIDE_ID);
            fm.setValue(pData.getFieldValue(OrderGuideDataAccess.ORDER_GUIDE_ID));
            addField(fm);
        }
    
    }

    /**
     * Constructor.
     */
    public OrderGuideDataMeta(TableField... fields) {
        
        super(OrderGuideDataAccess.CLW_ORDER_GUIDE);

        for (TableField field : fields) {
            TableObjectFieldMeta fm = new TableObjectFieldMeta();
            fm.setField(field.getName());
            fm.setValue(field.getValue());
            addField(fm);
        }
    
    }

}
    
