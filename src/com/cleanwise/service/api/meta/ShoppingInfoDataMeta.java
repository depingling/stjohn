
/* DO NOT EDIT - Generated code from XSL file ValueObject.xsl */

package com.cleanwise.service.api.meta;

/**
 * Title:        ShoppingInfoDataMeta
 * Description:  This is a meta class describing the object of database table  CLW_SHOPPING_INFO.
 * Purpose:
 * Copyright:    Copyright (c) 2009
 * Company:      CleanWise, Inc.
 * @author Generated Code from XSL file ValueObjectMeta.xsl
 */

import com.cleanwise.service.api.dao.ShoppingInfoDataAccess;
import com.cleanwise.service.api.value.TableObject;
import com.cleanwise.service.api.cachecos.TableObjectFieldMeta;
import com.cleanwise.service.api.cachecos.TableField;

/**
 * <code>ShoppingInfoDataMeta</code> is a meta class describing the database table object CLW_SHOPPING_INFO.
 */
public class ShoppingInfoDataMeta extends com.cleanwise.service.api.cachecos.TableObjectMeta {

    private static final long serialVersionUID = -1L; //ERROR. No Serial Version Provided
       
    /**
     * Constructor.
     */
    public ShoppingInfoDataMeta(TableObject pData) {
        
        super(ShoppingInfoDataAccess.CLW_SHOPPING_INFO);

        if (pData != null) {
            TableObjectFieldMeta fm = new TableObjectFieldMeta();
            fm.setField(ShoppingInfoDataAccess.SHOPPING_INFO_ID);
            fm.setValue(pData.getFieldValue(ShoppingInfoDataAccess.SHOPPING_INFO_ID));
            addField(fm);
        }
    
    }

    /**
     * Constructor.
     */
    public ShoppingInfoDataMeta(TableField... fields) {
        
        super(ShoppingInfoDataAccess.CLW_SHOPPING_INFO);

        for (TableField field : fields) {
            TableObjectFieldMeta fm = new TableObjectFieldMeta();
            fm.setField(field.getName());
            fm.setValue(field.getValue());
            addField(fm);
        }
    
    }

}
    
