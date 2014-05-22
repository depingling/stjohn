
/* DO NOT EDIT - Generated code from XSL file ValueObject.xsl */

package com.cleanwise.service.api.meta;

/**
 * Title:        PriceRuleDetailDataMeta
 * Description:  This is a meta class describing the object of database table  CLW_PRICE_RULE_DETAIL.
 * Purpose:
 * Copyright:    Copyright (c) 2009
 * Company:      CleanWise, Inc.
 * @author Generated Code from XSL file ValueObjectMeta.xsl
 */

import com.cleanwise.service.api.dao.PriceRuleDetailDataAccess;
import com.cleanwise.service.api.value.TableObject;
import com.cleanwise.service.api.cachecos.TableObjectFieldMeta;
import com.cleanwise.service.api.cachecos.TableField;

/**
 * <code>PriceRuleDetailDataMeta</code> is a meta class describing the database table object CLW_PRICE_RULE_DETAIL.
 */
public class PriceRuleDetailDataMeta extends com.cleanwise.service.api.cachecos.TableObjectMeta {

    private static final long serialVersionUID = -1L; //ERROR. No Serial Version Provided
       
    /**
     * Constructor.
     */
    public PriceRuleDetailDataMeta(TableObject pData) {
        
        super(PriceRuleDetailDataAccess.CLW_PRICE_RULE_DETAIL);

        if (pData != null) {
            TableObjectFieldMeta fm = new TableObjectFieldMeta();
            fm.setField(PriceRuleDetailDataAccess.PRICE_RULE_DETAIL_ID);
            fm.setValue(pData.getFieldValue(PriceRuleDetailDataAccess.PRICE_RULE_DETAIL_ID));
            addField(fm);
        }
    
    }

    /**
     * Constructor.
     */
    public PriceRuleDetailDataMeta(TableField... fields) {
        
        super(PriceRuleDetailDataAccess.CLW_PRICE_RULE_DETAIL);

        for (TableField field : fields) {
            TableObjectFieldMeta fm = new TableObjectFieldMeta();
            fm.setField(field.getName());
            fm.setValue(field.getValue());
            addField(fm);
        }
    
    }

}
    
