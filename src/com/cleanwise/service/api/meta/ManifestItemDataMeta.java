
/* DO NOT EDIT - Generated code from XSL file ValueObject.xsl */

package com.cleanwise.service.api.meta;

/**
 * Title:        ManifestItemDataMeta
 * Description:  This is a meta class describing the object of database table  CLW_MANIFEST_ITEM.
 * Purpose:
 * Copyright:    Copyright (c) 2009
 * Company:      CleanWise, Inc.
 * @author Generated Code from XSL file ValueObjectMeta.xsl
 */

import com.cleanwise.service.api.dao.ManifestItemDataAccess;
import com.cleanwise.service.api.value.TableObject;
import com.cleanwise.service.api.cachecos.TableObjectFieldMeta;
import com.cleanwise.service.api.cachecos.TableField;

/**
 * <code>ManifestItemDataMeta</code> is a meta class describing the database table object CLW_MANIFEST_ITEM.
 */
public class ManifestItemDataMeta extends com.cleanwise.service.api.cachecos.TableObjectMeta {

    private static final long serialVersionUID = -1L; //ERROR. No Serial Version Provided
       
    /**
     * Constructor.
     */
    public ManifestItemDataMeta(TableObject pData) {
        
        super(ManifestItemDataAccess.CLW_MANIFEST_ITEM);

        if (pData != null) {
            TableObjectFieldMeta fm = new TableObjectFieldMeta();
            fm.setField(ManifestItemDataAccess.MANIFEST_ITEM_ID);
            fm.setValue(pData.getFieldValue(ManifestItemDataAccess.MANIFEST_ITEM_ID));
            addField(fm);
        }
    
    }

    /**
     * Constructor.
     */
    public ManifestItemDataMeta(TableField... fields) {
        
        super(ManifestItemDataAccess.CLW_MANIFEST_ITEM);

        for (TableField field : fields) {
            TableObjectFieldMeta fm = new TableObjectFieldMeta();
            fm.setField(field.getName());
            fm.setValue(field.getValue());
            addField(fm);
        }
    
    }

}
    
