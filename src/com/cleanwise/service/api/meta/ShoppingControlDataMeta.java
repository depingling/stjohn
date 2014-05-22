
/* DO NOT EDIT - Generated code from XSL file ValueObject.xsl */

package com.cleanwise.service.api.meta;

/**
 * Title:        ShoppingControlDataMeta
 * Description:  This is a meta class describing the object of database table  CLW_SHOPPING_CONTROL.
 * Purpose:
 * Copyright:    Copyright (c) 2009
 * Company:      CleanWise, Inc.
 * @author Generated Code from XSL file ValueObjectMeta.xsl
 */

import com.cleanwise.service.api.dao.ShoppingControlDataAccess;
import com.cleanwise.service.api.value.TableObject;
import com.cleanwise.service.api.cachecos.TableObjectFieldMeta;
import com.cleanwise.service.api.cachecos.TableField;

/**
 * <code>ShoppingControlDataMeta</code> is a meta class describing the database table object CLW_SHOPPING_CONTROL.
 */
public class ShoppingControlDataMeta extends com.cleanwise.service.api.cachecos.TableObjectMeta {

    private static final long serialVersionUID = -1L; //ERROR. No Serial Version Provided
       
    /**
     * Constructor.
     */
    public ShoppingControlDataMeta(TableObject pData) {
        
        super(ShoppingControlDataAccess.CLW_SHOPPING_CONTROL);

        if (pData != null) {
            TableObjectFieldMeta fm = new TableObjectFieldMeta();
            fm.setField(ShoppingControlDataAccess.SHOPPING_CONTROL_ID);
            fm.setValue(pData.getFieldValue(ShoppingControlDataAccess.SHOPPING_CONTROL_ID));
            addField(fm);
        }
    
    }

    /**
     * Constructor.
     */
    public ShoppingControlDataMeta(TableField... fields) {
        
        super(ShoppingControlDataAccess.CLW_SHOPPING_CONTROL);

        for (TableField field : fields) {
            TableObjectFieldMeta fm = new TableObjectFieldMeta();
            fm.setField(field.getName());
            fm.setValue(field.getValue());
            addField(fm);
        }
    
    }

}
    
