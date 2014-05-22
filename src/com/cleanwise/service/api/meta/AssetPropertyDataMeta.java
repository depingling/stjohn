
/* DO NOT EDIT - Generated code from XSL file ValueObject.xsl */

package com.cleanwise.service.api.meta;

/**
 * Title:        AssetPropertyDataMeta
 * Description:  This is a meta class describing the object of database table  CLW_ASSET_PROPERTY.
 * Purpose:
 * Copyright:    Copyright (c) 2009
 * Company:      CleanWise, Inc.
 * @author Generated Code from XSL file ValueObjectMeta.xsl
 */

import com.cleanwise.service.api.dao.AssetPropertyDataAccess;
import com.cleanwise.service.api.value.TableObject;
import com.cleanwise.service.api.cachecos.TableObjectFieldMeta;
import com.cleanwise.service.api.cachecos.TableField;

/**
 * <code>AssetPropertyDataMeta</code> is a meta class describing the database table object CLW_ASSET_PROPERTY.
 */
public class AssetPropertyDataMeta extends com.cleanwise.service.api.cachecos.TableObjectMeta {

    private static final long serialVersionUID = -1L; //ERROR. No Serial Version Provided
       
    /**
     * Constructor.
     */
    public AssetPropertyDataMeta(TableObject pData) {
        
        super(AssetPropertyDataAccess.CLW_ASSET_PROPERTY);

        if (pData != null) {
            TableObjectFieldMeta fm = new TableObjectFieldMeta();
            fm.setField(AssetPropertyDataAccess.ASSET_PROPERTY_ID);
            fm.setValue(pData.getFieldValue(AssetPropertyDataAccess.ASSET_PROPERTY_ID));
            addField(fm);
        }
    
    }

    /**
     * Constructor.
     */
    public AssetPropertyDataMeta(TableField... fields) {
        
        super(AssetPropertyDataAccess.CLW_ASSET_PROPERTY);

        for (TableField field : fields) {
            TableObjectFieldMeta fm = new TableObjectFieldMeta();
            fm.setField(field.getName());
            fm.setValue(field.getValue());
            addField(fm);
        }
    
    }

}
    
