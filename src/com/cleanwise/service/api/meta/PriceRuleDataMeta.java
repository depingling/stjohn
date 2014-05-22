
/* DO NOT EDIT - Generated code from XSL file ValueObject.xsl */

package com.cleanwise.service.api.meta;

/**
 * Title:        PriceRuleDataMeta
 * Description:  This is a meta class describing the object of database table  CLW_PRICE_RULE.
 * Purpose:
 * Copyright:    Copyright (c) 2009
 * Company:      CleanWise, Inc.
 * @author Generated Code from XSL file ValueObjectMeta.xsl
 */

import com.cleanwise.service.api.dao.PriceRuleDataAccess;
import com.cleanwise.service.api.value.TableObject;
import com.cleanwise.service.api.cachecos.TableObjectFieldMeta;
import com.cleanwise.service.api.cachecos.TableField;

/**
 * <code>PriceRuleDataMeta</code> is a meta class describing the database table object CLW_PRICE_RULE.
 */
public class PriceRuleDataMeta extends com.cleanwise.service.api.cachecos.TableObjectMeta {

    private static final long serialVersionUID = -1L; //ERROR. No Serial Version Provided
       
    /**
     * Constructor.
     */
    public PriceRuleDataMeta(TableObject pData) {
        
        super(PriceRuleDataAccess.CLW_PRICE_RULE);

        if (pData != null) {
            TableObjectFieldMeta fm = new TableObjectFieldMeta();
            fm.setField(PriceRuleDataAccess.PRICE_RULE_ID);
            fm.setValue(pData.getFieldValue(PriceRuleDataAccess.PRICE_RULE_ID));
            addField(fm);
        }
    
    }

    /**
     * Constructor.
     */
    public PriceRuleDataMeta(TableField... fields) {
        
        super(PriceRuleDataAccess.CLW_PRICE_RULE);

        for (TableField field : fields) {
            TableObjectFieldMeta fm = new TableObjectFieldMeta();
            fm.setField(field.getName());
            fm.setValue(field.getValue());
            addField(fm);
        }
    
    }

}
    
