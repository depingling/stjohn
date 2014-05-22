
/* DO NOT EDIT - Generated code from XSL file ValueObject.xsl */

package com.cleanwise.service.api.meta;

/**
 * Title:        WorkOrderAssetDataMeta
 * Description:  This is a meta class describing the object of database table  CLW_WORK_ORDER_ASSET.
 * Purpose:
 * Copyright:    Copyright (c) 2009
 * Company:      CleanWise, Inc.
 * @author Generated Code from XSL file ValueObjectMeta.xsl
 */

import com.cleanwise.service.api.dao.WorkOrderAssetDataAccess;
import com.cleanwise.service.api.value.TableObject;
import com.cleanwise.service.api.cachecos.TableObjectFieldMeta;
import com.cleanwise.service.api.cachecos.TableField;

/**
 * <code>WorkOrderAssetDataMeta</code> is a meta class describing the database table object CLW_WORK_ORDER_ASSET.
 */
public class WorkOrderAssetDataMeta extends com.cleanwise.service.api.cachecos.TableObjectMeta {

    private static final long serialVersionUID = -1L; //ERROR. No Serial Version Provided
       
    /**
     * Constructor.
     */
    public WorkOrderAssetDataMeta(TableObject pData) {
        
        super(WorkOrderAssetDataAccess.CLW_WORK_ORDER_ASSET);

        if (pData != null) {
            TableObjectFieldMeta fm = new TableObjectFieldMeta();
            fm.setField(WorkOrderAssetDataAccess.WORK_ORDER_ASSET_ID);
            fm.setValue(pData.getFieldValue(WorkOrderAssetDataAccess.WORK_ORDER_ASSET_ID));
            addField(fm);
        }
    
    }

    /**
     * Constructor.
     */
    public WorkOrderAssetDataMeta(TableField... fields) {
        
        super(WorkOrderAssetDataAccess.CLW_WORK_ORDER_ASSET);

        for (TableField field : fields) {
            TableObjectFieldMeta fm = new TableObjectFieldMeta();
            fm.setField(field.getName());
            fm.setValue(field.getValue());
            addField(fm);
        }
    
    }

}
    
