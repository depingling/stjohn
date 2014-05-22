
/* DO NOT EDIT - Generated code from XSL file ValueObject.xsl */

package com.cleanwise.service.api.meta;

/**
 * Title:        AssetAssocDataMeta
 * Description:  This is a meta class describing the object of database table  CLW_ASSET_ASSOC.
 * Purpose:
 * Copyright:    Copyright (c) 2009
 * Company:      CleanWise, Inc.
 * @author Generated Code from XSL file ValueObjectMeta.xsl
 */

import com.cleanwise.service.api.dao.AssetAssocDataAccess;
import com.cleanwise.service.api.value.TableObject;
import com.cleanwise.service.api.cachecos.TableObjectFieldMeta;
import com.cleanwise.service.api.cachecos.TableField;

/**
 * <code>AssetAssocDataMeta</code> is a meta class describing the database table object CLW_ASSET_ASSOC.
 */
public class AssetAssocDataMeta extends com.cleanwise.service.api.cachecos.TableObjectMeta {

    private static final long serialVersionUID = -1L; //ERROR. No Serial Version Provided
       
    /**
     * Constructor.
     */
    public AssetAssocDataMeta(TableObject pData) {
        
        super(AssetAssocDataAccess.CLW_ASSET_ASSOC);

        if (pData != null) {
            TableObjectFieldMeta fm = new TableObjectFieldMeta();
            fm.setField(AssetAssocDataAccess.ASSET_ASSOC_ID);
            fm.setValue(pData.getFieldValue(AssetAssocDataAccess.ASSET_ASSOC_ID));
            addField(fm);
        }
    
    }

    /**
     * Constructor.
     */
    public AssetAssocDataMeta(TableField... fields) {
        
        super(AssetAssocDataAccess.CLW_ASSET_ASSOC);

        for (TableField field : fields) {
            TableObjectFieldMeta fm = new TableObjectFieldMeta();
            fm.setField(field.getName());
            fm.setValue(field.getValue());
            addField(fm);
        }
    
    }

}
    
