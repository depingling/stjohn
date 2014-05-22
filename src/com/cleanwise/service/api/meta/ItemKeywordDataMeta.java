
/* DO NOT EDIT - Generated code from XSL file ValueObject.xsl */

package com.cleanwise.service.api.meta;

/**
 * Title:        ItemKeywordDataMeta
 * Description:  This is a meta class describing the object of database table  CLW_ITEM_KEYWORD.
 * Purpose:
 * Copyright:    Copyright (c) 2009
 * Company:      CleanWise, Inc.
 * @author Generated Code from XSL file ValueObjectMeta.xsl
 */

import com.cleanwise.service.api.dao.ItemKeywordDataAccess;
import com.cleanwise.service.api.value.TableObject;
import com.cleanwise.service.api.cachecos.TableObjectFieldMeta;
import com.cleanwise.service.api.cachecos.TableField;

/**
 * <code>ItemKeywordDataMeta</code> is a meta class describing the database table object CLW_ITEM_KEYWORD.
 */
public class ItemKeywordDataMeta extends com.cleanwise.service.api.cachecos.TableObjectMeta {

    private static final long serialVersionUID = -1L; //ERROR. No Serial Version Provided
       
    /**
     * Constructor.
     */
    public ItemKeywordDataMeta(TableObject pData) {
        
        super(ItemKeywordDataAccess.CLW_ITEM_KEYWORD);

        if (pData != null) {
            TableObjectFieldMeta fm = new TableObjectFieldMeta();
            fm.setField(ItemKeywordDataAccess.ITEM_KEYWORD_ID);
            fm.setValue(pData.getFieldValue(ItemKeywordDataAccess.ITEM_KEYWORD_ID));
            addField(fm);
        }
    
    }

    /**
     * Constructor.
     */
    public ItemKeywordDataMeta(TableField... fields) {
        
        super(ItemKeywordDataAccess.CLW_ITEM_KEYWORD);

        for (TableField field : fields) {
            TableObjectFieldMeta fm = new TableObjectFieldMeta();
            fm.setField(field.getName());
            fm.setValue(field.getValue());
            addField(fm);
        }
    
    }

}
    
