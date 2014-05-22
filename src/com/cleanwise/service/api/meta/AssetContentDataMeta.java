
/* DO NOT EDIT - Generated code from XSL file ValueObject.xsl */

package com.cleanwise.service.api.meta;

/**
 * Title:        AssetContentDataMeta
 * Description:  This is a meta class describing the object of database table  CLW_ASSET_CONTENT.
 * Purpose:
 * Copyright:    Copyright (c) 2009
 * Company:      CleanWise, Inc.
 * @author Generated Code from XSL file ValueObjectMeta.xsl
 */

import com.cleanwise.service.api.dao.AssetContentDataAccess;
import com.cleanwise.service.api.value.TableObject;
import com.cleanwise.service.api.cachecos.TableObjectFieldMeta;
import com.cleanwise.service.api.cachecos.TableField;

/**
 * <code>AssetContentDataMeta</code> is a meta class describing the database table object CLW_ASSET_CONTENT.
 */
public class AssetContentDataMeta extends com.cleanwise.service.api.cachecos.TableObjectMeta {

    private static final long serialVersionUID = -1L; //ERROR. No Serial Version Provided
       
    /**
     * Constructor.
     */
    public AssetContentDataMeta(TableObject pData) {
        
        super(AssetContentDataAccess.CLW_ASSET_CONTENT);

        if (pData != null) {
            TableObjectFieldMeta fm = new TableObjectFieldMeta();
            fm.setField(AssetContentDataAccess.ASSET_CONTENT_ID);
            fm.setValue(pData.getFieldValue(AssetContentDataAccess.ASSET_CONTENT_ID));
            addField(fm);
        }
    
    }

    /**
     * Constructor.
     */
    public AssetContentDataMeta(TableField... fields) {
        
        super(AssetContentDataAccess.CLW_ASSET_CONTENT);

        for (TableField field : fields) {
            TableObjectFieldMeta fm = new TableObjectFieldMeta();
            fm.setField(field.getName());
            fm.setValue(field.getValue());
            addField(fm);
        }
    
    }

}
    
