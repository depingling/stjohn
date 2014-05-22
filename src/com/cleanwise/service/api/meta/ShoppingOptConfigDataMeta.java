
/* DO NOT EDIT - Generated code from XSL file ValueObject.xsl */

package com.cleanwise.service.api.meta;

/**
 * Title:        ShoppingOptConfigDataMeta
 * Description:  This is a meta class describing the object of database table  CLW_SHOPPING_OPT_CONFIG.
 * Purpose:
 * Copyright:    Copyright (c) 2009
 * Company:      CleanWise, Inc.
 * @author Generated Code from XSL file ValueObjectMeta.xsl
 */

import com.cleanwise.service.api.dao.ShoppingOptConfigDataAccess;
import com.cleanwise.service.api.value.TableObject;
import com.cleanwise.service.api.cachecos.TableObjectFieldMeta;
import com.cleanwise.service.api.cachecos.TableField;

/**
 * <code>ShoppingOptConfigDataMeta</code> is a meta class describing the database table object CLW_SHOPPING_OPT_CONFIG.
 */
public class ShoppingOptConfigDataMeta extends com.cleanwise.service.api.cachecos.TableObjectMeta {

    private static final long serialVersionUID = -1L; //ERROR. No Serial Version Provided
       
    /**
     * Constructor.
     */
    public ShoppingOptConfigDataMeta(TableObject pData) {
        
        super(ShoppingOptConfigDataAccess.CLW_SHOPPING_OPT_CONFIG);

        if (pData != null) {
            TableObjectFieldMeta fm = new TableObjectFieldMeta();
            fm.setField(ShoppingOptConfigDataAccess.SHOPPING_OPT_CONFIG_ID);
            fm.setValue(pData.getFieldValue(ShoppingOptConfigDataAccess.SHOPPING_OPT_CONFIG_ID));
            addField(fm);
        }
    
    }

    /**
     * Constructor.
     */
    public ShoppingOptConfigDataMeta(TableField... fields) {
        
        super(ShoppingOptConfigDataAccess.CLW_SHOPPING_OPT_CONFIG);

        for (TableField field : fields) {
            TableObjectFieldMeta fm = new TableObjectFieldMeta();
            fm.setField(field.getName());
            fm.setValue(field.getValue());
            addField(fm);
        }
    
    }

}
    
