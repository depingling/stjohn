
/* DO NOT EDIT - Generated code from XSL file ValueObject.xsl */

package com.cleanwise.service.api.meta;

/**
 * Title:        AssetDataMeta
 * Description:  This is a meta class describing the object of database table  CLW_ASSET.
 * Purpose:
 * Copyright:    Copyright (c) 2009
 * Company:      CleanWise, Inc.
 * @author Generated Code from XSL file ValueObjectMeta.xsl
 */

import com.cleanwise.service.api.dao.AssetDataAccess;
import com.cleanwise.service.api.value.TableObject;
import com.cleanwise.service.api.cachecos.TableObjectFieldMeta;
import com.cleanwise.service.api.cachecos.TableField;

/**
 * <code>AssetDataMeta</code> is a meta class describing the database table object CLW_ASSET.
 */
public class AssetDataMeta extends com.cleanwise.service.api.cachecos.TableObjectMeta {

    private static final long serialVersionUID = -1L; //ERROR. No Serial Version Provided
       
    /**
     * Constructor.
     */
    public AssetDataMeta(TableObject pData) {
        
        super(AssetDataAccess.CLW_ASSET);

        if (pData != null) {
            TableObjectFieldMeta fm = new TableObjectFieldMeta();
            fm.setField(AssetDataAccess.ASSET_ID);
            fm.setValue(pData.getFieldValue(AssetDataAccess.ASSET_ID));
            addField(fm);
        }
    
    }

    /**
     * Constructor.
     */
    public AssetDataMeta(TableField... fields) {
        
        super(AssetDataAccess.CLW_ASSET);

        for (TableField field : fields) {
            TableObjectFieldMeta fm = new TableObjectFieldMeta();
            fm.setField(field.getName());
            fm.setValue(field.getValue());
            addField(fm);
        }
    
    }

}
    
