
/* DO NOT EDIT - Generated code from XSL file ValueObject.xsl */

package com.cleanwise.service.api.meta;

/**
 * Title:        PurchaseOrderDataMeta
 * Description:  This is a meta class describing the object of database table  CLW_PURCHASE_ORDER.
 * Purpose:
 * Copyright:    Copyright (c) 2009
 * Company:      CleanWise, Inc.
 * @author Generated Code from XSL file ValueObjectMeta.xsl
 */

import com.cleanwise.service.api.dao.PurchaseOrderDataAccess;
import com.cleanwise.service.api.value.TableObject;
import com.cleanwise.service.api.cachecos.TableObjectFieldMeta;
import com.cleanwise.service.api.cachecos.TableField;

/**
 * <code>PurchaseOrderDataMeta</code> is a meta class describing the database table object CLW_PURCHASE_ORDER.
 */
public class PurchaseOrderDataMeta extends com.cleanwise.service.api.cachecos.TableObjectMeta {

    private static final long serialVersionUID = -1L; //ERROR. No Serial Version Provided
       
    /**
     * Constructor.
     */
    public PurchaseOrderDataMeta(TableObject pData) {
        
        super(PurchaseOrderDataAccess.CLW_PURCHASE_ORDER);

        if (pData != null) {
            TableObjectFieldMeta fm = new TableObjectFieldMeta();
            fm.setField(PurchaseOrderDataAccess.PURCHASE_ORDER_ID);
            fm.setValue(pData.getFieldValue(PurchaseOrderDataAccess.PURCHASE_ORDER_ID));
            addField(fm);
        }
    
    }

    /**
     * Constructor.
     */
    public PurchaseOrderDataMeta(TableField... fields) {
        
        super(PurchaseOrderDataAccess.CLW_PURCHASE_ORDER);

        for (TableField field : fields) {
            TableObjectFieldMeta fm = new TableObjectFieldMeta();
            fm.setField(field.getName());
            fm.setValue(field.getValue());
            addField(fm);
        }
    
    }

}
    
