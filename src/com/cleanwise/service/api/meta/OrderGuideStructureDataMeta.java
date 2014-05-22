
/* DO NOT EDIT - Generated code from XSL file ValueObject.xsl */

package com.cleanwise.service.api.meta;

/**
 * Title:        OrderGuideStructureDataMeta
 * Description:  This is a meta class describing the object of database table  CLW_ORDER_GUIDE_STRUCTURE.
 * Purpose:
 * Copyright:    Copyright (c) 2009
 * Company:      CleanWise, Inc.
 * @author Generated Code from XSL file ValueObjectMeta.xsl
 */

import com.cleanwise.service.api.dao.OrderGuideStructureDataAccess;
import com.cleanwise.service.api.value.TableObject;
import com.cleanwise.service.api.cachecos.TableObjectFieldMeta;
import com.cleanwise.service.api.cachecos.TableField;

/**
 * <code>OrderGuideStructureDataMeta</code> is a meta class describing the database table object CLW_ORDER_GUIDE_STRUCTURE.
 */
public class OrderGuideStructureDataMeta extends com.cleanwise.service.api.cachecos.TableObjectMeta {

    private static final long serialVersionUID = -1L; //ERROR. No Serial Version Provided
       
    /**
     * Constructor.
     */
    public OrderGuideStructureDataMeta(TableObject pData) {
        
        super(OrderGuideStructureDataAccess.CLW_ORDER_GUIDE_STRUCTURE);

        if (pData != null) {
            TableObjectFieldMeta fm = new TableObjectFieldMeta();
            fm.setField(OrderGuideStructureDataAccess.ORDER_GUIDE_STRUCTURE_ID);
            fm.setValue(pData.getFieldValue(OrderGuideStructureDataAccess.ORDER_GUIDE_STRUCTURE_ID));
            addField(fm);
        }
    
    }

    /**
     * Constructor.
     */
    public OrderGuideStructureDataMeta(TableField... fields) {
        
        super(OrderGuideStructureDataAccess.CLW_ORDER_GUIDE_STRUCTURE);

        for (TableField field : fields) {
            TableObjectFieldMeta fm = new TableObjectFieldMeta();
            fm.setField(field.getName());
            fm.setValue(field.getValue());
            addField(fm);
        }
    
    }

}
    
