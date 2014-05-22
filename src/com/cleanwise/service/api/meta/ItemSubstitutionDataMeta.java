
/* DO NOT EDIT - Generated code from XSL file ValueObject.xsl */

package com.cleanwise.service.api.meta;

/**
 * Title:        ItemSubstitutionDataMeta
 * Description:  This is a meta class describing the object of database table  CLW_ITEM_SUBSTITUTION.
 * Purpose:
 * Copyright:    Copyright (c) 2009
 * Company:      CleanWise, Inc.
 * @author Generated Code from XSL file ValueObjectMeta.xsl
 */

import com.cleanwise.service.api.dao.ItemSubstitutionDataAccess;
import com.cleanwise.service.api.value.TableObject;
import com.cleanwise.service.api.cachecos.TableObjectFieldMeta;
import com.cleanwise.service.api.cachecos.TableField;

/**
 * <code>ItemSubstitutionDataMeta</code> is a meta class describing the database table object CLW_ITEM_SUBSTITUTION.
 */
public class ItemSubstitutionDataMeta extends com.cleanwise.service.api.cachecos.TableObjectMeta {

    private static final long serialVersionUID = -1L; //ERROR. No Serial Version Provided
       
    /**
     * Constructor.
     */
    public ItemSubstitutionDataMeta(TableObject pData) {
        
        super(ItemSubstitutionDataAccess.CLW_ITEM_SUBSTITUTION);

        if (pData != null) {
            TableObjectFieldMeta fm = new TableObjectFieldMeta();
            fm.setField(ItemSubstitutionDataAccess.ITEM_SUBSTITUTION_ID);
            fm.setValue(pData.getFieldValue(ItemSubstitutionDataAccess.ITEM_SUBSTITUTION_ID));
            addField(fm);
        }
    
    }

    /**
     * Constructor.
     */
    public ItemSubstitutionDataMeta(TableField... fields) {
        
        super(ItemSubstitutionDataAccess.CLW_ITEM_SUBSTITUTION);

        for (TableField field : fields) {
            TableObjectFieldMeta fm = new TableObjectFieldMeta();
            fm.setField(field.getName());
            fm.setValue(field.getValue());
            addField(fm);
        }
    
    }

}
    
