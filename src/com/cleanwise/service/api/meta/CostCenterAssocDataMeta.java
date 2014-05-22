
/* DO NOT EDIT - Generated code from XSL file ValueObject.xsl */

package com.cleanwise.service.api.meta;

/**
 * Title:        CostCenterAssocDataMeta
 * Description:  This is a meta class describing the object of database table  CLW_COST_CENTER_ASSOC.
 * Purpose:
 * Copyright:    Copyright (c) 2009
 * Company:      CleanWise, Inc.
 * @author Generated Code from XSL file ValueObjectMeta.xsl
 */

import com.cleanwise.service.api.dao.CostCenterAssocDataAccess;
import com.cleanwise.service.api.value.TableObject;
import com.cleanwise.service.api.cachecos.TableObjectFieldMeta;
import com.cleanwise.service.api.cachecos.TableField;

/**
 * <code>CostCenterAssocDataMeta</code> is a meta class describing the database table object CLW_COST_CENTER_ASSOC.
 */
public class CostCenterAssocDataMeta extends com.cleanwise.service.api.cachecos.TableObjectMeta {

    private static final long serialVersionUID = -1L; //ERROR. No Serial Version Provided
       
    /**
     * Constructor.
     */
    public CostCenterAssocDataMeta(TableObject pData) {
        
        super(CostCenterAssocDataAccess.CLW_COST_CENTER_ASSOC);

        if (pData != null) {
            TableObjectFieldMeta fm = new TableObjectFieldMeta();
            fm.setField(CostCenterAssocDataAccess.COST_CENTER_ASSOC_ID);
            fm.setValue(pData.getFieldValue(CostCenterAssocDataAccess.COST_CENTER_ASSOC_ID));
            addField(fm);
        }
    
    }

    /**
     * Constructor.
     */
    public CostCenterAssocDataMeta(TableField... fields) {
        
        super(CostCenterAssocDataAccess.CLW_COST_CENTER_ASSOC);

        for (TableField field : fields) {
            TableObjectFieldMeta fm = new TableObjectFieldMeta();
            fm.setField(field.getName());
            fm.setValue(field.getValue());
            addField(fm);
        }
    
    }

}
    
