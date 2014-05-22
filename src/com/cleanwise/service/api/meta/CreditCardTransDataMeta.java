
/* DO NOT EDIT - Generated code from XSL file ValueObject.xsl */

package com.cleanwise.service.api.meta;

/**
 * Title:        CreditCardTransDataMeta
 * Description:  This is a meta class describing the object of database table  CLW_CREDIT_CARD_TRANS.
 * Purpose:
 * Copyright:    Copyright (c) 2009
 * Company:      CleanWise, Inc.
 * @author Generated Code from XSL file ValueObjectMeta.xsl
 */

import com.cleanwise.service.api.dao.CreditCardTransDataAccess;
import com.cleanwise.service.api.value.TableObject;
import com.cleanwise.service.api.cachecos.TableObjectFieldMeta;
import com.cleanwise.service.api.cachecos.TableField;

/**
 * <code>CreditCardTransDataMeta</code> is a meta class describing the database table object CLW_CREDIT_CARD_TRANS.
 */
public class CreditCardTransDataMeta extends com.cleanwise.service.api.cachecos.TableObjectMeta {

    private static final long serialVersionUID = -1L; //ERROR. No Serial Version Provided
       
    /**
     * Constructor.
     */
    public CreditCardTransDataMeta(TableObject pData) {
        
        super(CreditCardTransDataAccess.CLW_CREDIT_CARD_TRANS);

        if (pData != null) {
            TableObjectFieldMeta fm = new TableObjectFieldMeta();
            fm.setField(CreditCardTransDataAccess.CREDIT_CARD_TRANS_ID);
            fm.setValue(pData.getFieldValue(CreditCardTransDataAccess.CREDIT_CARD_TRANS_ID));
            addField(fm);
        }
    
    }

    /**
     * Constructor.
     */
    public CreditCardTransDataMeta(TableField... fields) {
        
        super(CreditCardTransDataAccess.CLW_CREDIT_CARD_TRANS);

        for (TableField field : fields) {
            TableObjectFieldMeta fm = new TableObjectFieldMeta();
            fm.setField(field.getName());
            fm.setValue(field.getValue());
            addField(fm);
        }
    
    }

}
    
