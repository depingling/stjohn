
/* DO NOT EDIT - Generated code from XSL file ValueObject.xsl */

package com.cleanwise.service.api.meta;

/**
 * Title:        BaseCostDataMeta
 * Description:  This is a meta class describing the object of database table  CLW_BASE_COST.
 * Purpose:
 * Copyright:    Copyright (c) 2009
 * Company:      CleanWise, Inc.
 * @author Generated Code from XSL file ValueObjectMeta.xsl
 */

import com.cleanwise.service.api.dao.BaseCostDataAccess;
import com.cleanwise.service.api.value.TableObject;
import com.cleanwise.service.api.cachecos.TableObjectFieldMeta;
import com.cleanwise.service.api.cachecos.TableField;

/**
 * <code>BaseCostDataMeta</code> is a meta class describing the database table object CLW_BASE_COST.
 */
public class BaseCostDataMeta extends com.cleanwise.service.api.cachecos.TableObjectMeta {

    private static final long serialVersionUID = -1L; //ERROR. No Serial Version Provided
       
    /**
     * Constructor.
     */
    public BaseCostDataMeta(TableObject pData) {
        
        super(BaseCostDataAccess.CLW_BASE_COST);

        if (pData != null) {
            TableObjectFieldMeta fm = new TableObjectFieldMeta();
            fm.setField(BaseCostDataAccess.BASE_COST_ID);
            fm.setValue(pData.getFieldValue(BaseCostDataAccess.BASE_COST_ID));
            addField(fm);
        }
    
    }

    /**
     * Constructor.
     */
    public BaseCostDataMeta(TableField... fields) {
        
        super(BaseCostDataAccess.CLW_BASE_COST);

        for (TableField field : fields) {
            TableObjectFieldMeta fm = new TableObjectFieldMeta();
            fm.setField(field.getName());
            fm.setValue(field.getValue());
            addField(fm);
        }
    
    }

}
    
