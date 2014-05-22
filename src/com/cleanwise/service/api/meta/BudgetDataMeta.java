
/* DO NOT EDIT - Generated code from XSL file ValueObject.xsl */

package com.cleanwise.service.api.meta;

/**
 * Title:        BudgetDataMeta
 * Description:  This is a meta class describing the object of database table  CLW_BUDGET.
 * Purpose:
 * Copyright:    Copyright (c) 2009
 * Company:      CleanWise, Inc.
 * @author Generated Code from XSL file ValueObjectMeta.xsl
 */

import com.cleanwise.service.api.dao.BudgetDataAccess;
import com.cleanwise.service.api.value.TableObject;
import com.cleanwise.service.api.cachecos.TableObjectFieldMeta;
import com.cleanwise.service.api.cachecos.TableField;

/**
 * <code>BudgetDataMeta</code> is a meta class describing the database table object CLW_BUDGET.
 */
public class BudgetDataMeta extends com.cleanwise.service.api.cachecos.TableObjectMeta {

    private static final long serialVersionUID = -1L; //ERROR. No Serial Version Provided
       
    /**
     * Constructor.
     */
    public BudgetDataMeta(TableObject pData) {
        
        super(BudgetDataAccess.CLW_BUDGET);

        if (pData != null) {
            TableObjectFieldMeta fm = new TableObjectFieldMeta();
            fm.setField(BudgetDataAccess.BUDGET_ID);
            fm.setValue(pData.getFieldValue(BudgetDataAccess.BUDGET_ID));
            addField(fm);
        }
    
    }

    /**
     * Constructor.
     */
    public BudgetDataMeta(TableField... fields) {
        
        super(BudgetDataAccess.CLW_BUDGET);

        for (TableField field : fields) {
            TableObjectFieldMeta fm = new TableObjectFieldMeta();
            fm.setField(field.getName());
            fm.setValue(field.getValue());
            addField(fm);
        }
    
    }

}
    
