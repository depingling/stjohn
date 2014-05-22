
/* DO NOT EDIT - Generated code from XSL file ValueObject.xsl */

package com.cleanwise.service.api.meta;

/**
 * Title:        AssetWarrantyDataMeta
 * Description:  This is a meta class describing the object of database table  CLW_ASSET_WARRANTY.
 * Purpose:
 * Copyright:    Copyright (c) 2009
 * Company:      CleanWise, Inc.
 * @author Generated Code from XSL file ValueObjectMeta.xsl
 */

import com.cleanwise.service.api.dao.AssetWarrantyDataAccess;
import com.cleanwise.service.api.value.TableObject;
import com.cleanwise.service.api.cachecos.TableObjectFieldMeta;
import com.cleanwise.service.api.cachecos.TableField;

/**
 * <code>AssetWarrantyDataMeta</code> is a meta class describing the database table object CLW_ASSET_WARRANTY.
 */
public class AssetWarrantyDataMeta extends com.cleanwise.service.api.cachecos.TableObjectMeta {

    private static final long serialVersionUID = -1L; //ERROR. No Serial Version Provided
       
    /**
     * Constructor.
     */
    public AssetWarrantyDataMeta(TableObject pData) {
        
        super(AssetWarrantyDataAccess.CLW_ASSET_WARRANTY);

        if (pData != null) {
            TableObjectFieldMeta fm = new TableObjectFieldMeta();
            fm.setField(AssetWarrantyDataAccess.ASSET_WARRANTY_ID);
            fm.setValue(pData.getFieldValue(AssetWarrantyDataAccess.ASSET_WARRANTY_ID));
            addField(fm);
        }
    
    }

    /**
     * Constructor.
     */
    public AssetWarrantyDataMeta(TableField... fields) {
        
        super(AssetWarrantyDataAccess.CLW_ASSET_WARRANTY);

        for (TableField field : fields) {
            TableObjectFieldMeta fm = new TableObjectFieldMeta();
            fm.setField(field.getName());
            fm.setValue(field.getValue());
            addField(fm);
        }
    
    }

}
    
