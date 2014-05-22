
/* DO NOT EDIT - Generated code from XSL file ValueObject.xsl */

package com.cleanwise.service.api.meta;

/**
 * Title:        CostCenterDataMeta
 * Description:  This is a meta class describing the object of database table  CLW_COST_CENTER.
 * Purpose:
 * Copyright:    Copyright (c) 2009
 * Company:      CleanWise, Inc.
 * @author Generated Code from XSL file ValueObjectMeta.xsl
 */

import com.cleanwise.service.api.dao.CostCenterDataAccess;
import com.cleanwise.service.api.value.TableObject;
import com.cleanwise.service.api.cachecos.TableObjectFieldMeta;
import com.cleanwise.service.api.cachecos.TableField;

/**
 * <code>CostCenterDataMeta</code> is a meta class describing the database table object CLW_COST_CENTER.
 */
public class CostCenterDataMeta extends com.cleanwise.service.api.cachecos.TableObjectMeta {

    private static final long serialVersionUID = -1L; //ERROR. No Serial Version Provided
       
    /**
     * Constructor.
     */
    public CostCenterDataMeta(TableObject pData) {
        
        super(CostCenterDataAccess.CLW_COST_CENTER);

        if (pData != null) {
            TableObjectFieldMeta fm = new TableObjectFieldMeta();
            fm.setField(CostCenterDataAccess.COST_CENTER_ID);
            fm.setValue(pData.getFieldValue(CostCenterDataAccess.COST_CENTER_ID));
            addField(fm);
        }
    
    }

    /**
     * Constructor.
     */
    public CostCenterDataMeta(TableField... fields) {
        
        super(CostCenterDataAccess.CLW_COST_CENTER);

        for (TableField field : fields) {
            TableObjectFieldMeta fm = new TableObjectFieldMeta();
            fm.setField(field.getName());
            fm.setValue(field.getValue());
            addField(fm);
        }
    
    }

}
    
