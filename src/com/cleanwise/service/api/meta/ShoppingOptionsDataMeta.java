
/* DO NOT EDIT - Generated code from XSL file ValueObject.xsl */

package com.cleanwise.service.api.meta;

/**
 * Title:        ShoppingOptionsDataMeta
 * Description:  This is a meta class describing the object of database table  CLW_SHOPPING_OPTIONS.
 * Purpose:
 * Copyright:    Copyright (c) 2009
 * Company:      CleanWise, Inc.
 * @author Generated Code from XSL file ValueObjectMeta.xsl
 */

import com.cleanwise.service.api.dao.ShoppingOptionsDataAccess;
import com.cleanwise.service.api.value.TableObject;
import com.cleanwise.service.api.cachecos.TableObjectFieldMeta;
import com.cleanwise.service.api.cachecos.TableField;

/**
 * <code>ShoppingOptionsDataMeta</code> is a meta class describing the database table object CLW_SHOPPING_OPTIONS.
 */
public class ShoppingOptionsDataMeta extends com.cleanwise.service.api.cachecos.TableObjectMeta {

    private static final long serialVersionUID = -1L; //ERROR. No Serial Version Provided
       
    /**
     * Constructor.
     */
    public ShoppingOptionsDataMeta(TableObject pData) {
        
        super(ShoppingOptionsDataAccess.CLW_SHOPPING_OPTIONS);

        if (pData != null) {
            TableObjectFieldMeta fm = new TableObjectFieldMeta();
            fm.setField(ShoppingOptionsDataAccess.SHOPPING_OPTIONS_ID);
            fm.setValue(pData.getFieldValue(ShoppingOptionsDataAccess.SHOPPING_OPTIONS_ID));
            addField(fm);
        }
    
    }

    /**
     * Constructor.
     */
    public ShoppingOptionsDataMeta(TableField... fields) {
        
        super(ShoppingOptionsDataAccess.CLW_SHOPPING_OPTIONS);

        for (TableField field : fields) {
            TableObjectFieldMeta fm = new TableObjectFieldMeta();
            fm.setField(field.getName());
            fm.setValue(field.getValue());
            addField(fm);
        }
    
    }

}
    
