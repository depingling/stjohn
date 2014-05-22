
/* DO NOT EDIT - Generated code from XSL file ValueObject.xsl */

package com.cleanwise.service.api.meta;

/**
 * Title:        ItemSubstitutionDefDataMeta
 * Description:  This is a meta class describing the object of database table  CLW_ITEM_SUBSTITUTION_DEF.
 * Purpose:
 * Copyright:    Copyright (c) 2009
 * Company:      CleanWise, Inc.
 * @author Generated Code from XSL file ValueObjectMeta.xsl
 */

import com.cleanwise.service.api.dao.ItemSubstitutionDefDataAccess;
import com.cleanwise.service.api.value.TableObject;
import com.cleanwise.service.api.cachecos.TableObjectFieldMeta;
import com.cleanwise.service.api.cachecos.TableField;

/**
 * <code>ItemSubstitutionDefDataMeta</code> is a meta class describing the database table object CLW_ITEM_SUBSTITUTION_DEF.
 */
public class ItemSubstitutionDefDataMeta extends com.cleanwise.service.api.cachecos.TableObjectMeta {

    private static final long serialVersionUID = -1L; //ERROR. No Serial Version Provided
       
    /**
     * Constructor.
     */
    public ItemSubstitutionDefDataMeta(TableObject pData) {
        
        super(ItemSubstitutionDefDataAccess.CLW_ITEM_SUBSTITUTION_DEF);

        if (pData != null) {
            TableObjectFieldMeta fm = new TableObjectFieldMeta();
            fm.setField(ItemSubstitutionDefDataAccess.ITEM_SUBSTITUTION_DEF_ID);
            fm.setValue(pData.getFieldValue(ItemSubstitutionDefDataAccess.ITEM_SUBSTITUTION_DEF_ID));
            addField(fm);
        }
    
    }

    /**
     * Constructor.
     */
    public ItemSubstitutionDefDataMeta(TableField... fields) {
        
        super(ItemSubstitutionDefDataAccess.CLW_ITEM_SUBSTITUTION_DEF);

        for (TableField field : fields) {
            TableObjectFieldMeta fm = new TableObjectFieldMeta();
            fm.setField(field.getName());
            fm.setValue(field.getValue());
            addField(fm);
        }
    
    }

}
    
