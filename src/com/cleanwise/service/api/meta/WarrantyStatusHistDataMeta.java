
/* DO NOT EDIT - Generated code from XSL file ValueObject.xsl */

package com.cleanwise.service.api.meta;

/**
 * Title:        WarrantyStatusHistDataMeta
 * Description:  This is a meta class describing the object of database table  CLW_WARRANTY_STATUS_HIST.
 * Purpose:
 * Copyright:    Copyright (c) 2009
 * Company:      CleanWise, Inc.
 * @author Generated Code from XSL file ValueObjectMeta.xsl
 */

import com.cleanwise.service.api.dao.WarrantyStatusHistDataAccess;
import com.cleanwise.service.api.value.TableObject;
import com.cleanwise.service.api.cachecos.TableObjectFieldMeta;
import com.cleanwise.service.api.cachecos.TableField;

/**
 * <code>WarrantyStatusHistDataMeta</code> is a meta class describing the database table object CLW_WARRANTY_STATUS_HIST.
 */
public class WarrantyStatusHistDataMeta extends com.cleanwise.service.api.cachecos.TableObjectMeta {

    private static final long serialVersionUID = -1L; //ERROR. No Serial Version Provided
       
    /**
     * Constructor.
     */
    public WarrantyStatusHistDataMeta(TableObject pData) {
        
        super(WarrantyStatusHistDataAccess.CLW_WARRANTY_STATUS_HIST);

        if (pData != null) {
            TableObjectFieldMeta fm = new TableObjectFieldMeta();
            fm.setField(WarrantyStatusHistDataAccess.WARRANTY_STATUS_HIST_ID);
            fm.setValue(pData.getFieldValue(WarrantyStatusHistDataAccess.WARRANTY_STATUS_HIST_ID));
            addField(fm);
        }
    
    }

    /**
     * Constructor.
     */
    public WarrantyStatusHistDataMeta(TableField... fields) {
        
        super(WarrantyStatusHistDataAccess.CLW_WARRANTY_STATUS_HIST);

        for (TableField field : fields) {
            TableObjectFieldMeta fm = new TableObjectFieldMeta();
            fm.setField(field.getName());
            fm.setValue(field.getValue());
            addField(fm);
        }
    
    }

}
    
